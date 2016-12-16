package ru.baltclinic.lliepmah.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.MenuItem;
import ru.baltclinic.lliepmah.view.activity.MainActivity;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.holders.MenuViewHolder;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


public class MenuFragment extends Fragment {

    private boolean mHideHeader = false;

    @Nullable
    private Navigator mNavigator = null;
    @Nullable
    private List<MenuItem> mMenuItems = null;
    @Nullable
    private UniversalAdapter mAdapter = null;
    @Nullable
    private DrawerLayout mDrawer = null;
    @Nullable
    private SharedPreferences mPreferences = null;

    public static MenuFragment newInstance() {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.items_container)
    RecyclerView mRvItemsContainer;

    @BindView(R.id.menu_header)
    ViewGroup mMenuHeader;

    SharedPreferences.OnSharedPreferenceChangeListener mPrefsChangesListener = (prefs, key) -> {
        if (MainActivity.KEY_PUSH_MESSAGES.equals(key)) {
            int messages = prefs.getInt(MainActivity.KEY_PUSH_MESSAGES, 0);
            updateBadge(R.id.menu_notifications, messages);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_menu, container, false);
        ButterKnife.bind(this, fragmentView);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        mPreferences.registerOnSharedPreferenceChangeListener(mPrefsChangesListener);

        mMenuItems = buildMenuItems();


        mAdapter = new UniversalAdapter(new MenuViewHolder.Builder(this::onMenuClick));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvItemsContainer.setLayoutManager(layoutManager);
        mRvItemsContainer.setAdapter(mAdapter);

        mAdapter.addNewItems(mMenuItems, MenuViewHolder.Builder.class);
        int messages = mPreferences.getInt(MainActivity.KEY_PUSH_MESSAGES, 0);
        updateBadge(R.id.menu_notifications, messages);

        mMenuHeader.setVisibility(mHideHeader ? View.GONE : View.VISIBLE);

        setSelection(R.id.menu_main);
        return fragmentView;
    }

    private void onMenuClick(MenuItem menuItem) {
        if (mNavigator == null) {
            return;
        }

        switch (menuItem.getId()) {
            case R.id.menu_main:
                mNavigator.openMain();
                break;
            case R.id.menu_services:
                mNavigator.openServices();
                break;
            case R.id.menu_specialists:
                mNavigator.openSpecialists();
                break;
            case R.id.menu_gallery:
                mNavigator.openGallery();
                break;
            case R.id.menu_prices:
                mNavigator.openPrices();
                break;
            case R.id.menu_news:
                mNavigator.openNews();
                break;
            case R.id.menu_actions:
                mNavigator.openActions();
                break;
            case R.id.menu_contacts:
                mNavigator.openContacts();
                break;
            case R.id.menu_notifications:
                mNavigator.openNotifications();
                break;
            default:
                mNavigator.openMain();
                break;
        }

        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.LEFT);
        }

    }

    private List<MenuItem> buildMenuItems() {

        return new ArrayList<MenuItem>() {{
            add(new MenuItem(R.id.menu_main, R.string.ttl_main, R.drawable.menu_main));
            add(new MenuItem(R.id.menu_services, R.string.ttl_services, R.drawable.menu_service));
            add(new MenuItem(R.id.menu_specialists, R.string.ttl_specialists, R.drawable.menu_doctor));
            add(new MenuItem(R.id.menu_gallery, R.string.ttl_gallery, R.drawable.menu_gallery));
            add(new MenuItem(R.id.menu_prices, R.string.ttl_prices, R.drawable.menu_price));
            add(new MenuItem(R.id.menu_news, R.string.ttl_news, R.drawable.menu_news));
            add(new MenuItem(R.id.menu_actions, R.string.ttl_actions, R.drawable.menu_action));
            add(new MenuItem(R.id.menu_contacts, R.string.ttl_contacts, R.drawable.menu_contact));
            add(new MenuItem(R.id.menu_notifications, R.string.ttl_notifications, R.drawable.menu_push));
        }};

    }

    public void hideHeader() {
        mHideHeader = true;
    }

    public void setSelection(long selection) {
        if (mAdapter == null || mMenuItems == null) {
            return;
        }

        for (MenuItem menuItem : mMenuItems) {
            menuItem.setSelected(menuItem.getId() == selection);
        }
        mAdapter.notifyDataSetChanged();

    }

    private void updateBadge(int menuId, int count) {
        if (mAdapter == null || mMenuItems == null) {
            return;
        }

        for (MenuItem menuItem : mMenuItems) {
            if (menuItem.getId() == menuId) {
                menuItem.setBadgeValue(count);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setNavigator(Navigator navigator) {
        mNavigator = navigator;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.mDrawer = drawer;
        if (mDrawer == null) {
            return;
        }

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
                int messages = prefs.getInt(MainActivity.KEY_PUSH_MESSAGES, 0);
                updateBadge(R.id.menu_notifications, messages);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    public Context getApplication() {
        return getActivity().getApplication();
    }
}
