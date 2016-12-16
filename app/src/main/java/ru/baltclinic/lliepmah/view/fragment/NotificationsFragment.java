package ru.baltclinic.lliepmah.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Notification;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.activity.MainActivity;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.holders.NotificationViewHolder;
import rx.Observable;


public class NotificationsFragment extends SimpleListFragment<Notification> {

    public static Fragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new UniversalAdapter(new NotificationViewHolder.Builder());
        mRvMain.setAdapter(mAdapter);
        makeRequest(false);

        invalidateNotifivationsCount();
    }

    private void invalidateNotifivationsCount() {
        AppCompatActivity baseActivity = getBaseActivity();
        if (baseActivity != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(baseActivity.getApplication());
            prefs.edit().putInt(MainActivity.KEY_PUSH_MESSAGES, 0).apply();
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_notifications);
    }

    @Override
    protected void onLoaded(List<Notification> Notifications) {
        super.onLoaded(Notifications);
        mAdapter.removeItems(0, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(Notifications, NotificationViewHolder.Builder.class);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<Notification>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getNotifications();
        }
        return null;
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_notifications;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_notifications;
    }

    @Override
    public boolean isGridList() {
        return isTablet();
    }

}
