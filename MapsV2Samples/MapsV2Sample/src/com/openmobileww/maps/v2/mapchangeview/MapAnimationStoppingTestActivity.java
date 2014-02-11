/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapchangeview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides buttons for animating to different points on map. Animation can be
 * interrupted by user. If animation reaches its destination or gets canceled by user
 * appears Toast with appropriate message.
 */
public class MapAnimationStoppingTestActivity extends FragmentActivity {

    private static final String ANIMATION_FINISHED = "Animation finished";
    private static final String ANIMATION_CANCELED = "Animation canceled";
    private static final LatLng coordinates1 = new LatLng(37.4, -122.1);
    private static final LatLng coordinates2 = new LatLng(-34, 149);
    private static final int delay = 2000;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_stopping_test);

        initializeMap();
        initializeButtons();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
    }

    private void initializeButtons() {
        final View animateToPointAButton = findViewById(R.id.animate_to_a);
        final View animateToPointBButton = findViewById(R.id.animate_to_b);
        final View stopAnimationButton = findViewById(R.id.stop_animation);

        animateToPointAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(coordinates1),
                        createCancelableCallback());
                stopAnimationButton.setVisibility(View.VISIBLE);
            }
        });

        animateToPointBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(coordinates2),
                        delay,
                        createCancelableCallback());
                stopAnimationButton.setVisibility(View.VISIBLE);
            }
        });

        stopAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.stopAnimation();
            }
        });
    }

    private GoogleMap.CancelableCallback createCancelableCallback() {
        final View stopAnimationButton = findViewById(R.id.stop_animation);

        return new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                showAnimationFinishMessage();
                stopAnimationButton.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {
                showAnimationCancelMessage();
                stopAnimationButton.setVisibility(View.GONE);
            }
        };
    }

    private void showAnimationFinishMessage() {
        final Toast toast = Toast.makeText(this, ANIMATION_FINISHED, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showAnimationCancelMessage() {
        final Toast toast = Toast.makeText(this, ANIMATION_CANCELED, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}