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
import ru.baltclinic.lliepmah.models.News;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class NewsViewHolder extends DefaultViewHolder<News> {

    private final Context mContext;

    @Nullable
    private News mNews = null;

    private final OnNewsClickListener mOnNewsClickListener;

    @BindView(R.id.li_news_tv_date)
    TextView mTvDate;
    @BindView(R.id.li_news_tv_title)
    TextView mTvTitle;
    @BindView(R.id.li_news_tv_description)
    TextView mTvDescription;

    private NewsViewHolder(View itemView, OnNewsClickListener onNewsClickListener) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mContext = itemView.getContext();
        mOnNewsClickListener = onNewsClickListener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(News model) {
        mNews = model;
        if (mNews != null) {
            TextViewUtils.setTextIfNotEmpty(mTvDate, model.getTextDate());
            TextViewUtils.setTextIfNotEmpty(mTvTitle, model.getTitle());
            TextViewUtils.setTextIfNotEmpty(mTvDescription, model.getDescr());
        }
    }

    @OnClick(R.id.li_news_root)
    void onNewsClick() {
        if (mOnNewsClickListener != null && mNews != null) {
            mOnNewsClickListener.onNewsClick(mNews);
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<News> {

        private final OnNewsClickListener mOnNewsClickListener;

        public Builder(OnNewsClickListener onNewsClickListener) {
            mOnNewsClickListener = onNewsClickListener;
        }

        @Override
        public DefaultViewHolder<News> build(Context context) {
            View view = View.inflate(context, R.layout.li_news, null);
            return new NewsViewHolder(view, mOnNewsClickListener);
        }
    }

    public interface OnNewsClickListener {
        void onNewsClick(News news);
    }
}
