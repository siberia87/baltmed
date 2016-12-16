package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.philosophicalhacker.lib.RxLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.decoration.SimpleDividerItemDecoration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public abstract class SimpleListFragment<Entity> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_main)
    RecyclerView mRvMain;

    @BindView(R.id.l_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.l_progress)
    LinearLayout mLProgress;

    @BindView(R.id.l_empty)
    FrameLayout mLEmpty;

    @BindView(R.id.l_error_loading)
    LinearLayout mLErrorLoading;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    UniversalAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_simple_list, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvMain.setLayoutManager(layoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        if (withDecoration()) {
            mRvMain.addItemDecoration(new SimpleDividerItemDecoration(getContext(), isGridList()));
        }

    }

    protected boolean withDecoration() {
        return true;
    }

    protected void makeRequest(boolean force) {
        makeRequestInternal(getObservable(), getRequestId(), force);
    }

    private void makeRequestInternal(Observable<List<Entity>> observable, int id, boolean force) {
        setLoading(true);

        AppCompatActivity baseActivity = getBaseActivity();

        if (observable != null && baseActivity != null) {
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLoader.from(baseActivity, id, force))
                    .onErrorResumeNext(
                            observable
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()))
                    .subscribe(this::onLoaded, this::onError);
        }
    }

    @CallSuper
    protected void onLoaded(List<Entity> data) {
        mRvMain.setVisibility(View.VISIBLE);
        mLErrorLoading.setVisibility(View.GONE);
        setLoading(false);
    }

    protected void onError(Throwable throwable) {
        setLoading(false);
        mLProgress.setVisibility(View.GONE);
        mRvMain.setVisibility(View.GONE);
        mLErrorLoading.setVisibility(View.VISIBLE);
        throwable.getStackTrace();
    }

    @OnClick(R.id.btn_error_retry)
    void onClickRetry() {
        mLErrorLoading.setVisibility(View.GONE);
        makeRequest(true);
    }

    public void setLoading(boolean loading) {
        mSwipeRefreshLayout.setRefreshing(loading);
        mLProgress.setVisibility(loading ? View.VISIBLE : View.GONE);
        mRvMain.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        makeRequest(true);
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected abstract Observable<List<Entity>> getObservable();

    public abstract int getRequestId();

    public abstract boolean isGridList();
}
