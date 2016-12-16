package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Service;
import ru.baltclinic.lliepmah.util.ImageUtils;
import ru.baltclinic.lliepmah.util.TextViewUtils;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


public class ServiceFragment extends BaseFragment {

    private static final String KEY_DESCR = "key_full_descr";
    private static final String KEY_IMAGE_URL = "key_image_url";
    private static final String KEY_TITLE = "key_title";

    @BindView(R.id.fmt_service_iv_picture)
    ImageView mIvPicture;
    @BindView(R.id.fmt_service_tv_title)
    TextView mTvTitle;
    @BindView(R.id.fmt_service_tv_desc)
    TextView mTvDesc;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private String mTitle;

    public static Fragment newInstance(Service service) {
        ServiceFragment serviceFragment = new ServiceFragment();
        Bundle bundle = new Bundle();

        bundle.putString(KEY_DESCR, service.getDescr());
        bundle.putString(KEY_IMAGE_URL, service.getImageUrl());
        bundle.putString(KEY_TITLE, service.getTitle());

        serviceFragment.setArguments(bundle);
        return serviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_service, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        mTitle = args.getString(KEY_TITLE);

        super.onViewCreated(view, savedInstanceState);

        TextViewUtils.setTextIfNotEmpty(mTvDesc, args.getString(KEY_DESCR));
        TextViewUtils.setTextIfNotEmpty(mTvTitle, mTitle);

        ImageUtils.loadImage(getContext(), args.getString(KEY_IMAGE_URL), mIvPicture);

    }

    @Override
    public String getTitle() {
        return mTitle;
    }


    @OnClick(R.id.fmt_service_appointment)
    void onServiceAppointmentClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openMakeAppointment();
        }
    }

    @OnClick(R.id.fmt_service_call)
    void onServiceCallClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openOrderCall();
        }
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_services;
    }
}
