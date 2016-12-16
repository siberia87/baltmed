package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.News;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.holders.NewsViewHolder;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.Observable;


public class NewsFragment extends SimpleListFragment<News> implements SwipeRefreshLayout.OnRefreshListener {

    public static final String KEY_NEWS = "key_news_id";

    public static NewsFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_NEWS, news);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new UniversalAdapter(new NewsViewHolder.Builder(this::onNewsClick));
        mRvMain.setAdapter(mAdapter);
        makeRequest(false);

        Bundle args = getArguments();
        if (args != null && args.containsKey(KEY_NEWS)) {
            openNews((News) args.getSerializable(KEY_NEWS));
        }
    }

    private void openNews(News news) {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openSingleNews(news);
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_news);
    }

    private void onNewsClick(News news) {
        openNews(news);
    }

    @Override
    protected void onLoaded(List<News> news) {
        super.onLoaded(news);
        mAdapter.removeItems(0, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(news, NewsViewHolder.Builder.class);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<News>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getNews();
        }
        return null;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_all_news;
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_news;
    }

    @Override
    public boolean isGridList() {
        return isTablet();
    }

}
