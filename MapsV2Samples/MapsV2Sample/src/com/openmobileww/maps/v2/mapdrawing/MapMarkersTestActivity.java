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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openmobileww.maps.v2.R;

/**
 * FragmentActivity which uses SupportMapFragment for displaying GoogleMap.
 * <p/>
 * Provides to user button for creating new Marker on map. Created Marker can be
 * modified by clicking on "Transform Marker" button. After transforming user should
 * see new marker icon and . Marker can be deleted by clicking on "Delete
 * Marker" button.
 */
public class MapMarkersTestActivity extends FragmentActivity {

    private static final String MARKER_DRAG_STARTED = "Marker drag started";
    private static final String MARKER_DRAG_FINISHED = "Marker drag finished";
    private static final String MARKER_CLICK = "Marker click event";
    private static final String TITLE = "Wembley Stadium";
    private static final String TITLE_HIDDEN = "Hidden marker";
    private static final String SNIPPET = "The Venue of Legends, New Wembley";
    private static final String SNIPPET_HIDDEN = "Click on \"Transform marker\" button";
    private static final LatLng POSITION = new LatLng(51.5558, -0.27936);
    private static final float ZOOM = 11;
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";

    private GoogleMap googleMap;
    private Marker marker;
    private boolean isEnabled;
    private boolean isTransformed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_markers_test);

        initializeMap();
        initializeButtons();
        setMarkersInfoWindowAdapter();
    }

    private void initializeMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = mapFragment.getMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POSITION, ZOOM));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                showMarkerClickMessage();
                return false;
            }
        });
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(final Marker marker) {
                showMarkerDragStartMessage();
            }

            @Override
            public void onMarkerDrag(final Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(final Marker marker) {
                showMarkerDragEndMessage();
            }
        });
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                if (!marker.isFlat()) {
                    createMarkerDetailsDialog(marker).show();
                }
            }
        });
    }

    private void initializeButtons() {
        final Button button = (Button) findViewById(R.id.add_marker);
        if (isTransformed) {
            button.setText(getString(R.string.delete_marker));
        } else if (isEnabled) {
            button.setText(getString(R.string.transform_marker));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!isEnabled) {
                    final MarkerOptions options = createWembleyMarker();
                    createMarkerDialog(options).show();
                } else if (!isTransformed) {
                    createTransformMarkerDialog(marker).show();
                } else {
                    marker.remove();
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setMarkersInfoWindowAdapter() {
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(final Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {
                final View view = getLayoutInflater().inflate(R.layout.stadium_info_window,
                        null);

                if (view != null) {
                    final TextView title = (TextView) view.findViewById(R.id
                            .marker_info_title);
                    final TextView snippet = (TextView) view.findViewById(R.id
                            .marker_info_snippet);
                    title.setText(marker.getTitle());
                    snippet.setText(marker.getSnippet());

                    final View image = view.findViewById(R.id.marker_info_image);
                    if (marker.isFlat()) {
                        image.setVisibility(View.GONE);
                    } else {
                        image.setVisibility(View.VISIBLE);
                    }
                }

                return view;
            }
        });
    }

    private void showMarkerDragStartMessage() {
        final Toast toast = Toast.makeText(this, MARKER_DRAG_STARTED,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showMarkerDragEndMessage() {
        final Toast toast = Toast.makeText(this, MARKER_DRAG_FINISHED,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showMarkerClickMessage() {
        final Toast toast = Toast.makeText(this, MARKER_CLICK,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private Dialog createMarkerDialog(final MarkerOptions options) {
        final String title = "Marker details";
        final String message = "Alpha: " + options.getAlpha()
                + "\nlatitude: " + options.getPosition().latitude
                + "\nlongitude: " + options.getPosition().longitude
                + "\nanchor U: " + options.getAnchorU()
                + "\nanchor V: " + options.getAnchorU()
                + "\nInfoWindow anchor U: " + options.getInfoWindowAnchorU()
                + "\nInfoWindow anchor V: " + options.getInfoWindowAnchorV()
                + "\nrotation: " + options.getRotation()
                + "\ndraggable: " + options.isDraggable()
                + "\nflat: " + options.isFlat()
                + "\nisVisible: " + options.isVisible();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        marker = googleMap.addMarker(createWembleyMarker());
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

    private Dialog createMarkerDetailsDialog(final Marker marker) {
        marker.hideInfoWindow();

        final String title = "Marker details";
        final String message = "Alpha: " + marker.getAlpha()
                + "\nID: " + marker.getId()
                + "\nlatitude: " + marker.getPosition().latitude
                + "\nlongitude: " + marker.getPosition().longitude
                + "\nrotation: " + marker.getRotation()
                + "\ndraggable: " + marker.isDraggable()
                + "\nflat: " + marker.isFlat()
                + "\nisInfoWindowShown: " + marker.isInfoWindowShown()
                + "\nisVisible: " + marker.isVisible();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                    }
                });
        return builder.create();
    }

    private Dialog createTransformMarkerDialog(final Marker marker) {
        final String title = "Marker transformation";
        final String message = "Do you want to transform marker?";

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                        transformMarker(marker).showInfoWindow();
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
        return builder.create();
    }

    private MarkerOptions createWembleyMarker() {
        return new MarkerOptions()
                .alpha(1.0f)
                .anchor(0.5f, 0.5f)
                .draggable(true)
                .flat(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .infoWindowAnchor(0.5f, 0.5f)
                .position(POSITION)
                .rotation(0)
                .snippet(SNIPPET_HIDDEN)
                .title(TITLE_HIDDEN)
                .visible(true);
    }

    private Marker transformMarker(final Marker marker) {
        marker.setAlpha(1.0f);
        marker.setAnchor(0.5f, 0.5f);
        marker.setDraggable(true);
        marker.setFlat(false);
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.stadium_icon));
        marker.setInfoWindowAnchor(0.5f, 0.5f);
        marker.setPosition(marker.getPosition());
        marker.setSnippet(SNIPPET);
        marker.setTitle(TITLE);
        marker.setVisible(true);

        return marker;
    }
}