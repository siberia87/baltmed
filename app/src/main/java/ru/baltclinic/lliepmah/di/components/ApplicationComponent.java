package ru.baltclinic.lliepmah.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.baltclinic.lliepmah.di.modules.ApplicationModule;
import ru.baltclinic.lliepmah.service.network.NetworkService;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    NetworkService networkService();
}
