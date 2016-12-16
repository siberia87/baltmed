package ru.baltclinic.lliepmah.view.activity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.fragment.BaseFragment;
import ru.baltclinic.lliepmah.view.fragment.MenuFragment;

import static com.balysv.materialmenu.MaterialMenuDrawable.IconState.ARROW;
import static com.balysv.materialmenu.MaterialMenuDrawable.IconState.BURGER;

public class MainActivity extends BaseActivity {

    //    public static final String BALT_MED_PREFS = "baltMed_prefs";
    public static final String KEY_GCM_REGISTERED = "key_gcm_registered";
    public static final String KEY_PUSH_MESSAGES = "key_push_messages";

    @BindView(R.id.container)
    ViewGroup mContainer;

    @BindView(R.id.left_menu)
    FrameLayout mLLeftMenu;

    @Nullable
    private DrawerLayout mDrawerLayout = null;
    @Nullable
    private LinearLayout mBaseContainer = null;

    @Inject
    NetworkService mNetworkService;

    private MenuFragment mFmtMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        View drl = findViewById(R.id.drawer_layout);
        mDrawerLayout = DrawerLayout.class.isInstance(drl) ? (DrawerLayout) drl : null;

        View bc = findViewById(R.id.base_container);
        mBaseContainer = LinearLayout.class.isInstance(bc) ? (LinearLayout) bc : null;

        mFmtMenu = MenuFragment.newInstance();
        mFmtMenu.setNavigator(getNavigator());
        mFmtMenu.setDrawer(mDrawerLayout);

        getActivityComponent().inject(this);

        mNavigator.openMain();

        if (isTablet()) {
            mFmtMenu.hideHeader();
        }

        enableMenu();

        if (!PreferenceManager.getDefaultSharedPreferences(getApplication()).getBoolean(KEY_GCM_REGISTERED, false)) {
            getNetworkService()
                    .refreshToken();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);
        onBackStackChanged();

    }

    public void hideMenu() {
        if (isTablet()) {
            mLLeftMenu.setVisibility(View.GONE);
        } else if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START, true);
        }
    }

    public void showMenu() {
        if (isTablet())
            mLLeftMenu.setVisibility(View.VISIBLE);
        else if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(GravityCompat.START, true);
        }
    }

    public void enableMenu() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.left_menu, mFmtMenu)
                .commit();
        mLLeftMenu.setVisibility(View.VISIBLE);

        if (!isTablet() && mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        }
    }


    public NetworkService getNetworkService() {
        return mNetworkService;
    }

    public void initToolbar(@NonNull Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            if (isRootFragment()) {
                if (mDrawerLayout != null) {
                    if (mDrawerLayout.isDrawerOpen(mLLeftMenu)) {
                        mDrawerLayout.closeDrawer(mLLeftMenu);
                    } else {
                        mDrawerLayout.openDrawer(mLLeftMenu);
                    }
                }

            } else {
                final FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    @Override
    public void setTitle(int titleRes) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(titleRes);
        }
    }

    public void setSelection(long selectionIdentifier) {
        if (mFmtMenu != null) {
            mFmtMenu.setSelection(selectionIdentifier);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }

    private void onBackStackChanged() {
        FragmentManager fmtManager = getSupportFragmentManager();
        Fragment fmt = fmtManager.findFragmentById(R.id.container);


        hideSoftKeyboard();

//        boolean isRootFragment = isRootFragment();

        if (BaseFragment.class.isInstance(fmt)) {
            ((BaseFragment) fmt).updateTitle();
        }

    }

}
