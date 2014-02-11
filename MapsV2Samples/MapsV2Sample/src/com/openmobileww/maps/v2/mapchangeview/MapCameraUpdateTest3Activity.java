/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapchangeview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user buttons for animating camera.
 */
public class MapCameraUpdateTest3Activity extends FragmentActivity {

    private static final String GOOGLE_PLAY_SERVICES_NOT_AVAILABLE_MESSAGE = "Google " +
            "play services not available";
    private static final LatLng coordinates1 = new LatLng(37.4, -122.1);
    private static final LatLng coordinates2 = new LatLng(-34, 149);

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_update_test_part3);

        try {
            MapsInitializer.initialize(this);
            initializeMap();
            initializeButtons();
        } catch (final GooglePlayServicesNotAvailableException exception) {
            final Toast toast = Toast.makeText(this,
                    GOOGLE_PLAY_SERVICES_NOT_AVAILABLE_MESSAGE,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
    }

    private void initializeButtons() {
        final View newCameraPositionButton = findViewById(R.id.new_camera_position);
        final View newLatLngButton = findViewById(R.id.new_lat_lng);

        newCameraPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
                        (createCameraPosition(coordinates1)));
            }
        });

        newLatLngButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(coordinates2));
            }
        });
    }

    private CameraPosition createCameraPosition(final LatLng latLng) {
        return new CameraPosition.Builder()
                .target(latLng)
                .zoom(5)
                .bearing(30)
                .tilt(0)
                .build();
    }
}