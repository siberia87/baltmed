package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Appointment;
import ru.baltclinic.lliepmah.util.DateUtils;
import ru.baltclinic.lliepmah.view.navigator.Navigator;



public class AppointmentFragment extends BaseFragment implements SendFragment, DateUtils.OnDateChangedListener, DateUtils.OnTimeChangedListener {

    @BindView(R.id.fmt_appointment_et_date)
    EditText mEtDate;
    @BindView(R.id.fmt_appointment_et_time)
    EditText mEtTime;
    @BindView(R.id.fmt_appointment_et_lastname)
    EditText mEtLastname;
    @BindView(R.id.fmt_appointment_et_name)
    EditText mEtName;
    @BindView(R.id.fmt_appointment_et_middlename)
    EditText mEtMiddlename;
    @BindView(R.id.fmt_appointment_et_email)
    EditText mEtEmail;
    @BindView(R.id.fmt_appointment_et_phone)
    EditText mEtPhone;
    @BindView(R.id.fmt_appointment_et_notes)
    EditText mEtNotes;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Fragment newInstance() {
        AppointmentFragment serviceFragment = new AppointmentFragment();
        return serviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_appointment, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.fmt_appointment_btn_send)
    void onSendAppointmentClick() {
        if (validateFields()) {
            Appointment appointment = collectFields();
            Navigator navigator = getNavigator();
            if (navigator != null) {
                navigator.openCheckAppointment(appointment);
            }
        }
    }

    @OnClick(R.id.fmt_appointment_et_date)
    void onDateClick() {
        DateUtils.showDateDialog(getActivity(), DateUtils.parseDisplayDate(mEtDate.getText().toString()), this);
    }

    @OnClick(R.id.fmt_appointment_et_time)
    void onTimeClick() {
        DateUtils.showTimeDialog(getActivity(), DateUtils.parseDisplayTime(mEtTime.getText().toString()), this);
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(mEtDate.getText().toString())) {
            errorEmptyField(R.string.label_desired_date_of_appointment);
            return false;
        }

        if (TextUtils.isEmpty(mEtTime.getText().toString())) {
            errorEmptyField(R.string.label_desired_time_of_appointment);
            return false;
        }

        if (TextUtils.isEmpty(mEtLastname.getText().toString())) {
            errorEmptyField(R.string.label_lastname);
            return false;
        }

        if (TextUtils.isEmpty(mEtName.getText().toString())) {
            errorEmptyField(R.string.label_name);
            return false;
        }

        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            errorEmptyField(R.string.label_phone);
            return false;
        }

        return true;
    }

    private void errorEmptyField(@StringRes int fieldNameRes) {
        Toast.makeText(getContext(), getString(R.string.label_fill_field, getString(fieldNameRes)), Toast.LENGTH_LONG)
                .show();
    }

    private Appointment collectFields() {
        return new Appointment(DateUtils.convertFromDisplayToServerDate(mEtDate.getText().toString()),
                mEtTime.getText().toString(),
                mEtLastname.getText().toString(),
                mEtName.getText().toString(),
                mEtMiddlename.getText().toString(),
                mEtEmail.getText().toString(),
                mEtPhone.getText().toString(),
                mEtNotes.getText().toString()
        );
    }

    @OnClick(R.id.fmt_appointment_root)
    void onRootClick() {
        hideSoftKeyboard();
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_make_appointment);
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_services;
    }

    @Override
    public void onDateSet(Date date) {
        mEtDate.setText(DateUtils.formatDisplayDate(date.getTime()));
    }

    @Override
    public void onTimeSet(Date date) {
        mEtTime.setText(DateUtils.formatDisplayTime(date.getTime()));
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }
}
