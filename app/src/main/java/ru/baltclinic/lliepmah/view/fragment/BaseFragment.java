package ru.baltclinic.lliepmah.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.activity.BaseActivity;
import ru.baltclinic.lliepmah.view.activity.MainActivity;
import ru.baltclinic.lliepmah.view.navigator.Navigator;

import static com.balysv.materialmenu.MaterialMenuDrawable.IconState.ARROW;
import static com.balysv.materialmenu.MaterialMenuDrawable.IconState.BURGER;



public abstract class BaseFragment extends Fragment {

    public MaterialMenuDrawable mMaterialMenu;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTitle();
        updateMenuSelection();
        createMaterialMenu();
        mMaterialMenu.setVisible(!isTablet());
        getToolbar().setNavigationIcon(mMaterialMenu);
    }

    private void createMaterialMenu() {
        mMaterialMenu = new MaterialMenuDrawable(getContext(), ContextCompat.getColor(getContext(), R.color.white),
                MaterialMenuDrawable.Stroke.THIN);
    }

    public void hideSoftKeyboard() {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            ((BaseActivity) activity).hideSoftKeyboard();
        }
    }

    protected void setTitle(@StringRes int titleRes) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(titleRes);
        }
    }

    protected void setTitle(String title) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(title);
        }
    }

    @Nullable
    protected Navigator getNavigator() {
        FragmentActivity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            return ((BaseActivity) activity).getNavigator();
        }
        return null;
    }

    private void updateMenuSelection() {
        FragmentActivity activity = getActivity();
        if (MainActivity.class.isInstance(activity)) {
            ((MainActivity) activity).setSelection(getMenuIdentifier());
        }
    }

    public NetworkService getNetworkService() {
        Activity activity = getActivity();
        if (MainActivity.class.isInstance(activity)) {
            MainActivity mainActivity = (MainActivity) activity;
            return mainActivity.getNetworkService();
        }
        return null;
    }

    public AppCompatActivity getBaseActivity() {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            return (BaseActivity) activity;
        }
        return null;
    }

    public void updateTitle() {
        initToolbar();
        updateMenuSelection();
        setTitle(getTitle());

        boolean isRootFragment = isRootFragment();
        if (mMaterialMenu != null) {
            mMaterialMenu.animateIconState(isRootFragment ? BURGER : ARROW);
        }
    }

    protected boolean isRootFragment() {
        Activity activity = getActivity();
        if (BaseActivity.class.isInstance(activity)) {
            return ((BaseActivity) activity).isRootFragment();
        }
        return false;
    }

    private void initToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar == null) {
            return;
        }

        Activity activity = getActivity();
        if (MainActivity.class.isInstance(activity)) {
            ((MainActivity) activity).initToolbar(toolbar);
        }
    }

    protected boolean isTablet() {
        Activity activity = getActivity();
        return BaseActivity.class.isInstance(activity) && ((BaseActivity) activity).isTablet();
    }

    protected abstract Toolbar getToolbar();

    protected abstract long getMenuIdentifier();

    public abstract String getTitle();

}
