package ru.baltclinic.lliepmah.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import ru.baltclinic.lliepmah.di.components.ApplicationComponent;
import ru.baltclinic.lliepmah.di.components.DaggerApplicationComponent;
import ru.baltclinic.lliepmah.di.modules.ApplicationModule;

public class BaltApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initializeInjector();
    }

    private void initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
