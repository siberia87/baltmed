package ru.baltclinic.lliepmah.service.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.baltclinic.lliepmah.models.Action;
import ru.baltclinic.lliepmah.models.Doctor;
import ru.baltclinic.lliepmah.models.GalleryImage;
import ru.baltclinic.lliepmah.models.News;
import ru.baltclinic.lliepmah.models.Notification;
import ru.baltclinic.lliepmah.models.Price;
import ru.baltclinic.lliepmah.models.Service;
import rx.Observable;

public interface ApiService {

    String QUERY_TOKEN = "token";

    String FIELD_PHONE = "phone";
    String FIELD_NAME = "name";
    String FIELD_DATE_TEXT = "date_text";
    String FIELD_TIME_TEXT = "time_text";
    String FIELD_LAST_NAME = "last_name";
    String FIELD_FIRST_NAME = "first_name";
    String FIELD_MIDDLE_NAME = "middle_name";
    String FIELD_EMAIL = "email";
    String FIELD_COMMENT = "comment";

    @GET("get_news.php")
    Observable<List<News>> getNews();

    @GET("get_services.php")
    Observable<List<Service>> getServices();

    @GET("get_services_with_prices.php")
    Observable<List<Service>> getServicesWithPrices();

    @GET("get_doctors.php")
    Observable<List<Doctor>> getDoctors();

    @GET("get_gallery.php")
    Observable<List<GalleryImage>> getGallery();

    @GET("get_prices.php")
    Observable<List<Price>> getPrices();

    @GET("get_actions.php")
    Observable<List<Action>> getActions();

    @GET("get_push.php")
    Observable<List<Notification>> getNotifications();

    @GET("save_push_token.php?platform=android")
    Observable<ResponseBody> saveToken(
            @Query(QUERY_TOKEN) String token
    );

    @FormUrlEncoded
    @POST("call_action.php")
    Observable<ResponseBody> callAction(
            @Field(FIELD_NAME) String name,
            @Field(FIELD_PHONE) String phone
    );

    @FormUrlEncoded
    @POST("goto_action.php")
    Observable<ResponseBody> gotoAction(
            @Field(FIELD_DATE_TEXT) String date,
            @Field(FIELD_TIME_TEXT) String time,
            @Field(FIELD_LAST_NAME) String lastName,
            @Field(FIELD_FIRST_NAME) String firstName,
            @Field(FIELD_MIDDLE_NAME) String middleName,
            @Field(FIELD_EMAIL) String email,
            @Field(FIELD_PHONE) String phone,
            @Field(FIELD_COMMENT) String comment
    );

}
