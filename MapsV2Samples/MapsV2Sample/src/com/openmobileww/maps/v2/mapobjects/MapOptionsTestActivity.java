/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapobjects;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;

/**
 * Activity which uses MapView for displaying GoogleMap.
 * <p/>
 * MapView is created dynamically with prepared GoogleMapOptions object. User can check
 * current GoogleMap options by clicking on "Show map options" button.
 */
public class MapOptionsTestActivity extends Activity {

    private static final String DIALOG_TITLE = "Map options";
    private static final String OK_BUTTON = "OK";

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_options_test);

        final GoogleMapOptions options = createMapOptions();

        mapView = new MapView(this, options);
        addMapToView(mapView);
        mapView.onCreate(savedInstanceState);
        googleMap = mapView.getMap();

        initializeButtons(options);
    }

    private void addMapToView(final MapView mapView) {
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.map_layout);
        linearLayout.addView(mapView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private GoogleMapOptions createMapOptions() {
        final LatLng target = new LatLng(0, 0);
        final CameraPosition cameraPosition = new CameraPosition(target, 4, 0, 0);

        return new GoogleMapOptions()
                .camera(cameraPosition)
                .compassEnabled(true)
                .mapType(GoogleMap.MAP_TYPE_NORMAL)
                .rotateGesturesEnabled(true)
                .scrollGesturesEnabled(true)
                .tiltGesturesEnabled(true)
                .useViewLifecycleInFragment(false)
                .zOrderOnTop(true)
                .zoomControlsEnabled(true)
                .zoomGesturesEnabled(true);
    }

    private void initializeButtons(final GoogleMapOptions googleMapOptions) {
        final View getMapOptionsButton = findViewById(R.id.get_map_options);
        getMapOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMapOptions.camera(googleMap.getCameraPosition());
                createMapOptionsDialog(googleMapOptions).show();
            }
        });
    }

    private Dialog createMapOptionsDialog(final GoogleMapOptions googleMapOptions) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(DIALOG_TITLE)
                .setPositiveButton(OK_BUTTON, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setMessage(formatMapOptions(googleMapOptions));

        return dialog.create();
    }

    private String formatMapOptions(final GoogleMapOptions googleMapOptions) {
        final CameraPosition cameraPosition = googleMapOptions.getCamera();
        final double latitude = cameraPosition.target.latitude;
        final double longitude = cameraPosition.target.longitude;

        return "camera position latitude: " + String.format("%.2f", latitude)
                + "\ncamera position longitude: " + String.format("%.2f", longitude)
                + "\nzoom: " + cameraPosition.zoom
                + "\ncompass enabled: " + googleMapOptions.getCompassEnabled()
                + "\nrotate gesture enabled: " + googleMapOptions
                .getRotateGesturesEnabled()
                + "\nscroll gesture enabled: " + googleMapOptions
                .getScrollGesturesEnabled()
                + "\ntilt gesture enabled: " + googleMapOptions.getTiltGesturesEnabled()
                + "\nuse view lifecycle in fragment: " + googleMapOptions
                .getUseViewLifecycleInFragment()
                + "\nZ order on top: " + googleMapOptions.getZOrderOnTop()
                + "\nzoom controls enabled: " + googleMapOptions.getZoomControlsEnabled()
                + "\nzoom gestures enabled: " + googleMapOptions.getZoomGesturesEnabled()
                + "\nmap type (code value): " + googleMapOptions.getMapType();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}