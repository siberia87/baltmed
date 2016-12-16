package ru.baltclinic.lliepmah.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.baltclinic.lliepmah.app.BaltApplication;
import ru.baltclinic.lliepmah.service.network.NetworkService;

@Module
public class ApplicationModule {

    BaltApplication mBaltApplication;

    public ApplicationModule(BaltApplication mBaltApplication) {
        this.mBaltApplication = mBaltApplication;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return mBaltApplication;
    }


    @Provides
    @Singleton
    NetworkService providesNetworkService() {
        return new NetworkService(mBaltApplication);
    }

}
