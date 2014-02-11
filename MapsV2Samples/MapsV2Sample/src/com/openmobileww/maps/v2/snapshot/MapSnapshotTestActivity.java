/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.snapshot;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.openmobileww.maps.v2.R;
/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user button for getting current map snapshot. Snapshot is displayed in
 * the top left corner of Activity.
 */
public class MapSnapshotTestActivity extends FragmentActivity {

    private static final String SNAPSHOT_READY_MESSAGE = "Snapshot has been taken";

    private GoogleMap googleMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_snapshot_test);

        initializeMap();
        initializeSnapshotButton();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
    }

    private void initializeSnapshotButton() {
        final View takeSnapshotButton1 = findViewById(R.id.take_snapshot_button1);
        final ImageView snapshotPlace = (ImageView) findViewById(R.id.snapshot_place);

        takeSnapshotButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setClickable(false);
                googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(final Bitmap bitmap) {
                        snapshotPlace.setImageBitmap(bitmap);
                        view.setClickable(true);
                        showSnapshotReadyMessage();
                    }
                });
            }
        });

        final View takeSnapshotButton2 = findViewById(R.id.take_snapshot_button2);
        takeSnapshotButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(final Bitmap bitmap) {
                        snapshotPlace.setImageBitmap(bitmap);
                        view.setClickable(true);
                        showSnapshotReadyMessage();
                    }
                }, null);
            }
        });
    }

    private void showSnapshotReadyMessage() {
        final Toast toast = Toast.makeText(this, SNAPSHOT_READY_MESSAGE,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}