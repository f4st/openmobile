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
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.openmobileww.maps.v2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user button for creating new polygon on map. Created polygon can be
 * modified by clicking on "Transform polygon" button. After transforming user should
 * see color changes in polygon. Polygon can be deleted by clicking on "Delete
 * polygon" button.
 */
public class MapPolygonTestActivity extends FragmentActivity {

    private static final LatLng POINT1 = new LatLng(43, -170);
    private static final LatLng POINT2 = new LatLng(-11, -206);
    private static final LatLng POINT3 = new LatLng(-11, -132);
    private static final LatLng POINT4 = new LatLng(34, -122);
    private static final LatLng HOLE1 = new LatLng(17, -172);
    private static final LatLng HOLE2 = new LatLng(6, -158);
    private static final LatLng HOLE3 = new LatLng(-7, -186);

    private static final LatLng POSITION = new LatLng(15, -168);
    private static final float ZOOM = 2;
    private static final float STROKE_WIDTH = 5;
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";

    private GoogleMap googleMap;
    private Polygon polygon;
    private boolean isEnabled;
    private boolean isTransformed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon_test);

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
        final Button button = (Button) findViewById(R.id.polygon);
        if (isTransformed) {
            button.setText(getString(R.string.delete_polygon));
        } else if (isEnabled) {
            button.setText(getString(R.string.transform_polygon));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!isEnabled) {
                    final PolygonOptions options = createPolygonOptions();
                    createPolygonDialog(options).show();
                } else if (!isTransformed) {
                    createTransformPolygonDialog(polygon).show();
                } else {
                    polygon.remove();
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    private Dialog createPolygonDialog(final PolygonOptions options) {
        final String title = "Create Polygon with following options";
        final String message = "fill color: " + Integer.toHexString(options.getFillColor())
                + "\nstroke color: " + Integer.toHexString(options.getStrokeColor())
                + "\nstroke width: " + options.getStrokeWidth()
                + "\nZ index: " + options.getZIndex()
                + "\nvisible: " + options.isVisible()
                + "\ngeodesic: " + options.isGeodesic()
                + "\nnumber of points: " + options.getPoints().size()
                + "\nnumber of holes: " + options.getHoles().size();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        polygon = googleMap.addPolygon(options);
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

    private PolygonOptions createPolygonOptions() {
        final List<LatLng> points = new ArrayList<LatLng>();
        points.add(POINT1);

        final List<LatLng> hole = new ArrayList<LatLng>();
        hole.add(HOLE1);
        hole.add(HOLE2);
        hole.add(HOLE3);

        return new PolygonOptions()
                .addAll(points)
                .add(POINT2, POINT3)
                .add(POINT4)
                .addHole(hole)
                .fillColor(getResources().getColor(R.color.green))
                .geodesic(true)
                .strokeColor(getResources().getColor(R.color.grey))
                .strokeWidth(STROKE_WIDTH)
                .visible(true)
                .zIndex(0);
    }

    private void transformPolygon(final Polygon polygon) {
        final List<LatLng> points = new ArrayList<LatLng>();
        points.add(POINT1);
        points.add(POINT2);
        points.add(POINT3);
        points.add(POINT4);

        final List<LatLng> hole = new ArrayList<LatLng>();
        hole.add(HOLE1);
        hole.add(HOLE2);
        hole.add(HOLE3);

        final List<List<LatLng>> holes = new ArrayList<List<LatLng>>();
        holes.add(hole);

        polygon.setVisible(true);
        polygon.setPoints(points);
        polygon.setFillColor(getResources().getColor(R.color.light_black));
        polygon.setHoles(holes);
        polygon.setStrokeColor(getResources().getColor(R.color.blue));
        polygon.setStrokeWidth(STROKE_WIDTH);
        polygon.setVisible(true);
        polygon.setZIndex(0);
    }


    private Dialog createTransformPolygonDialog(final Polygon polygon) {
        final String title = "Polygon transformation with following options";
        final String message = "ID: " + polygon.getId()
                + "fill color: " + Integer.toHexString(polygon.getFillColor())
                + "\nstroke color: " + Integer.toHexString(polygon.getStrokeColor())
                + "\nstroke width: " + polygon.getStrokeWidth()
                + "\nZ index: " + polygon.getZIndex()
                + "\nvisible: " + polygon.isVisible()
                + "\ngeodesic: " + polygon.isGeodesic()
                + "\nnumber of points: " + polygon.getPoints().size()
                + "\nnumber of holes: " + polygon.getHoles().size();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        transformPolygon(polygon);
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