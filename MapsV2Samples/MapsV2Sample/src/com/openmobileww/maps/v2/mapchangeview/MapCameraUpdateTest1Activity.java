/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapchangeview;

import android.graphics.Point;
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
public class MapCameraUpdateTest1Activity extends FragmentActivity {

    private static final String GOOGLE_PLAY_SERVICES_NOT_AVAILABLE_MESSAGE = "Google " +
            "play services not available";

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_update_test_part1);

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
        final View scrollBy = findViewById(R.id.scroll_by);
        final View zoomBy1 = findViewById(R.id.zoom_by_1);
        final View zoomBy2 = findViewById(R.id.zoom_by_2);

        scrollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.scrollBy(200, 0));
            }
        });

        zoomBy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.zoomBy(1.1f, new Point()));
            }
        });

        zoomBy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.zoomBy(-1.1f));
            }
        });
    }
}