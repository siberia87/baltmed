package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Service;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.decoration.SimpleDividerItemDecoration;
import ru.baltclinic.lliepmah.view.list.holders.ServiceViewHolder;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.Observable;


public class ServicesFragment extends SimpleListFragment<Service> {

    public static Fragment newInstance() {
        return new ServicesFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new UniversalAdapter(new ServiceViewHolder.Builder(this::onServiceClick));

        mRvMain.setAdapter(mAdapter);
        mRvMain.addItemDecoration(new SimpleDividerItemDecoration(getContext(), isGridList()));

        makeRequest(false);
    }

    private void onServiceClick(Service service) {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openService(service);
        }
    }

    @Override
    protected void onLoaded(List<Service> services) {
        super.onLoaded(services);
        mAdapter.removeItems(0, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(services, ServiceViewHolder.Builder.class);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<Service>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getServices();
        }
        return null;
    }


    @Override
    public String getTitle() {
        return getString(R.string.ttl_services);
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_services;
    }

    @Override
    public boolean isGridList() {
        return isTablet();
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_services;
    }

}
