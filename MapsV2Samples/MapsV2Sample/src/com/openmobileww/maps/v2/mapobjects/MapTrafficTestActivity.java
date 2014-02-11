/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapobjects;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;
/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user button for toggling traffic on map.
 */
public class MapTrafficTestActivity extends FragmentActivity {

    private static final float zoom = 9.22f;
    private static final float latitude = 52.49f;
    private static final float longitude = 13.39f;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_test);

        initializeMap();
        initializeButton();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.setTrafficEnabled(true);

        final LatLng latLng = new LatLng(latitude, longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initializeButton() {
        final Button toggleTrafficButton = (Button) findViewById
                (R.id.toggle_traffic);

        toggleTrafficButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMap.isTrafficEnabled()) {
                    googleMap.setTrafficEnabled(false);
                    toggleTrafficButton.setText(getString(R.string.enable_traffic));

                    toggleTrafficButton.setBackgroundColor(getResources().getColor
                            (R.color.grey));
                } else {
                    googleMap.setTrafficEnabled(true);
                    toggleTrafficButton.setText(getString(R.string.disable_traffic));

                    toggleTrafficButton.setBackgroundColor(getResources().getColor
                            (R.color.blue));
                }
            }
        });
    }
}