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
import ru.baltclinic.lliepmah.models.MenuItem;


public class MenuViewHolder extends DefaultViewHolder<MenuItem> {

    private final Context mContext;

    @Nullable
    private MenuItem mMenuItem = null;

    private final OnMenuClickListener mOnMenuClickListener;

    @BindView(R.id.li_menu_root)
    View mVRoot;
    @BindView(R.id.li_menu_iv_icon)
    ImageView mIvLogo;
    @BindView(R.id.li_menu_tv_title)
    TextView mTvTitle;
    @BindView(R.id.li_menu_tv_badge)
    TextView mTvBadge;

    private MenuViewHolder(View itemView, OnMenuClickListener onMenuClickListener) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContext = itemView.getContext();
        mOnMenuClickListener = onMenuClickListener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(MenuItem model) {
        mMenuItem = model;
        if (mMenuItem != null) {
            mIvLogo.setImageResource(model.getDrawableRes());
            mTvTitle.setText(model.getTitleRes());
            int badgeValue = model.getBadgeValue();
            mTvBadge.setText(Integer.toString(model.getBadgeValue()));
            mTvBadge.setVisibility(badgeValue > 0 ? View.VISIBLE : View.GONE);
            mVRoot.setSelected(model.isSelected());
        }
    }

    @OnClick(R.id.li_menu_root)
    void onMenuClick() {
        if (mOnMenuClickListener != null && mMenuItem != null) {
            mOnMenuClickListener.onMenuClick(mMenuItem);
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<MenuItem> {

        private final OnMenuClickListener mOnMenuClickListener;

        public Builder(OnMenuClickListener onMenuClickListener) {
            mOnMenuClickListener = onMenuClickListener;
        }

        @Override
        public DefaultViewHolder<MenuItem> build(Context context) {
            View view = View.inflate(context, R.layout.li_menu, null);
            return new MenuViewHolder(view, mOnMenuClickListener);
        }
    }

    public interface OnMenuClickListener {
        void onMenuClick(MenuItem menuItem);
    }
}
