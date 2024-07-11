package com.example.sce.screen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.sce.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewLocations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locations);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double[] latitudes = extras.getDoubleArray("latitudes");
            double[] longitudes = extras.getDoubleArray("longitudes");

            if (latitudes != null && longitudes != null && latitudes.length == longitudes.length) {
                for (int i = 0; i < latitudes.length; i++) {
                    LatLng location = new LatLng(latitudes[i], longitudes[i]);
                    mMap.addMarker(new MarkerOptions().position(location).title("Marker at " + location));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
                }
            }
        }
    }
}
