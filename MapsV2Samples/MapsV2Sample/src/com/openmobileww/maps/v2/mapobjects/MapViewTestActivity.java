/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapobjects;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.openmobileww.maps.v2.R;
/**
 * Activity which uses MapView for displaying GoogleMap. Also in this Activity where
 * used APIs for setting Map padding and getting map max and min zoom level.
 */
public class MapViewTestActivity extends Activity {

    private static final String MAX_ZOOM_LEVEL = "Map max zoom level: ";
    private static final String MIN_ZOOM_LEVEL = "Map min zoom level: ";

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view_test);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        googleMap = mapView.getMap();

        initializeMap();
        initializeButtons();
    }

    private void initializeMap() {
        googleMap.setPadding(50, 50, 50, 50);
        googleMap.setMyLocationEnabled(true);
    }

    private void initializeButtons() {
        final View getMinZoomLevelButton = findViewById(R.id.get_min_zoom_level);
        final View getMaxZoomLevelButton = findViewById(R.id.get_max_zoom_level);

        getMinZoomLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Toast toast = Toast.makeText(MapViewTestActivity.this,
                        MIN_ZOOM_LEVEL + googleMap.getMinZoomLevel(),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        getMaxZoomLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Toast toast = Toast.makeText(MapViewTestActivity.this,
                        MAX_ZOOM_LEVEL + googleMap.getMaxZoomLevel(),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}