package ru.baltclinic.lliepmah.di.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ru.baltclinic.lliepmah.di.PerActivity;
import ru.baltclinic.lliepmah.view.activity.BaseActivity;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


@Module
public class ActivityModule {

    private final BaseActivity mActivity;

    public ActivityModule(BaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    BaseActivity baseActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    Activity Activity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

}
