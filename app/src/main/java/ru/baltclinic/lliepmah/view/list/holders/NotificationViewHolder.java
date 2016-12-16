package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Notification;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class NotificationViewHolder extends DefaultViewHolder<Notification> {

    private final Context mContext;

    @Nullable
    private Notification mNotification = null;

    @BindView(R.id.li_notification_tv_date)
    TextView mTvDate;
    @BindView(R.id.li_notification_tv_description)
    TextView mTvDescription;


    private NotificationViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Notification model) {
        mNotification = model;
        if (mNotification != null) {
            TextViewUtils.setTextIfNotEmpty(mTvDate, model.getTextDate());
            TextViewUtils.setTextIfNotEmpty(mTvDescription, model.getMessage());
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<Notification> {


        public Builder() {
        }

        @Override
        public DefaultViewHolder<Notification> build(Context context) {
            View view = View.inflate(context, R.layout.li_notification, null);
            return new NotificationViewHolder(view);
        }
    }

}
