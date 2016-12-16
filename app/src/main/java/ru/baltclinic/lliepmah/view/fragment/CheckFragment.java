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
import ru.baltclinic.lliepmah.models.Appointment;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class CheckFragment extends BaseFragment {

    public static final String KEY_APPOINTMENT = "key_appointment";

    @BindView(R.id.fmt_check_tv_date)
    TextView mTvDate;
    @BindView(R.id.fmt_check_tv_date_label)
    TextView mTvDateLabel;
    @BindView(R.id.fmt_check_tv_time)
    TextView mTvTime;
    @BindView(R.id.fmt_check_tv_time_label)
    TextView mTvTimeLabel;
    @BindView(R.id.fmt_check_tv_email)
    TextView mTvEmail;
    @BindView(R.id.fmt_check_tv_email_label)
    TextView mTvEmailLabel;
    @BindView(R.id.fmt_check_tv_lastname)
    TextView mTvLastname;
    @BindView(R.id.fmt_check_tv_lastname_label)
    TextView mTvLastnameLabel;
    @BindView(R.id.fmt_check_tv_name)
    TextView mTvName;
    @BindView(R.id.fmt_check_tv_name_label)
    TextView mTvNameLabel;
    @BindView(R.id.fmt_check_tv_middlename)
    TextView mTvMiddlename;
    @BindView(R.id.fmt_check_tv_middlename_label)
    TextView mTvMiddlenameLabel;
    @BindView(R.id.fmt_check_tv_notes)
    TextView mTvNotes;
    @BindView(R.id.fmt_check_tv_notes_label)
    TextView mTvNotesLabel;
    @BindView(R.id.fmt_check_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.fmt_check_tv_phone_label)
    TextView mTvPhoneLabel;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Appointment mAppointment;

    public static Fragment newInstance(Appointment appointment) {
        CheckFragment checkFragment = new CheckFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_APPOINTMENT, appointment);
        checkFragment.setArguments(bundle);
        return checkFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_check, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Serializable serializable = getArguments().getSerializable(KEY_APPOINTMENT);
        if (serializable == null) {
            return;
        }

        mAppointment = (Appointment) serializable;

        setField(mAppointment.getDate(), mTvDate, mTvDateLabel);
        setField(mAppointment.getTime(), mTvTime, mTvTimeLabel);
        setField(mAppointment.getEmail(), mTvEmail, mTvEmailLabel);
        setField(mAppointment.getLastName(), mTvLastname, mTvLastnameLabel);
        setField(mAppointment.getFirstName(), mTvName, mTvNameLabel);
        setField(mAppointment.getMiddleName(), mTvMiddlename, mTvMiddlenameLabel);
        setField(mAppointment.getComment(), mTvNotes, mTvNotesLabel);
        setField(mAppointment.getPhone(), mTvPhone, mTvPhoneLabel);

    }

    private void setField(String text, TextView tvValue, TextView tvLabel) {
        boolean hasText = !TextUtils.isEmpty(text);
        tvValue.setVisibility(hasText ? View.VISIBLE : View.GONE);
        tvLabel.setVisibility(hasText ? View.VISIBLE : View.GONE);
        tvValue.setText(hasText ? text : "");
    }


    @OnClick(R.id.fmt_check_btn_send)
    void onSendAppointmentClick() {
        NetworkService networkService = getNetworkService();

        if (networkService != null) {
            // TODO: 29.09.16 show progress
            networkService.gotoAction(mAppointment)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLoader.from(getBaseActivity(), R.id.loader_send_appointment, true))
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
