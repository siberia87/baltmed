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
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.News;
import ru.baltclinic.lliepmah.util.ImageUtils;
import ru.baltclinic.lliepmah.util.TextViewUtils;

public class SingleNewsFragment extends BaseFragment {

    private static final String KEY_TEXT_DATE = "key_text_date";
    private static final String KEY_FULL_DESCR = "key_full_descr";
    private static final String KEY_IMAGE_URL = "key_image_url";
    private static final String KEY_TITLE = "key_title";

    @BindView(R.id.fmt_single_news_iv_picture)
    ImageView mIvPicture;
    @BindView(R.id.fmt_single_news_tv_date)
    TextView mTvDate;
    @BindView(R.id.fmt_single_news_tv_title)
    TextView mTvTitle;
    @BindView(R.id.fmt_single_news_tv_desc)
    TextView mTvDesc;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Fragment newInstance(News news) {
        SingleNewsFragment singleNewsFragment = new SingleNewsFragment();
        Bundle bundle = new Bundle();

        bundle.putString(KEY_TEXT_DATE, news.getTextDate());
        bundle.putString(KEY_FULL_DESCR, news.getFullDescr());
        bundle.putString(KEY_IMAGE_URL, news.getImageUrl());
        bundle.putString(KEY_TITLE, news.getTitle());

        singleNewsFragment.setArguments(bundle);
        return singleNewsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_single_news, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        TextViewUtils.setTextIfNotEmpty(mTvDate, args.getString(KEY_TEXT_DATE));
        TextViewUtils.setTextIfNotEmpty(mTvDesc, args.getString(KEY_FULL_DESCR));
        TextViewUtils.setTextIfNotEmpty(mTvTitle, args.getString(KEY_TITLE));

        ImageUtils.loadImage(getContext(), args.getString(KEY_IMAGE_URL), mIvPicture);

    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_news);
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_news;
    }
}
