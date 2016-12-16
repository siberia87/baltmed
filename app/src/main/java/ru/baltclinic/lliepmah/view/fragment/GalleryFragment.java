package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.GalleryImage;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.holders.GalleryViewHolder;
import rx.Observable;

public class GalleryFragment extends SimpleListFragment<GalleryImage> {

    public static Fragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new UniversalAdapter(new GalleryViewHolder.Builder());
        mRvMain.setAdapter(mAdapter);
        makeRequest(false);
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_gallery);
    }

    @Override
    protected void onLoaded(List<GalleryImage> news) {
        super.onLoaded(news);
        mAdapter.removeItems(0, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(news, GalleryViewHolder.Builder.class);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<GalleryImage>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getGallery();
        }
        return null;
    }

    protected boolean withDecoration() {
        return false;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_gallery;
    }

    @Override
    public boolean isGridList() {
        return isTablet();
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_gallery;
    }

}
