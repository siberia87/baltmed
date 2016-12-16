package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Doctor;
import ru.baltclinic.lliepmah.service.network.NetworkService;
import ru.baltclinic.lliepmah.view.list.adapters.UniversalAdapter;
import ru.baltclinic.lliepmah.view.list.decoration.SimpleDividerItemDecoration;
import ru.baltclinic.lliepmah.view.list.holders.DoctorViewHolder;
import ru.baltclinic.lliepmah.view.navigator.Navigator;
import rx.Observable;


public class DoctorsFragment extends SearchListFragment<Doctor> {

    private List<Doctor> mDoctors;

    public static Fragment newInstance() {
        return new DoctorsFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new UniversalAdapter(new DoctorViewHolder.Builder(this::onDoctorClick));

        mRvMain.setAdapter(mAdapter);
        mRvMain.addItemDecoration(new SimpleDividerItemDecoration(getContext(), isGridList()));

        makeRequest(false);
    }

    private void onDoctorClick(Doctor doctor) {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openDoctor(doctor);
        }
    }

    @Override
    protected void onLoaded(List<Doctor> doctors) {
        super.onLoaded(doctors);
        mDoctors = doctors;

        replaceItems(doctors);
    }

    private void replaceItems(List<Doctor> doctors) {
        mAdapter.removeItems(0, mAdapter.getItemCount() - 1);
        mAdapter.addNewItems(doctors, DoctorViewHolder.Builder.class);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected Observable<List<Doctor>> getObservable() {
        NetworkService networkService = getNetworkService();
        if (networkService != null) {
            return networkService.getDoctors();
        }
        return null;
    }

    @Override
    public int getRequestId() {
        return R.id.loader_load_doctors;
    }

    @Override
    protected void search(String searchText) {
        List<Doctor> doctors = Stream.of(mDoctors)
                .filter(value -> value.hasString(searchText))
                .collect(Collectors.toList());
        replaceItems(doctors);
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_specialists;
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_specialists);
    }

    @Override
    public boolean isGridList() {
        return isTablet();
    }
}
