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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;
/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * At the Activity start animates to point, where indoor map is available. Indoor map
 * can be turned off/on by clicking on "Disable indoor"/"Enable indoor" button.
 */
public class MapIndoorTestActivity extends FragmentActivity {

    private static final float bearing = 337.71f;
    private static final float tilt = 64.75f;
    private static final float zoom = 17.126f;
    private static final double latitude = 51.5558;
    private static final double longitude = -0.27936;

    private GoogleMap googleMap;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_indoor_test);

        initializeMap();
        initializeButton();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.setIndoorEnabled(true);

        final LatLng target = new LatLng(latitude, longitude);
        final CameraPosition cameraPosition = new CameraPosition(target,
                zoom,
                tilt,
                bearing);
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void initializeButton() {
        final Button toggleIndoorButton = (Button) findViewById
                (R.id.toggle_indoor);

        toggleIndoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMap.isIndoorEnabled()) {
                    googleMap.setIndoorEnabled(false);
                    toggleIndoorButton.setText(
                            getString(R.string.enable_indoor));

                    toggleIndoorButton.setBackgroundColor(getResources().getColor
                            (R.color.grey));
                } else {
                    googleMap.setIndoorEnabled(true);
                    toggleIndoorButton.setText(
                            getString(R.string.disable_indoor));

                    toggleIndoorButton.setBackgroundColor(getResources().getColor
                            (R.color.blue));
                }
            }
        });
    }
}