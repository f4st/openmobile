/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapinteractions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user buttons for enabling/disabling all UI gestures.
 */
public class MapUiGesturesTest2Activity extends FragmentActivity {

    private GoogleMap googleMap;
    private boolean disabled;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_gestures_test_part2);

        initializeMap();
        initializeButton();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
    }

    private void initializeButton() {
        final View switchAllGesturesButton = findViewById
                (R.id.switch_all_gestures_button);

        switchAllGesturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                switchAllMapGestures(view);
            }
        });
    }

    private void switchAllMapGestures(final View view) {
        final Button button = (Button) view;

        if (disabled) {
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            button.setBackgroundColor(getResources().getColor(R.color.blue));
            button.setText(getString(R.string.disable_all_gestures));
            disabled = false;
        } else {
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            button.setBackgroundColor(getResources().getColor(R.color.grey));
            button.setText(getString(R.string.enable_all_gestures));
            disabled = true;
        }
    }
}