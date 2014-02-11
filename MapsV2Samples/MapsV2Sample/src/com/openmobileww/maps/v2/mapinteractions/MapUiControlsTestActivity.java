/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapinteractions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides for user buttons for toggling Map UI controls such as zoom,
 * compass and "My location" button.
 */
public class MapUiControlsTestActivity extends FragmentActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_controls_test);

        initializeMap();
        setButtonOption();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
    }

    private void setButtonOption() {
        final TextView switchZoomControlsButton = (TextView) findViewById(R.id
                .switch_zoom_controls);
        final TextView switchCompassButton = (TextView) findViewById(R.id.switch_compass);
        final TextView switchMyLocationControlsButton = (TextView) findViewById(R.id
                .switch_my_location_controls);

        final UiSettings uiSettings = googleMap.getUiSettings();

        switchZoomControlsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (uiSettings.isZoomControlsEnabled()) {
                    uiSettings.setZoomControlsEnabled(false);
                    switchZoomControlsButton.setText(
                            getString(R.string.turn_on_zoom_controls));

                    switchZoomControlsButton.setBackgroundColor(getResources().getColor
                            (R.color.grey));
                } else {
                    uiSettings.setZoomControlsEnabled(true);
                    switchZoomControlsButton.setText(
                            getString(R.string.turn_off_zoom_controls));

                    switchZoomControlsButton.setBackgroundColor(getResources().getColor
                            (R.color.blue));
                }
            }
        });

        switchCompassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (uiSettings.isCompassEnabled()) {
                    uiSettings.setCompassEnabled(false);
                    switchCompassButton.setText(getString(R.string.turn_on_compass));

                    switchCompassButton.setBackgroundColor(getResources().getColor
                            (R.color.grey));
                } else {
                    uiSettings.setCompassEnabled(true);
                    switchCompassButton.setText(getString(R.string.turn_off_compass));

                    switchCompassButton.setBackgroundColor(getResources().getColor
                            (R.color.blue));
                }
            }
        });

        switchMyLocationControlsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (uiSettings.isMyLocationButtonEnabled()) {
                    uiSettings.setMyLocationButtonEnabled(false);
                    switchMyLocationControlsButton.setText(
                            getString(R.string.turn_on_user_location_controls));

                    switchMyLocationControlsButton.setBackgroundColor(getResources().getColor
                            (R.color.grey));
                } else {
                    uiSettings.setMyLocationButtonEnabled(true);
                    switchMyLocationControlsButton.setText(
                            getString(R.string.turn_off_user_location_controls));

                    switchMyLocationControlsButton.setBackgroundColor(getResources().getColor
                            (R.color.blue));
                }
            }
        });
    }
}