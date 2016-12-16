package ru.baltclinic.lliepmah.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.util.Constants;
import ru.baltclinic.lliepmah.view.navigator.Navigator;

public class ContactDetailFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String KEY_ADDRESS_POSITION = "key_address_position";

    public static final int MAP_ZOOM = 16;

    @BindView(R.id.fmt_contact_detail_root)
    ViewGroup mLRoot;
    @BindView(R.id.fmt_contact_detail_mv_address)
    MapView mMapView;
    @BindView(R.id.fmt_contact_detail_tv_route)
    View mTvRoute;
    @BindView(R.id.fmt_contact_detail_l_times)
    View mLTimes;
    @BindView(R.id.fmt_contact_detail_v_address)
    View mVAddress;
    @BindView(R.id.fmt_contact_detail_tv_spec)
    TextView mTvSpec;
    @BindView(R.id.fmt_contact_detail_tv_work_time)
    TextView mTvWorkTime;
    @BindView(R.id.fmt_contact_detail_tv_appointment_time)
    TextView mTvAppointmentTime;
    @BindView(R.id.fmt_contact_detail_tv_analyzes_time)
    TextView mTvAnalyzesTime;

    @BindView(R.id.v_address_tv_address)
    TextView mTvAddress;
    @BindView(R.id.v_address_tv_place)
    TextView mTvPlace;
    @BindView(R.id.v_address_tv_phone_numbers)
    TextView mTvPhoneNumber;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private LatLng mAddressCoordinates;
    private int mPositionNumber;

    public static Fragment newInstance(int addressPosition) {
        ContactDetailFragment mapFragment = new ContactDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ADDRESS_POSITION, addressPosition);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_contact_detail, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mPositionNumber = getArguments().getInt(KEY_ADDRESS_POSITION, 0);
        mAddressCoordinates = Constants.ADDRESSES[mPositionNumber];
        initFields(mPositionNumber);

    }

    private void initFields(int pos) {
        Resources resources = getResources();

        mTvSpec.setText(resources.getStringArray(R.array.contact_specs)[pos]);

        mTvWorkTime.setText(resources.getStringArray(R.array.contact_work_times)[pos]);
        mTvAppointmentTime.setText(resources.getStringArray(R.array.contact_appointment_times)[pos]);
        mTvAnalyzesTime.setText(resources.getStringArray(R.array.contact_analyzes_times)[pos]);

        mTvAddress.setText(resources.getStringArray(R.array.contact_addresses)[pos]);
        mTvPlace.setText(resources.getStringArray(R.array.contact_places)[pos]);

        String phones = resources.getStringArray(R.array.contact_phones)[pos];
        boolean isEmptyPhones = TextUtils.isEmpty(phones);
        mTvPhoneNumber.setText(phones);

        mTvPhoneNumber.setVisibility(isEmptyPhones ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.fmt_contact_detail_v_touchable_wrapper)
    void onMapClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openMap(mPositionNumber, false);
        }
    }

    @OnClick(R.id.fmt_contact_detail_tv_route)
    void onRouteClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openRoute(mAddressCoordinates);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(mAddressCoordinates)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mAddressCoordinates).zoom(MAP_ZOOM).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public String getTitle() {
        return getString(R.string.ttl_contacts);
    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_contacts;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }
}