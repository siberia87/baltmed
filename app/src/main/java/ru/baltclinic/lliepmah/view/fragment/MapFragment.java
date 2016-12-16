package ru.baltclinic.lliepmah.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.util.Constants;
import ru.baltclinic.lliepmah.util.PermissionUtils;

public class MapFragment extends BaseFragment
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final String KEY_ADDRESS_POSITION = "key_address_position";
    private static final String KEY_WITH_ROUTE = "key_with_route";

    private GoogleMap mMap;

    @BindView(R.id.fmt_map_m)
    View mMapView;

    @BindView(R.id.v_address_tv_address)
    TextView mTvAddress;
    @BindView(R.id.v_address_tv_place)
    TextView mTvPlace;
    @BindView(R.id.v_address_tv_phone_numbers)
    TextView mTvPhoneNumber;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mShowPermissionDeniedDialog = false;

    private LatLng mAddressCoordinates;

    private int mPositionNumber;
    private boolean mWithRoute;

    public static Fragment newInstance(int addressPosition, boolean withRoute) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ADDRESS_POSITION, addressPosition);
        bundle.putBoolean(KEY_WITH_ROUTE, withRoute);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_map, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.fmt_map_btn_plus)
    void onBtnPlusClick() {
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    @OnClick(R.id.fmt_map_btn_minus)
    void onBtnMinusClick() {
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWithRoute = getArguments().getBoolean(KEY_WITH_ROUTE);
        mPositionNumber = getArguments().getInt(KEY_ADDRESS_POSITION, 0);

        mAddressCoordinates = Constants.ADDRESSES[mPositionNumber];

        initFields(mPositionNumber);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fmt_map_m);
        mapFragment.getMapAsync(this);

    }

    private void initFields(int pos) {
        Resources resources = getResources();

        mTvAddress.setText(resources.getStringArray(R.array.contact_addresses)[pos]);
        mTvPlace.setText(resources.getStringArray(R.array.contact_places)[pos]);

        String phones = resources.getStringArray(R.array.contact_phones)[pos];
        boolean isEmptyPhones = TextUtils.isEmpty(phones);
        mTvPhoneNumber.setText(phones);

        mTvPhoneNumber.setVisibility(isEmptyPhones ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.getUiSettings().setZoomControlsEnabled(false);
        toPosition(mAddressCoordinates);
        updateMyLocation();
    }

    private void toPosition(LatLng position) {
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position).zoom(ContactDetailFragment.MAP_ZOOM).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(getActivity(), R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void updateMyLocation() {
        if (!checkReady()) {
            return;
        }

        if (ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            PermissionUtils.requestPermission(getBaseActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, results,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
        } else {
            mShowPermissionDeniedDialog = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShowPermissionDeniedDialog) {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(false).show(getChildFragmentManager(), "dialog");
            mShowPermissionDeniedDialog = false;
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