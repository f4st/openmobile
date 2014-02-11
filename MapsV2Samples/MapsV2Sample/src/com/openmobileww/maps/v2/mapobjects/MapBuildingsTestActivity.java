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
 * When activity starts, performed animation to point on map where user can see
 * buildings. Activity provides button to user for toggling buildings on map.
 */
public class MapBuildingsTestActivity extends FragmentActivity {

    private static final float bearing = 345.71f;
    private static final float tilt = 67.5f;
    private static final float zoom = 17.492f;
    private static final double latitude = 40.715;
    private static final double longitude = -74.0027;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings_test);

        initializeMap();
        initializeButton();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.setBuildingsEnabled(true);

        final LatLng target = new LatLng(latitude, longitude);
        final CameraPosition cameraPosition = new CameraPosition(target,
                zoom,
                tilt,
                bearing);
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void initializeButton() {
        final Button toggleBuildingsButton = (Button) findViewById
                (R.id.toggle_buildings);

        toggleBuildingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMap.isBuildingsEnabled()) {
                    googleMap.setBuildingsEnabled(false);
                    toggleBuildingsButton.setText(
                            getString(R.string.enable_buildings));

                    toggleBuildingsButton.setBackgroundColor(getResources().getColor
                            (R.color.grey));
                } else {
                    googleMap.setBuildingsEnabled(true);
                    toggleBuildingsButton.setText(
                            getString(R.string.disable_buildings));

                    toggleBuildingsButton.setBackgroundColor(getResources().getColor
                            (R.color.blue));
                }
            }
        });
    }
}