/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapinteractions;

import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * User gets notified when some map event occurs while running Activity.
 * List of possible events:
 * - map loaded event
 * - camera position changed event
 * - map click event
 * - map long click event
 * - user location button click event
 * - user location changed event
 * <p/>
 * When one of this events occurs appears Toast with appropriate message. Only exception
 * is "user location changed" event - when such event occurs square at the top left
 * corner of  screen will start blinking for a few moments.
 */
public class MapCallbacksTestActivity extends FragmentActivity {

    private static final String MAP_LOADED_MESSAGE = "Map loaded";
    private static final String MAP_CAMERA_CHANGE_MESSAGE = "Camera position changed";
    private static final String MAP_CLICK_MESSAGE = "Map click";
    private static final String MAP_LONG_CLICK_MESSAGE = "Map long click";
    private static final String MAP_LOCATION_BUTTON_CLICK = "User location button click";

    private GoogleMap googleMap;
    private View locationChangeIndicator;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_callbacks_test);

        locationChangeIndicator = findViewById(R.id.location_change_indicator);
        initializeMap();
        setMapCallbacks();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
    }

    private void setMapCallbacks() {
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                showToastMessage(MAP_LOADED_MESSAGE);
            }
        });

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(final CameraPosition cameraPosition) {
                showToastMessage(MAP_CAMERA_CHANGE_MESSAGE);
            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                showToastMessage(MAP_CLICK_MESSAGE);
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                showToastMessage(MAP_LONG_CLICK_MESSAGE);
            }
        });

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                showToastMessage(MAP_LOCATION_BUTTON_CLICK);
                return false;
            }
        });

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(final Location location) {
                enableLocationChangeIndicator();
            }
        });
    }

    private void showToastMessage(final String message) {
        final Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void enableLocationChangeIndicator() {
        final CountDownTimer timer = new CountDownTimer(500, 100) {
            @Override
            public void onTick(final long l) {
                if (locationChangeIndicator.getVisibility() == View.VISIBLE) {
                    locationChangeIndicator.setVisibility(View.INVISIBLE);
                } else {
                    locationChangeIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                locationChangeIndicator.setVisibility(View.VISIBLE);
                locationChangeIndicator.setBackgroundColor(
                        getResources().getColor(R.color.grey));
            }
        };

        locationChangeIndicator.setBackgroundColor(
                getResources().getColor(R.color.green));
        timer.start();
    }
}