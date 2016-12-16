package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Doctor;
import ru.baltclinic.lliepmah.util.ImageUtils;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class DoctorViewHolder extends DefaultViewHolder<Doctor> {

    private final Context mContext;
    private final String mBullet;

    @Nullable
    private Doctor mDoctor = null;

    private final OnDoctorClickListener mOnDoctorClickListener;

    @BindView(R.id.li_doctor_iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.li_doctor_tv_lastname)
    TextView mTvLastname;
    @BindView(R.id.li_doctor_tv_name)
    TextView mTvName;
    @BindView(R.id.li_doctor_tv_first_position)
    TextView mTvFirstPosition;
    @BindView(R.id.li_doctor_tv_second_position)
    TextView mTvSecondPosition;
    @BindView(R.id.li_doctor_tv_third_position)
    TextView mTvThirdPosition;

    private DoctorViewHolder(View itemView, OnDoctorClickListener onDoctorClickListener) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContext = itemView.getContext();
        mOnDoctorClickListener = onDoctorClickListener;
        ButterKnife.bind(this, itemView);
        mBullet = mContext.getString(R.string.bullet);
    }

    @Override
    public void bind(Doctor model) {
        mDoctor = model;
        if (mDoctor != null) {
            ImageUtils.loadCircleImage(mContext, model.getPhotoUrl(), mIvAvatar);

            TextViewUtils.setTextIfNotEmpty(mTvName, TextViewUtils.splitValues(TextViewUtils.VALUE_SPACE_VALUE, model.getFirstName(), model.getMiddleName()));
            TextViewUtils.setTextIfNotEmpty(mTvLastname, model.getLastName());

            TextViewUtils.setTextWithPrefixIfNotEmptyOrHide(mTvFirstPosition, mBullet, model.getFirstPosition());
            TextViewUtils.setTextWithPrefixIfNotEmptyOrHide(mTvSecondPosition, mBullet, model.getSecondPosition());
            TextViewUtils.setTextWithPrefixIfNotEmptyOrHide(mTvThirdPosition, mBullet, model.getThirdPosition());
        }
    }

    @OnClick(R.id.li_doctor_root)
    void onDoctorClick() {
        if (mOnDoctorClickListener != null && mDoctor != null) {
            mOnDoctorClickListener.onDoctorClick(mDoctor);
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<Doctor> {

        private final OnDoctorClickListener mOnDoctorClickListener;

        public Builder(OnDoctorClickListener onDoctorClickListener) {
            mOnDoctorClickListener = onDoctorClickListener;
        }

        @Override
        public DefaultViewHolder<Doctor> build(Context context) {
            View view = View.inflate(context, R.layout.li_doctor, null);
            return new DoctorViewHolder(view, mOnDoctorClickListener);
        }
    }

    public interface OnDoctorClickListener {
        void onDoctorClick(Doctor doctor);
    }
}
