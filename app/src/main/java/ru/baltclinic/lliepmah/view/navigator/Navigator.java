package ru.baltclinic.lliepmah.view.navigator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Action;
import ru.baltclinic.lliepmah.models.Appointment;
import ru.baltclinic.lliepmah.models.Call;
import ru.baltclinic.lliepmah.models.Doctor;
import ru.baltclinic.lliepmah.models.News;
import ru.baltclinic.lliepmah.models.Service;
import ru.baltclinic.lliepmah.view.activity.BaseActivity;
import ru.baltclinic.lliepmah.view.fragment.ActionFragment;
import ru.baltclinic.lliepmah.view.fragment.ActionsFragment;
import ru.baltclinic.lliepmah.view.fragment.AppointmentFragment;
import ru.baltclinic.lliepmah.view.fragment.CheckCallFragment;
import ru.baltclinic.lliepmah.view.fragment.CheckFragment;
import ru.baltclinic.lliepmah.view.fragment.ContactDetailFragment;
import ru.baltclinic.lliepmah.view.fragment.ContactsFragment;
import ru.baltclinic.lliepmah.view.fragment.DoctorFragment;
import ru.baltclinic.lliepmah.view.fragment.DoctorsFragment;
import ru.baltclinic.lliepmah.view.fragment.GalleryFragment;
import ru.baltclinic.lliepmah.view.fragment.MainFragment;
import ru.baltclinic.lliepmah.view.fragment.MapFragment;
import ru.baltclinic.lliepmah.view.fragment.NewsFragment;
import ru.baltclinic.lliepmah.view.fragment.NotificationsFragment;
import ru.baltclinic.lliepmah.view.fragment.OrderCallFragment;
import ru.baltclinic.lliepmah.view.fragment.PricesFragment;
import ru.baltclinic.lliepmah.view.fragment.ServiceFragment;
import ru.baltclinic.lliepmah.view.fragment.ServicesFragment;
import ru.baltclinic.lliepmah.view.fragment.ServicesPricesFragment;
import ru.baltclinic.lliepmah.view.fragment.SingleNewsFragment;
import rx.subjects.PublishSubject;

public class Navigator {

    private BaseActivity mActivity;

    @Inject
    public Navigator(BaseActivity activity) {
        mActivity = activity;
    }

    public void openMain() {
        Fragment mainFragment = MainFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openActions() {
        Fragment mainFragment = ActionsFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openServices() {
        Fragment mainFragment = ServicesFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openSpecialists() {
        Fragment mainFragment = DoctorsFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openGallery() {
        Fragment mainFragment = GalleryFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openNews() {
        Fragment mainFragment = NewsFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openNews(News news) {
        Fragment mainFragment = NewsFragment.newInstance(news);
        mActivity.pushRoot(mainFragment);
    }

    public void openPrices() {
        Fragment mainFragment = ServicesPricesFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openContacts() {
        Fragment mainFragment = ContactsFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openNotifications() {
        Fragment mainFragment = NotificationsFragment.newInstance();
        mActivity.pushRoot(mainFragment);
    }

    public void openSingleNews(News news) {
        Fragment mainFragment = SingleNewsFragment.newInstance(news);
        mActivity.push(mainFragment);
    }

    public void openService(Service service) {
        Fragment mainFragment = ServiceFragment.newInstance(service);
        mActivity.push(mainFragment);
    }

    public void openPrice(Service service) {
        Fragment mainFragment = PricesFragment.newInstance(service);
        mActivity.push(mainFragment);
    }

    public void openDoctor(Doctor doctor) {
        Fragment mainFragment = DoctorFragment.newInstance(mActivity, doctor);
        mActivity.push(mainFragment);
    }

    public void openAction(Action action) {
        Fragment mainFragment = ActionFragment.newInstance(mActivity, action);
        mActivity.push(mainFragment);
    }

    public void openMakeAppointment() {
        Fragment mainFragment = AppointmentFragment.newInstance();
        mActivity.push(mainFragment);
    }

    public void openCheckAppointment(Appointment appointment) {
        Fragment mainFragment = CheckFragment.newInstance(appointment);
        mActivity.push(mainFragment);
    }

    public void openCheckCall(Call call) {
        Fragment mainFragment = CheckCallFragment.newInstance(call);
        mActivity.push(mainFragment);
    }

    public void openOrderCall() {
        Fragment mainFragment = OrderCallFragment.newInstance();
        mActivity.push(mainFragment);
    }

    public void walkOutFromCheck() {
        mActivity.awayFromCheck();
    }

    public void openSite() {
        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(mActivity.getString(R.string.contact_site)));
        mActivity.startActivity(intent);
    }

    public void openDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(mActivity.getString(R.string.contacts_phone)));
        try {
            mActivity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mActivity, "Невозможно осуществить звонок с этого устройства", Toast.LENGTH_LONG).show();
        }

    }

    public void openContactDetail(int addressNumber) {
        Fragment mainFragment = ContactDetailFragment.newInstance(addressNumber);
        mActivity.push(mainFragment);
    }

    public void openMap(int addressNumber, boolean withRoute) {
        Fragment mainFragment = MapFragment.newInstance(addressNumber, withRoute);
        mActivity.push(mainFragment);
    }

    public void openRoute(LatLng addressCoordinates) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(mActivity.getString(R.string.url_google_maps_route,
                        Double.toString(addressCoordinates.latitude),
                        Double.toString(addressCoordinates.longitude))));

        mActivity.startActivity(intent);
    }
}
