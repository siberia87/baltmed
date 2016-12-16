package ru.baltclinic.lliepmah.gcm;

import com.google.android.gms.iid.InstanceIDListenerService;

import ru.baltclinic.lliepmah.app.BaltApplication;
import ru.baltclinic.lliepmah.service.network.NetworkService;


public class InstanceIdListenerService extends InstanceIDListenerService {

    NetworkService mNetworkService;


    @Override
    public void onTokenRefresh() {
        BaltApplication application = (BaltApplication) getApplication();
        mNetworkService = application.getApplicationComponent().networkService();
        mNetworkService.refreshToken();

    }


}
