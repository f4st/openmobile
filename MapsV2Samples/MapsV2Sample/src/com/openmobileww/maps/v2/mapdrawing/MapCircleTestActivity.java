/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapdrawing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user button for creating new circle on map. Created circle can be
 * modified by clicking on "Transform circle" button. After transforming user should
 * see color changes in circle. Circle can be deleted by clicking on "Delete circle"
 * button.
 */
public class MapCircleTestActivity extends FragmentActivity {

    private static final LatLng POSITION = new LatLng(15, -168);
    private static final float ZOOM = 3;
    private static final double RADIUS = 700000;
    private static final float STROKE_WIDTH = 50;
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";

    private GoogleMap googleMap;
    private Circle circle;
    private boolean isEnabled;
    private boolean isTransformed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_test);

        initializeMap();
        initializeButtons();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POSITION, ZOOM));
    }

    private void initializeButtons() {
        final Button button = (Button) findViewById(R.id.circle);
        if (isTransformed) {
            button.setText(getString(R.string.delete_circle));
        } else if (isEnabled) {
            button.setText(getString(R.string.transform_circle));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!isEnabled) {
                    final CircleOptions options = createCircleOptions();
                    createCircleDialog(options).show();
                } else if (!isTransformed) {
                    createTransformCircleDialog(circle).show();
                } else {
                    circle.remove();
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    private Dialog createCircleDialog(final CircleOptions options) {
        final String title = "Create Circle with following options";
        final String message = "center: " + options.getCenter().latitude + ", " +
                +options.getCenter().longitude
                + "\nfill color: " + Integer.toHexString(options.getFillColor())
                + "\nradius: " + options.getRadius()
                + "\nstroke color: " + Integer.toHexString(options.getStrokeColor())
                + "\nstroke width: " + options.getStrokeWidth()
                + "\nZ index: " + options.getZIndex()
                + "\nvisible: " + options.isVisible();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        circle = googleMap.addCircle(options);
                        isEnabled = true;
                        initializeButtons();
                    }
                })
                .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }

    private CircleOptions createCircleOptions() {
        return new CircleOptions()
                .center(POSITION)
                .fillColor(getResources().getColor(R.color.green))
                .radius(RADIUS)
                .strokeColor(getResources().getColor(R.color.grey))
                .strokeWidth(STROKE_WIDTH)
                .visible(true)
                .zIndex(0);
    }

    private void transformCircle(final Circle circle) {
        circle.setVisible(true);
        circle.setCenter(POSITION);
        circle.setFillColor(getResources().getColor(R.color.light_black));
        circle.setRadius(RADIUS);
        circle.setStrokeColor(getResources().getColor(R.color.blue));
        circle.setStrokeWidth(STROKE_WIDTH);
        circle.setVisible(true);
        circle.setZIndex(0);
    }


    private Dialog createTransformCircleDialog(final Circle circle) {
        final String title = "Circle transformation with following options";
        final String message = "ID: " + circle.getId()
                + "\ncenter: " + circle.getCenter().latitude + ", " +
                +circle.getCenter().longitude
                + "\nfill color: " + Integer.toHexString(circle.getFillColor())
                + "\nradius: " + circle.getRadius()
                + "\nstroke color: " + Integer.toHexString(circle.getStrokeColor())
                + "\nstroke width: " + circle.getStrokeWidth()
                + "\nZ index: " + circle.getZIndex()
                + "\nvisible: " + circle.isVisible();


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        transformCircle(circle);
                        isTransformed = true;
                        initializeButtons();
                    }
                })
                .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.cancel();
                    }
                });
        return dialog.create();
    }
}