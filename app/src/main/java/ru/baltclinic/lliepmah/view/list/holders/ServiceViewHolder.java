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
import ru.baltclinic.lliepmah.models.Service;
import ru.baltclinic.lliepmah.util.ImageUtils;
import ru.baltclinic.lliepmah.util.TextViewUtils;

public class ServiceViewHolder extends DefaultViewHolder<Service> {

    private final Context mContext;

    @Nullable
    private Service mService = null;

    private final OnServiceClickListener mOnServiceClickListener;

    @BindView(R.id.li_service_iv_poster)
    ImageView mIvPoster;
    @BindView(R.id.li_service_tv_service)
    TextView mTvService;

    private ServiceViewHolder(View itemView, OnServiceClickListener onServiceClickListener) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContext = itemView.getContext();
        mOnServiceClickListener = onServiceClickListener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Service model) {
        mService = model;
        if (mService != null) {
            ImageUtils.loadImage(mContext, model.getImageUrl(), mIvPoster);
            TextViewUtils.setTextIfNotEmpty(mTvService, model.getTitle());
        }
    }

    @OnClick(R.id.li_service_root)
    void onServiceClick() {
        if (mOnServiceClickListener != null && mService != null) {
            mOnServiceClickListener.onServiceClick(mService);
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<Service> {

        private final OnServiceClickListener mOnServiceClickListener;

        public Builder(OnServiceClickListener onServiceClickListener) {
            mOnServiceClickListener = onServiceClickListener;
        }

        @Override
        public DefaultViewHolder<Service> build(Context context) {
            View view = View.inflate(context, R.layout.li_service, null);
            return new ServiceViewHolder(view, mOnServiceClickListener);
        }
    }

    public interface OnServiceClickListener {
        void onServiceClick(Service Service);
    }
}
