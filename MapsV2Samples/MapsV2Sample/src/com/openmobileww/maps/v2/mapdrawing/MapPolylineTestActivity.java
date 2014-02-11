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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.openmobileww.maps.v2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user button for creating new polyline on map. Created polyline can be
 * modified by clicking on "Transform polyline" button. After transforming user should
 * see color and shape changes in polyline. Polyline can be deleted by clicking on
 * "Delete polyline" button.
 */
public class MapPolylineTestActivity extends FragmentActivity {

    private static final LatLng POINT1 = new LatLng(43, -170);
    private static final LatLng POINT2 = new LatLng(-11, -206);
    private static final LatLng POINT3 = new LatLng(-11, -132);
    private static final LatLng POINT4 = new LatLng(34, -122);

    private static final LatLng POSITION = new LatLng(15, -168);
    private static final float ZOOM = 2;
    private static final float POLYLINE_WIDTH = 15;
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";

    private GoogleMap googleMap;
    private Polyline polyline;
    private boolean isEnabled;
    private boolean isTransformed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline_test);

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
        final Button button = (Button) findViewById(R.id.polyline);
        if (isTransformed) {
            button.setText(getString(R.string.delete_polyline));
        } else if (isEnabled) {
            button.setText(getString(R.string.transform_polyline));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!isEnabled) {
                    final PolylineOptions options = createPolylineOptions();
                    createPolylineDialog(options).show();
                } else if (!isTransformed) {
                    createTransformPolylineDialog(polyline).show();
                } else {
                    polyline.remove();
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    private Dialog createPolylineDialog(final PolylineOptions options) {
        final String title = "Create polyline with following options";
        final String message = "number of points: " + options.getPoints().size()
                + "\ncolor: " + Integer.toHexString(options.getColor())
                + "\nwidth: " + options.getWidth()
                + "\nZ index: " + options.getZIndex()
                + "\nvisible: " + options.isVisible()
                + "\ngeodesic: " + options.isGeodesic();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        polyline = googleMap.addPolyline(options);
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

    private PolylineOptions createPolylineOptions() {
        final List<LatLng> points = new ArrayList<LatLng>();
        points.add(POINT1);

        return new PolylineOptions()
                .addAll(points)
                .add(POINT2, POINT3)
                .add(POINT4)
                .color(getResources().getColor(R.color.green))
                .geodesic(true)
                .visible(true)
                .width(POLYLINE_WIDTH)
                .zIndex(0);
    }

    private void transformPolyline(final Polyline polyline) {
        final List<LatLng> points = new ArrayList<LatLng>();
        points.add(POINT4);
        points.add(POINT2);
        points.add(POINT3);
        points.add(POINT1);

        polyline.setColor(getResources().getColor(R.color.grey));
        polyline.setGeodesic(true);
        polyline.setPoints(points);
        polyline.setVisible(true);
        polyline.setWidth(POLYLINE_WIDTH);
        polyline.setZIndex(0);
    }


    private Dialog createTransformPolylineDialog(final Polyline polyline) {
        final String title = "Polyline transformation with following options";
        final String message = "ID: " + polyline.getId()
                + "\nnumber of points: " + polyline.getPoints().size()
                + "\ncolor: " + Integer.toHexString(polyline.getColor())
                + "\nwidth: " + polyline.getWidth()
                + "\nZ index: " + polyline.getZIndex()
                + "\nvisible: " + polyline.isVisible()
                + "\ngeodesic: " + polyline.isGeodesic();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        transformPolyline(polyline);
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