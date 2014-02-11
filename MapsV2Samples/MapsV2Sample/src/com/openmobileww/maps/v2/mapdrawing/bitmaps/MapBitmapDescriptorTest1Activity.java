/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapdrawing.bitmaps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;

/**
 * Allows user to draw bitmap on map using BitmapDescriptorFactory API.
 */
public class MapBitmapDescriptorTest1Activity extends FragmentActivity {

    private static final int WIDTH = 6000000;
    private static final int HEIGHT = 6000000;
    private static final LatLng POSITION = new LatLng(0, 0);

    private GoogleMap googleMap;
    private GroundOverlay groundOverlay;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_descriptor_test);

        initializeMap();
        initializeButtons();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
    }

    private void initializeButtons() {
        final View drawBitmapButton = findViewById(R.id.draw_bitmap);
        drawBitmapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (groundOverlay == null) {
                    final GroundOverlayOptions options = new GroundOverlayOptions()
                            .image(getBitmapDescriptor())
                            .position(POSITION, WIDTH, HEIGHT);
                    groundOverlay = googleMap.addGroundOverlay(options);
                }
            }
        });

        final View clearMapButton = findViewById(R.id.clear_map);
        clearMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.clear();
                groundOverlay = null;
            }
        });
    }

    private BitmapDescriptor getBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.orange_icon);
    }
}