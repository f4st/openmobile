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
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user buttons for animating camera.
 */
public class MapCameraUpdateTest2Activity extends FragmentActivity {

    private static final String GOOGLE_PLAY_SERVICES_NOT_AVAILABLE_MESSAGE = "Google " +
            "play services not available";

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_update_test_part2);

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
        final View zoomIn = findViewById(R.id.zoom_in);
        final View zoomOut = findViewById(R.id.zoom_out);
        final View zoomTo = findViewById(R.id.zoom_to);

        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            }
        });

        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.moveCamera(CameraUpdateFactory.zoomOut());
            }
        });

        zoomTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(4f));
            }
        });
    }
}