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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Call;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


public class OrderCallFragment extends BaseFragment implements SendFragment {

    @BindView(R.id.fmt_order_call_et_fio)
    EditText mEtName;
    @BindView(R.id.fmt_order_call_et_phone)
    EditText mEtPhone;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Fragment newInstance() {
        OrderCallFragment serviceFragment = new OrderCallFragment();
        return serviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_order_call, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.fmt_order_call_btn_send)
    void onSendAppointmentClick() {
        if (validateFields()) {
            Call call = collectFields();
            Navigator navigator = getNavigator();
            if (navigator != null) {
                navigator.openCheckCall(call);
            }
        }
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(mEtName.getText().toString())) {
            errorEmptyField(R.string.label_contact_person);
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

    private Call collectFields() {
        return new Call(mEtName.getText().toString(),
                mEtPhone.getText().toString()
        );
    }

    @OnClick(R.id.fmt_order_call_root)
    void onRootClick() {
        hideSoftKeyboard();
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_order_call);
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
