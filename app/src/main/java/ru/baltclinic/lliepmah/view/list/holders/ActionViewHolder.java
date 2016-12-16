package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Action;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class ActionViewHolder extends DefaultViewHolder<Action> {

    private final Context mContext;

    @Nullable
    private Action mAction = null;

    private final OnActionClickListener mOnActionClickListener;

    @BindView(R.id.li_action_tv_date)
    TextView mTvDate;
    @BindView(R.id.li_action_tv_title)
    TextView mTvTitle;
    @BindView(R.id.li_action_tv_description)
    TextView mTvDescription;

    ActionViewHolder(View itemView, OnActionClickListener onActionClickListener) {
        super(itemView);
        mContext = itemView.getContext();
        mOnActionClickListener = onActionClickListener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Action model) {
        mAction = model;
        if (mAction != null) {
            TextViewUtils.setTextIfNotEmpty(mTvDate, TextViewUtils.splitValues(TextViewUtils.VALUE_DASH_VALUE, model.getFromDateString(), model.getToDateString()));
            TextViewUtils.setTextIfNotEmpty(mTvTitle, model.getTitle());
            TextViewUtils.setTextIfNotEmpty(mTvDescription, model.getDescr());
        }
    }

    @OnClick(R.id.li_action_root)
    void onActionClick() {
        if (mOnActionClickListener != null && mAction != null) {
            mOnActionClickListener.onActionClick(mAction);
        }

    }

    public static class Builder implements DefaultViewHolder.Builder<Action> {

        private final OnActionClickListener mOnActionClickListener;

        public Builder(OnActionClickListener onActionClickListener) {
            mOnActionClickListener = onActionClickListener;
        }

        @Override
        public DefaultViewHolder<Action> build(Context context) {
            View view = View.inflate(context, R.layout.li_action, null);
            return new ActionViewHolder(view, mOnActionClickListener);
        }
    }

    public interface OnActionClickListener {
        void onActionClick(Action Action);
    }
}
