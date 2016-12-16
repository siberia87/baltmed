package ru.baltclinic.lliepmah.service.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.baltclinic.lliepmah.models.Action;
import ru.baltclinic.lliepmah.models.Appointment;
import ru.baltclinic.lliepmah.models.Call;
import ru.baltclinic.lliepmah.models.Doctor;
import ru.baltclinic.lliepmah.models.GalleryImage;
import ru.baltclinic.lliepmah.models.News;
import ru.baltclinic.lliepmah.models.Notification;
import ru.baltclinic.lliepmah.models.Price;
import ru.baltclinic.lliepmah.models.Service;
import ru.baltclinic.lliepmah.util.DateUtils;
import ru.baltclinic.lliepmah.util.RxUtils;
import ru.baltclinic.lliepmah.view.activity.MainActivity;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Singleton
public class NetworkService {

    private static final String WEB_SERVICE_BASE_URL = "http://www.mobile.baltclinicvo.ru/backend/";
    private static final String SENDER_ID = "395207414424";
    public static final String METHOD_GET = "GET";

    private final ApiService mRequestService;
    private final Context mContext;

    public NetworkService(Context context) {
        mContext = context;
        OkHttpClient httpClient = createHttpClient();
        Retrofit retrofitBuilder = buildRetrofit(httpClient, WEB_SERVICE_BASE_URL);
        mRequestService = retrofitBuilder.create(ApiService.class);
    }

    protected OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(createHeadersInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();

                        Response response = null;
                        byte[] resultBytes = new byte[0];

                        try {
                            response = chain.proceed(requestBuilder.build());

                            if (METHOD_GET.equals(request.method()) && response != null) {
                                String path = request.url().encodedPath();
                                resultBytes = response.body().bytes();
                                File cacheFile = new File(mContext.getCacheDir(), path);
                                writeTofile(cacheFile, resultBytes);
                            }

                        } catch (IOException exception) {
                            File cacheFile;
                            String path = request.url().encodedPath();
                            if (METHOD_GET.equals(request.method()) && (cacheFile = new File(mContext.getCacheDir(), path)).exists()) {
                                resultBytes = getBytesFromFile(cacheFile);
                            } else {
                                throw exception;
                            }
                        }
                        return new Response.Builder()
                                .code(200)
                                .protocol(Protocol.HTTP_1_1)
                                .request(request)
                                .body(ResponseBody.create(MediaType.parse("application/json"), resultBytes))
                                .build();
                    }
                })
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    @NonNull
    private void writeTofile(File cacheFile, byte[] bytes) throws IOException {
        cacheFile.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(cacheFile);
        out.write(bytes);
        out.close();
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        return bytes;
    }

    protected Interceptor createHeadersInterceptor() {
        return (chain) -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.addHeader("Accept-Language", "RU");
            return chain.proceed(requestBuilder.build());
        };
    }

    @NonNull
    protected Converter.Factory createConverterFactory() {
        return GsonConverterFactory.create(createGson());
    }

    @NonNull
    protected Retrofit buildRetrofit(OkHttpClient httpClient, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(createConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    @NonNull
    protected Gson createGson() {
        return new GsonBuilder()
                .create();
    }

    public Observable<List<News>> getNews() {
        return mRequestService.getNews()
                .flatMap(Observable::from)
                .sorted((n1, n2) -> n1.getDate() < n2.getDate() ? 1 : -1)
                .doOnNext(news -> news.setTextDate(DateUtils.formatDisplayDate(news.getDate() * 1000)))
                .toList();

    }

    public Observable<ResponseBody> gotoAction(Appointment appointment) {
        return mRequestService.gotoAction(appointment.getDate(), appointment.getTime(),
                appointment.getLastName(), appointment.getFirstName(), appointment.getMiddleName(),
                appointment.getEmail(), appointment.getPhone(), appointment.getComment());
    }

    public Observable<ResponseBody> callAction(Call call) {
        return mRequestService.callAction(call.getName(), call.getPhone());
    }

    public Observable<List<Price>> getPrices(int serviceId) {
        return mRequestService.getPrices()
                .flatMap(Observable::from)
                .filter(price -> price.getServiceId() == serviceId)
                .toList();
    }

    public Observable<List<Service>> getServices() {
        return mRequestService.getServices()
                .compose(RxUtils.sortOrdered());
    }

    public Observable<List<Service>> getServicesWithPrices() {
        return mRequestService.getServicesWithPrices()
                .compose(RxUtils.sortOrdered());
    }

    public Observable<List<Action>> getActions() {
        return mRequestService.getActions()
                .flatMap(Observable::from)
                .sorted((n1, n2) -> n1.getFromDate() < n2.getFromDate() ? 1 : -1)
                .doOnNext(action -> {
                    action.setToDateText(DateUtils.formatMonthDate(action.getToDate() * 1000));
                    action.setFromDateText(DateUtils.formatMonthDate(action.getFromDate() * 1000));
                })
                .toList();
    }

    public Observable<List<Notification>> getNotifications() {
        return mRequestService.getNotifications()
                .flatMap(Observable::from)
                .sorted((n1, n2) -> n1.getDate() < n2.getDate() ? 1 : -1)
                .doOnNext(news -> news.setTextDate(DateUtils.formatMonthDate(news.getDate() * 1000)))
                .toList();
    }


    public Observable<List<GalleryImage>> getGallery() {
        return mRequestService.getGallery();
    }

    public Observable<List<Doctor>> getDoctors() {
        return mRequestService.getDoctors()
                .compose(RxUtils.sortOrdered());
    }

    public void refreshToken() {
        getGcmToken()
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                })
                .flatMap(mRequestService::saveToken)
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        try {
                            return responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "Exception";
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                        prefs.edit().putBoolean(MainActivity.KEY_GCM_REGISTERED, true).apply();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(NetworkService.class.getName(), "e-> onError ->" + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(NetworkService.class.getName(), "e-> onNext ->" + s);
                    }
                });
    }

    public Observable<String> getGcmToken() {
        return Observable.create(subscriber -> {
            InstanceID instanceID = InstanceID.getInstance(mContext);
            String token = null;
            try {
                token = instanceID.getToken(SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
            subscriber.onNext(token);
            subscriber.onCompleted();
        });
    }
}
