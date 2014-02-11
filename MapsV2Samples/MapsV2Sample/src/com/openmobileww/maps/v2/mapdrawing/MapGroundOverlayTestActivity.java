/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapdrawing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.openmobileww.maps.v2.R;

import java.io.IOException;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user button for creating new GroundOverlay on map (Android logo). Created
 * GroundOverlay can be modified by clicking on "Transform GroundOverlay" button. After
 * transforming user should see Android Kit-Kat logo. GroundOverlay can be deleted by
 * clicking on "Delete GroundOverlay" button.
 */
public class MapGroundOverlayTestActivity extends FragmentActivity {

    private static final String ANDROID_IMAGE = "kivi.png";
    private static final String KIT_KAT_IMAGE = "lemon.png";
    private static final LatLng POSITION = new LatLng(15, -168);
    private static final float ZOOM = 3;
    private static final int WIDTH = 5000000;
    private static final int HEIGHT = 5000000;
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";

    private GoogleMap googleMap;
    private GroundOverlay groundOverlay;
    private boolean isEnabled;
    private boolean isTransformed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_overlay_test);

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
        final Button button = (Button) findViewById(R.id.ground_overlay);
        if (isTransformed) {
            button.setText(getString(R.string.delete_ground_overlay));
        } else if (isEnabled) {
            button.setText(getString(R.string.transform_overlay));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!isEnabled) {
                    final GroundOverlayOptions options = createGroundOverlayOptions();
                    createGroundOverlayDialog(options).show();
                } else if (!isTransformed) {
                    createTransformGroundOverlayDialog(groundOverlay).show();
                } else {
                    groundOverlay.remove();
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    private Dialog createGroundOverlayDialog(final GroundOverlayOptions options) {
        final String title = "Create GroundOverlay with following options";
        final String message = "anchor: " + options.getAnchorU() + ", " + options.getAnchorV()
                + "\nbearing: " + options.getBearing()
                + "\nlocation: " + options.getLocation().latitude + ", "
                + options.getLocation().longitude
                + "\ntransparency: " + options.getTransparency()
                + "\nwidth: " + options.getWidth()
                + "\nheight: " + options.getHeight()
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
                        groundOverlay = googleMap.addGroundOverlay(options);
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

    private GroundOverlayOptions createGroundOverlayOptions() {
        return new GroundOverlayOptions()
                .anchor(0.5f, 0.5f)
                .bearing(0)
                .image(BitmapDescriptorFactory.fromAsset(ANDROID_IMAGE))
                .position(POSITION, WIDTH, HEIGHT)
                .transparency(0.1f)
                .visible(true)
                .zIndex(0);
    }

    private void transformGroundOverlay(final GroundOverlay groundOverlay) {
        final Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getAssets().open(KIT_KAT_IMAGE));
        } catch (final IOException exception) {
            throw new Error("Unable to open image");
        }

        groundOverlay.setVisible(true);
        groundOverlay.setBearing(0);
        groundOverlay.setDimensions(WIDTH, HEIGHT);
        groundOverlay.setImage(BitmapDescriptorFactory.fromBitmap(bitmap));
        groundOverlay.setPosition(POSITION);
        groundOverlay.setTransparency(0.1f);
        groundOverlay.setVisible(true);
        groundOverlay.setZIndex(0);
    }


    private Dialog createTransformGroundOverlayDialog(final GroundOverlay groundOverlay) {
        final String title = "GroundOverlay transformation with following options";
        final String message = "bearing: " + groundOverlay.getBearing()
                + "\ntransparency: " + groundOverlay.getTransparency()
                + "\nwidth: " + groundOverlay.getWidth()
                + "\nheight: " + groundOverlay.getHeight()
                + "\nZ index: " + groundOverlay.getZIndex()
                + "\nvisible: " + groundOverlay.isVisible();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        transformGroundOverlay(groundOverlay);
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