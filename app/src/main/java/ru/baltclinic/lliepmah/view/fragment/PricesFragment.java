package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Price;
import ru.baltclinic.lliepmah.models.Service;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.decoration.SimpleDividerItemDecoration;
import ru.baltclinic.lliepmah.view.list.holders.PriceHeaderViewHolder;
import ru.baltclinic.lliepmah.view.list.holders.PriceViewHolder;
import ru.baltclinic.lliepmah.view.list.holders.PriceWarnViewHolder;
import ru.baltclinic.lliepmah.view.list.holders.TitleViewHolder;
import ru.baltclinic.lliepmah.view.list.viewmodels.ViewModelWrapper;
import rx.Observable;


public class PricesFragment extends SimpleListFragment<Price> {

    private static final String SERVICE_ID = "service_id";
    private static final String SERVICE_TITLE = "service_title";

    private int mServiceId;

    public static Fragment newInstance(Service service) {
        PricesFragment pricesFragment = new PricesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SERVICE_ID, service.getId());
        bundle.putString(SERVICE_TITLE, service.getTitle());
        pricesFragment.setArguments(bundle);
        return pricesFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        mServiceId = arguments.getInt(SERVICE_ID);
        String serviceTitle = arguments.getString(SERVICE_TITLE);

        mAdapter = new UniversalAdapter(new PriceViewHolder.Builder(),
                new TitleViewHolder.Builder(),
                new PriceHeaderViewHolder.Builder(),
                new PriceWarnViewHolder.Builder());

        mRvMain.setAdapter(mAdapter);
        mRvMain.addItemDecoration(new SimpleDividerItemDecoration(getContext(), isGridList()));
        mAdapter.add(ViewModelWrapper.build(getString(R.string.label_services_price, serviceTitle),
                TitleViewHolder.Builder.class));
        mAdapter.add(ViewModelWrapper.build(null, PriceHeaderViewHolder.Builder.class));

        makeRequest(false);
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_prices);
    }

    @Override
    protected void onLoaded(List<Price> prices) {
        super.onLoaded(prices);
        mAdapter.removeItems(2, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(prices, PriceViewHolder.Builder.class);
        mAdapter.add(ViewModelWrapper.build(null, PriceWarnViewHolder.Builder.class));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<Price>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getPrices(mServiceId);
        }
        return null;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_prices + mServiceId;
    }

    @Override
    public boolean isGridList() {
        return false;
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_prices;
    }
}
