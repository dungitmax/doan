package com.example.tandung_pc.monngonduongpho.View;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tandung_pc.monngonduongpho.Adapter.PlaceAutocompleteAdapter;
import com.example.tandung_pc.monngonduongpho.GPSTracker.GPSTracker;
import com.example.tandung_pc.monngonduongpho.Model.PlaceInfo;
import com.example.tandung_pc.monngonduongpho.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentMap extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String KEY_MAP_SAVED_STATE = "mapState";
    private static final String TAG = "FragmentMap";
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    MapView mMapView;
    GoogleMap googleMap;
    double latitude;
    double longitude;
    private GPSTracker gps;
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    private PlaceAutocompleteAdapter mAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "onResult: Place query did not complete success" + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try {
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                //  mPlace.setAttributions(place.getAttributions().toString());
                mPlace.setId(place.getId());
                mPlace.setLatlng(place.getLatLng());
                mPlace.setRating(place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                mPlace.setWebsiteUri(place.getWebsiteUri());
            } catch (NullPointerException e) {
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
            }
            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace.getName());

            places.release();
        }
    };
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();
            final AutocompletePrediction item = mAdapter.getItem(i);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.mapView);
        mSearchText = view.findViewById(R.id.input_search);
        mGps = view.findViewById(R.id.ic_gps);
        Bundle mapState = (savedInstanceState != null)
                ? savedInstanceState.getBundle(KEY_MAP_SAVED_STATE) : null;
        mMapView.onCreate(mapState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                gps = new GPSTracker(getActivity());

                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                } else {
                    // can't get location
                }
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }


                googleMap.setMyLocationEnabled(true);

                // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                // For dropping a marker at a point on the Map

                final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            if (googleMap.getMyLocation() != null) {
                                //TODO: Any custom actions
                                latitude = googleMap.getMyLocation().getLatitude();
                                longitude = googleMap.getMyLocation().getLongitude();
                                gps.getLocation();
                            } else {
                                gps.showSettingsAlert();
                            }
                            return false;
                        }
                    });
                    LatLng myLocation = new LatLng(latitude, longitude);
                    //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                    googleMap.addMarker(new MarkerOptions().position(myLocation).title("Vị trí của tôi"));
                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {
                    gps.showSettingsAlert();
                }
                init();
            }
        });

        return view;
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            googleMap.addMarker(options);
        }
        hideSoftKeyboard();
    }

    private void init() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        mSearchText.setOnItemClickListener(mOnItemClickListener);
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(mAdapter);
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();

                }
                return false;
            }
        });
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            if (googleMap.getMyLocation() != null) {
                                //TODO: Any custom actions
                                latitude = googleMap.getMyLocation().getLatitude();
                                longitude = googleMap.getMyLocation().getLongitude();
                                gps.getLocation();
                            } else {
                                gps.showSettingsAlert();
                            }
                            return false;
                        }
                    });
                    LatLng myLocation = new LatLng(latitude, longitude);
                    //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                    googleMap.addMarker(new MarkerOptions().position(myLocation).title("Vị trí của tôi"));
                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {
                    gps.showSettingsAlert();
                }

            }
        });
        hideSoftKeyboard();
    }

    private void geoLocate() {
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());

        }
        if (list.size() > 0) {
            Address address = list.get(0);
            // Toast.makeText(getActivity(), address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapState = new Bundle();
        mMapView.onSaveInstanceState(mapState);
        outState.putBundle(KEY_MAP_SAVED_STATE, mapState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    private void hideSoftKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
