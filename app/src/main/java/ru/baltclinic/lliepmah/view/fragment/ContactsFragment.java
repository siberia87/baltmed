package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


public class ContactsFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Fragment newInstance() {
        ContactsFragment contactsFragment = new ContactsFragment();
        return contactsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_contacts, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.fmt_contacts_tv_call)
    void onCallClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openDialer();
        }
    }

    @OnClick(R.id.fmt_contacts_tv_site)
    void onSiteClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openSite();
        }
    }

    @OnClick(R.id.fmt_contacts_cv_address1)
    void onAddress1Click() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openContactDetail(0);
        }
    }

    @OnClick(R.id.fmt_contacts_cv_address2)
    void onAddress2Click() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openContactDetail(1);
        }
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_contacts;
    }


    @Override
    public String getTitle() {
        return getString(R.string.ttl_contacts);
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

}
