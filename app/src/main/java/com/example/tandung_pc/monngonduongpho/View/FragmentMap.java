package com.example.tandung_pc.monngonduongpho.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tandung_pc.monngonduongpho.Adapter.PlaceAutocompleteAdapter;
import com.example.tandung_pc.monngonduongpho.GPSTracker.GPSTracker;
import com.example.tandung_pc.monngonduongpho.Model.ModelFindTheWay.DirectionFinder;
import com.example.tandung_pc.monngonduongpho.Model.ModelFindTheWay.DirectionFinderListener;
import com.example.tandung_pc.monngonduongpho.Model.ModelFindTheWay.Route;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FragmentMap extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, DirectionFinderListener,
        GoogleMap.OnMyLocationClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String KEY_MAP_SAVED_STATE = "mapState";
    private static final String TAG = "FragmentMap";
    private static final CharSequence[] MAP_TYPE_ITEMS =
            {"Road Map", "Hybrid", "Satellite", "Terrain"};
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    MapView mMapView;
    GoogleMap googleMap;
    double latitude;
    double longitude;
    private LinearLayout line;
    private ProgressDialog progressDialog;
    private List<Marker> originMarkers;
    private List<Marker> destinationMarkers;
    private List<Polyline> polylinePaths;
    private GPSTracker gps;
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    private PlaceAutocompleteAdapter mAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private LatLng myLocation;
    private TextView tvDuration, tvDistance;
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
//            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
//                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace.getName());

            places.release();
        }
    };
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboardd(getActivity());
            final AutocompletePrediction item = mAdapter.getItem(i);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            sendRequest();
        }
    };

    public static void hideSoftKeyboardd(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.mapView);
        mSearchText = view.findViewById(R.id.input_search);
        tvDistance = view.findViewById(R.id.tvDistance);
        tvDuration = view.findViewById(R.id.tvDuration);
        line = view.findViewById(R.id.line);
        line.setVisibility(View.INVISIBLE);
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
                    myLocation = new LatLng(latitude, longitude);
                    //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                    //googleMap.addMarker(new MarkerOptions().position(myLocation).title("Vị trí của tôi"));
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.choose_type_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.chooseTypeMap) {
            showMapTypeSelectorDialog();
        }
        return super.onOptionsItemSelected(item);
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
                    // googleMap.addMarker(new MarkerOptions().position(myLocation).title("Vị trí của tôi"));
                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(16).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {
                    gps.showSettingsAlert();
                }
            }
        });

    }

    private void sendRequest() {
        String origin = myLocation.latitude + ", " + myLocation.longitude;
        String destination = mSearchText.getText().toString();
        if (destination.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập địa chỉ !", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
            // moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
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

    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = "Chọn kiểu bản đồ";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = googleMap.getMapType() - 1;

        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.

                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 1:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            default:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                        dialog.dismiss();
                    }
                }
        );

        // Build the dialog and show it.
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(getActivity(), "Vui lòng chờ...",
                "Đang tìm đường...!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        line.setVisibility(View.VISIBLE);
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 15));
            tvDuration.setText(route.duration.text);
            tvDistance.setText(route.distance.text);

            originMarkers.add(googleMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(googleMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(googleMap.addPolyline(polylineOptions));
        }
    }
}
