package ru.baltclinic.lliepmah.view.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.app.BaltApplication;
import ru.baltclinic.lliepmah.di.components.ActivityComponent;
import ru.baltclinic.lliepmah.di.components.ApplicationComponent;
import ru.baltclinic.lliepmah.di.components.DaggerActivityComponent;
import ru.baltclinic.lliepmah.di.modules.ActivityModule;
import ru.baltclinic.lliepmah.view.fragment.SendFragment;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(isTablet() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(getApplicationComponent())
                .build();

        mActivityComponent.inject(this);

    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.is_tablet);
    }

    public Navigator getNavigator() {
        return mNavigator;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public ApplicationComponent getApplicationComponent() {
        return ((BaltApplication) getApplication()).getApplicationComponent();
    }

    public synchronized boolean isRootFragment() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        return backStackEntryCount <= 0;
    }

    public void hideSoftKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public void popBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void pushRoot(Fragment fragment) {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (IllegalStateException ex) {/*no-op*/}
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, fragment.getClass().getName())
                .commitAllowingStateLoss();
    }

    public void push(Fragment fragment) {
        int container = isTablet() ? R.id.detail : R.id.container;
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(container, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commitAllowingStateLoss();
    }


    public void awayFromCheck() {
        int container = isTablet() ? R.id.detail : R.id.container;

        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.popBackStackImmediate() && SendFragment.class.isInstance(fragmentManager.findFragmentById(container))) {
            fragmentManager.popBackStackImmediate();
        }
    }
}
