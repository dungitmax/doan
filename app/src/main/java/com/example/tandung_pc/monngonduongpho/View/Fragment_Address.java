package com.example.tandung_pc.monngonduongpho.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tandung_pc.monngonduongpho.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by TANDUNG-PC on 3/18/2018.
 */

public class Fragment_Address extends Fragment implements OnMapReadyCallback {
    private static final String KEY_MAP_SAVED_STATE = "mapState";
    private MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        mMapView = view.findViewById(R.id.mapView);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
