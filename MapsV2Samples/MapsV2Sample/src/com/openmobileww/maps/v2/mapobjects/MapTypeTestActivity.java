/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapobjects;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.openmobileww.maps.v2.R;
/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user buttons for changing map type. Available types:
 * MAP_TYPE_NORMAL
 * MAP_TYPE_SATELLITE
 * MAP_TYPE_TERRAIN
 * MAP_TYPE_HYBRID
 */
public class MapTypeTestActivity extends FragmentActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_types_test);

        initializeMap();
        setButtonsOptions();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
    }

    private void setButtonsOptions() {
        final View mapTypeNormalButton = findViewById(R.id.tab_normal);
        final View mapTypeSatelliteButton = findViewById(R.id.tab_satellite);
        final View mapTypeTerrainButton = findViewById(R.id.tab_terran);
        final View mapTypeHybridButton = findViewById(R.id.tab_hybrid);

        mapTypeNormalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMap.getMapType() != GoogleMap.MAP_TYPE_NORMAL) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        mapTypeSatelliteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMap.getMapType() != GoogleMap.MAP_TYPE_SATELLITE)
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        mapTypeTerrainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMap.getMapType() != GoogleMap.MAP_TYPE_TERRAIN) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });

        mapTypeHybridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMap.getMapType() != GoogleMap.MAP_TYPE_HYBRID) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });
    }
}