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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user buttons for animating camera.
 */
public class MapCameraUpdateTest4Activity extends FragmentActivity {

    private static final String GOOGLE_PLAY_SERVICES_NOT_AVAILABLE_MESSAGE = "Google " +
            "play services not available";
    private static final LatLng coordinates = new LatLng(-34, 149);
    private static final LatLng southWestBound = new LatLng(-27, 37);
    private static final LatLng northEastBound = new LatLng(-11, 56);

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_update_test_part4);

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
        final View newLatLntBounds1Button = findViewById(R.id.new_lat_lng_bounds1);
        final View newLatLntBounds2Button = findViewById(R.id.new_lat_lng_bounds2);
        final View newLatLntZoom = findViewById(R.id.new_lat_lng_zoom);

        newLatLntBounds1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds
                        (createNewLatLngBounds(), 300, 300, 10));
            }
        });

        newLatLntBounds2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds
                        (createNewLatLngBounds(), 10));
            }
        });

        newLatLntZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates,
                        5f));
            }
        });
    }

    private LatLngBounds createNewLatLngBounds() {
        return new LatLngBounds.Builder()
                .include(southWestBound)
                .include(northEastBound)
                .build();
    }
}