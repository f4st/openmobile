/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.projection;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.openmobileww.maps.v2.R;
/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Uses Projection instance to translate between on screen location and geographic
 * coordinates on the surface of the Earth.
 */
public class MapProjectionTestActivity extends FragmentActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_projection_test);

        initializeMap();
        initializeButtons();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                showClickedPointCoordinates(latLng);
            }
        });
    }

    private void initializeButtons() {
        final View getVisibleRegionButton = findViewById(R.id.get_visible_region);
        final View fromScreenLocationButton = findViewById(R.id.from_screen_location);

        getVisibleRegionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showVisibleRegionCoordinates(googleMap.getProjection().getVisibleRegion());
            }
        });

        fromScreenLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Display display = getWindowManager().getDefaultDisplay();
                final int width = display.getWidth();
                final int height = display.getHeight();

                showMapCenterCoordinates(new Point(width / 2, height / 2));
            }
        });
    }

    private void showClickedPointCoordinates(final LatLng latLng) {
        final Point point = googleMap.getProjection().toScreenLocation(latLng);
        final String text = "Clicked point coordinates: "
                + "x = " + point.x + ", "
                + "y = " + point.y;

        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showVisibleRegionCoordinates(final VisibleRegion visibleRegion) {
        final String text = "Northeast corner coordinates: "
                + String.format("%.2f", visibleRegion.latLngBounds.northeast.latitude)
                + ", "
                + String.format("%.2f", visibleRegion.latLngBounds.northeast.longitude)
                + "\nSouthwest corner coordinates: "
                + String.format("%.2f", visibleRegion.latLngBounds.southwest.latitude)
                + ", "
                + String.format("%.2f", visibleRegion.latLngBounds.southwest.longitude);

        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showMapCenterCoordinates(final Point point) {
        final LatLng latLng = googleMap.getProjection().fromScreenLocation(point);

        final String text = "Map center latitude: "
                + String.format("%.2f", latLng.latitude)
                + "\nMap center latitude: "
                + String.format("%.2f", latLng.longitude);

        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}