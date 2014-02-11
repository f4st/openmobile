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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.openmobileww.maps.v2.R;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Uses UrlTileProvider for creating TileOverlay on map. In this test UrlTileProvider
 * uses same image for all zoom values.
 */
public class MapTileOverlayTestActivity extends FragmentActivity {

    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 200;

    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";

    private GoogleMap googleMap;
    private TileOverlay tileOverlay;
    private boolean isEnabled;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tiles_test);

        initializeMap();
        initializeButtons();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
    }

    private void initializeButtons() {
        final Button button = (Button) findViewById(R.id.tile);
        if (isEnabled) {
            button.setText(getString(R.string.delete_tile));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!isEnabled) {
                    final TileOverlayOptions options = createTileOverlayOptions();
                    createTileOverlayDialog(options).show();
                } else {
                    createTileOverlayDetailsDialog(tileOverlay).show();
                }
            }
        });
    }

    private TileOverlayOptions createTileOverlayOptions() {
        final MapTileProvider provider = new MapTileProvider(IMAGE_WIDTH, IMAGE_HEIGHT);
        return new TileOverlayOptions()
                .tileProvider(provider)
                .visible(true)
                .zIndex(0);
    }

    private Dialog createTileOverlayDialog(final TileOverlayOptions options) {
        final String title = "Create TileOverlay with following options";
        final String message = "TileProvider: " + options.getTileProvider().getClass()
                .getSimpleName()
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
                        tileOverlay = googleMap.addTileOverlay(options);
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

    private Dialog createTileOverlayDetailsDialog(final TileOverlay overlay) {
        final String title = "Delete TileOverlay with following options";
        final String message = "ID: " + overlay.getId()
                + "\nZ index: " + overlay.getZIndex()
                + "\nvisible: " + overlay.isVisible();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        overlay.remove();
                        findViewById(R.id.tile).setVisibility(View.GONE);
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

    private static class MapTileProvider extends UrlTileProvider {

        private static final String ORANGE_URL = "http://icons.iconarchive.com" +
                "/icons/gcds/chinese-new-year/256/orange-icon.png";
        private static final String INVALID_URL_MESSAGE = "Invalid url: ";

        private MapTileProvider(final int width, final int height) {
            super(width, height);
        }

        @Override
        public URL getTileUrl(final int x, final int y, final int zoom) {
            URL url = null;
            try {
                url = new URL(ORANGE_URL);
            } catch (final MalformedURLException exception) {
                Log.e(this.getClass().getSimpleName(), INVALID_URL_MESSAGE + ORANGE_URL);
            }
            return url;
        }
    }
}