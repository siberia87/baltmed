package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.philosophicalhacker.lib.RxLoader;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Call;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CheckCallFragment extends BaseFragment {

    public static final String KEY_CALL = "key_call";

    @BindView(R.id.fmt_check_call_tv_fio)
    TextView mTvName;
    @BindView(R.id.fmt_check_call_tv_fio_label)
    TextView mTvNameLabel;
    @BindView(R.id.fmt_check_call_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.fmt_check_call_tv_phone_label)
    TextView mTvPhoneLabel;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Call mCall;


    public static Fragment newInstance(Call call) {
        CheckCallFragment checkFragment = new CheckCallFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CALL, call);
        checkFragment.setArguments(bundle);
        return checkFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_check_call, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Serializable serializable = getArguments().getSerializable(KEY_CALL);
        if (!Call.class.isInstance(serializable)) {
            return;
        }

        mCall = (Call) serializable;

        setField(mCall.getName(), mTvName, mTvNameLabel);
        setField(mCall.getPhone(), mTvPhone, mTvPhoneLabel);

    }

    private void setField(String text, TextView tvValue, TextView tvLabel) {
        boolean hasText = !TextUtils.isEmpty(text);
        tvValue.setVisibility(hasText ? View.VISIBLE : View.GONE);
        tvLabel.setVisibility(hasText ? View.VISIBLE : View.GONE);
        tvValue.setText(hasText ? text : "");
    }


    @OnClick(R.id.fmt_check_call_btn_send)
    void onSendAppointmentClick() {
        NetworkService networkService = getNetworkService();

        if (networkService != null) {
            // TODO: 29.09.16 show progress
            networkService.callAction(mCall)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLoader.from(getBaseActivity(), R.id.loader_order_call, true))
                    .subscribe(this::onSent, this::onError);
        }

    }

    private void onError(Throwable throwable) {
        Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_LONG).show();
    }

    private void onSent(ResponseBody s) {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            new Handler().post(navigator::walkOutFromCheck);
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_check_data);
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_services;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }
}
