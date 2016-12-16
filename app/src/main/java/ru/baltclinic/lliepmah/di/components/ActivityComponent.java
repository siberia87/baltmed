package ru.baltclinic.lliepmah.di.components;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import ru.baltclinic.lliepmah.di.PerActivity;
import ru.baltclinic.lliepmah.di.modules.ActivityModule;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.activity.BaseActivity;
import ru.baltclinic.lliepmah.view.activity.MainActivity;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


@PerActivity
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    void inject(BaseActivity activity);

    void inject(MainActivity activity);

    //  Exposed to sub-graphs.
    BaseActivity baseActivity();

    Activity activity();

    Navigator navigator();

    Context context();


}
