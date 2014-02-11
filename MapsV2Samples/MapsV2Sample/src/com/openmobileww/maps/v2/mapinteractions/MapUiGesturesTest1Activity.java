/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapinteractions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.openmobileww.maps.v2.R;
/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user buttons for toggling UI gestures such as rotate, zoom, scroll, tilt.
 */
public class MapUiGesturesTest1Activity extends FragmentActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_gestures_test_part1);

        initializeMap();
        initializeList();
        initializeAllMapGestures();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
    }

    private void initializeList() {
        final ListView gesturesList = (ListView) findViewById(R.id.gestures_list);
        final String[] items = getResources().getStringArray(R.array.map_ui_gestures);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, items);
        gesturesList.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount(); i++) {
            gesturesList.setItemChecked(i, true);
        }

        gesturesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView,
                                    final View view,
                                    final int position, final long l) {
                switchMapGestures(position);
            }
        });
    }

    // Could be implemented a lot easier, complicated just in order to use
    // is...GestureEnabled() methods.
    private void switchMapGestures(final int position) {
        final UiSettings uiSettings = googleMap.getUiSettings();

        switch (position) {
            case 0:
                uiSettings.setRotateGesturesEnabled(
                        !uiSettings.isRotateGesturesEnabled());
                break;
            case 1:
                uiSettings.setScrollGesturesEnabled(
                        !uiSettings.isScrollGesturesEnabled());
                break;
            case 2:
                uiSettings.setTiltGesturesEnabled(
                        !uiSettings.isTiltGesturesEnabled());
                break;
            case 3:
                uiSettings.setZoomGesturesEnabled(
                        !uiSettings.isZoomGesturesEnabled());
                uiSettings.setZoomControlsEnabled(
                        !uiSettings.isZoomControlsEnabled());
                break;
        }
    }

    private void initializeAllMapGestures() {
        final UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
    }
}