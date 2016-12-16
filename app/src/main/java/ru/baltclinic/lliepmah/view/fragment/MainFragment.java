package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.News;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.decoration.SimpleDividerItemDecoration;
import ru.baltclinic.lliepmah.view.list.holders.ButtonViewHolder;
import ru.baltclinic.lliepmah.view.list.holders.MainHeaderViewHolder;
import ru.baltclinic.lliepmah.view.list.holders.NewsViewHolder;
import ru.baltclinic.lliepmah.view.list.holders.TitleViewHolder;
import ru.baltclinic.lliepmah.view.list.viewmodels.ViewModelWrapper;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.Observable;


public class MainFragment extends SimpleListFragment<News> implements SwipeRefreshLayout.OnRefreshListener, MainHeaderViewHolder.IMainHeaderCallbacks {


    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_main, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new UniversalAdapter(new NewsViewHolder.Builder(this::onNewsClick),
                new TitleViewHolder.Builder(),
                new MainHeaderViewHolder.Builder(this),
                new ButtonViewHolder.Builder(this::onShowAllNews));

        mRvMain.setAdapter(mAdapter);
        mRvMain.addItemDecoration(new SimpleDividerItemDecoration(getContext(), isGridList()));

        mAdapter.addNewItems(Arrays.asList(
                new ViewModelWrapper<>(null, MainHeaderViewHolder.Builder.class),
                new ViewModelWrapper<>(getString(R.string.label_news_of_clinic), TitleViewHolder.Builder.class)
        ));

        makeRequest(false);

        mToolbar.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        mMaterialMenu.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    protected void onLoaded(List<News> news) {
        super.onLoaded(news);
        mAdapter.removeItems(2, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(news, NewsViewHolder.Builder.class);
        mAdapter.add(new ViewModelWrapper<>(getString(R.string.label_all_news), ButtonViewHolder.Builder.class));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<News>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getNews()
                    .map(newses -> newses.subList(0, Math.min(2, newses.size() - 1))); // TODO: 27.09.16 test!
        }
        return null;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_main_news;
    }

    @Override
    public boolean isGridList() {
        return false;
    }

    private void onShowAllNews() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openNews();
        }
    }

    private void onNewsClick(News news) {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openNews(news);
        }
    }

    @Override
    public void onViewPrices() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openPrices();
        }
    }

    @Override
    public void onViewServices() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openServices();
        }
    }

    @Override
    public void onMakeAppointment() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openMakeAppointment();
        }
    }

    @Override
    public void onCallAction() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openDialer();
        }
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_main;
    }
}
