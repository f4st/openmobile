/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.locationdata;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Activity uses custom LocationSource for determining user location.
 */
public class MapLocationSourceTestActivity extends FragmentActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_souce_test);

        initializeMap();
        initializeButton();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();

        googleMap.setLocationSource(new CurrentLocationProvider(this));
        if (!googleMap.isMyLocationEnabled()) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void initializeButton() {
        final View getUserLocationButton = findViewById(R.id.get_user_location);
        getUserLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showUserLocationMessage();
            }
        });
    }

    private void showUserLocationMessage() {
        final Location location = googleMap.getMyLocation();

        final Toast toast = Toast.makeText(this,
                location.getLatitude() + " " + location.getLongitude(),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static class CurrentLocationProvider implements LocationSource,
            LocationListener {

        private final LocationManager locationManager;
        private OnLocationChangedListener listener;

        private CurrentLocationProvider(final Context context) {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
        }

        @Override
        public void activate(final OnLocationChangedListener onLocationChangedListener) {
            listener = onLocationChangedListener;

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    this);
        }

        @Override
        public void deactivate() {
            locationManager.removeUpdates(this);
        }

        @Override
        public void onLocationChanged(final Location location) {
            listener.onLocationChanged(location);
        }

        @Override
        public void onStatusChanged(final String s, final int i, final Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(final String s) {
        }

        @Override
        public void onProviderDisabled(final String s) {
        }
    }
}