package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Action;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.holders.ActionViewHolder;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.Observable;



public class ActionsFragment extends SimpleListFragment<Action> {

    public static Fragment newInstance() {
        return new ActionsFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new UniversalAdapter(new ActionViewHolder.Builder(this::onActionClick));
        mRvMain.setAdapter(mAdapter);
        makeRequest(false);
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_actions);
    }

    private void onActionClick(Action action) {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openAction(action);
        }
    }

    @Override
    protected void onLoaded(List<Action> actions) {
        super.onLoaded(actions);
        mAdapter.removeItems(0, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(actions, ActionViewHolder.Builder.class);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<Action>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getActions();
        }
        return null;
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_actions;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_actions;
    }

    @Override
    public boolean isGridList() {
        return false;
    }

}
