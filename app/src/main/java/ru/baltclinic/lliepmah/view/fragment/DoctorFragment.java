package ru.baltclinic.lliepmah.view.fragment;

import android.content.Context;
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
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Doctor;
import ru.baltclinic.lliepmah.util.ImageUtils;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class DoctorFragment extends BaseFragment {

    private static final String KEY_POSITIONS = "key_positions";
    private static final String KEY_DESCR = "key_full_descr";
    private static final String KEY_AVATAR_URL = "key_avatar_url";
    private static final String KEY_NAME = "key_name";

    @BindView(R.id.fmt_doctor_iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.fmt_doctor_tv_name)
    TextView mTvName;
    @BindView(R.id.fmt_doctor_tv_positions)
    TextView mTvPosition;
    @BindView(R.id.fmt_doctor_tv_desc)
    TextView mTvDesc;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mName;

    public static Fragment newInstance(Context context, Doctor doctor) {
        DoctorFragment doctorFragment = new DoctorFragment();
        Bundle bundle = new Bundle();

        bundle.putString(KEY_AVATAR_URL, doctor.getPhotoUrl());
        bundle.putString(KEY_DESCR, doctor.getDescr());
        bundle.putString(KEY_NAME, TextViewUtils.splitValues(TextViewUtils.SPACE, doctor.getLastName(), doctor.getFirstName(), doctor.getMiddleName()));

        bundle.putCharSequence(KEY_POSITIONS, TextViewUtils.formatList(doctor.getFirstPosition(), doctor.getSecondPosition(), doctor.getThirdPosition()));

        doctorFragment.setArguments(bundle);
        return doctorFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_doctor, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        mName = args.getString(KEY_NAME);
        super.onViewCreated(view, savedInstanceState);

        TextViewUtils.setTextIfNotEmpty(mTvDesc, args.getString(KEY_DESCR));
        TextViewUtils.setTextIfNotEmpty(mTvName, mName);
        TextViewUtils.setTextIfNotEmpty(mTvPosition, args.getCharSequence(KEY_POSITIONS));

        ImageUtils.loadCircleImage(getContext(), args.getString(KEY_AVATAR_URL), mIvAvatar);

    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_specialists;
    }

    @Override
    public String getTitle() {
        return mName;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }
}
