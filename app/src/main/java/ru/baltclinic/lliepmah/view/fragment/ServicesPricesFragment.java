package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Service;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.decoration.SimpleDividerItemDecoration;
import ru.baltclinic.lliepmah.view.list.holders.ServicePriceViewHolder;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.Observable;


public class ServicesPricesFragment extends SearchListFragment<Service> {

    private List<Service> mServices;

    public static Fragment newInstance() {
        return new ServicesPricesFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new UniversalAdapter(new ServicePriceViewHolder.Builder(this::onServiceClick));

        mRvMain.setAdapter(mAdapter);
        mRvMain.addItemDecoration(new SimpleDividerItemDecoration(getContext(), isGridList()));

        makeRequest(false);
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_prices);
    }

    private void onServiceClick(Service service) {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openPrice(service);
        }
    }

    @Override
    protected void onLoaded(List<Service> services) {
        super.onLoaded(services);
        mServices = services;
        replaceItems(services);
    }

    private void replaceItems(List<Service> services) {
        mAdapter.removeItems(0, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(services, ServicePriceViewHolder.Builder.class);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<Service>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getServicesWithPrices();
        }
        return null;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_services_prices;
    }

    @Override
    public boolean isGridList() {
        return false;
    }

    @Override
    protected void search(String searchText) {
        List<Service> services = Stream.of(mServices)
                .filter(value -> value.hasString(searchText))
                .collect(Collectors.toList());
        replaceItems(services);
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_prices;
    }
}
