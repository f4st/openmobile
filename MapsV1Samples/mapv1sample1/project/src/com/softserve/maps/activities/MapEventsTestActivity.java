package com.softserve.maps.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;
import com.softserve.maps.view.SampleMapView;

/**
 * Activity that uses {@link com.softserve.maps.view.SampleMapView}.
 * MapChangeListener - implementation of {@link SampleMapView.OnChangeListener}
 * which receives zoom and pan events from SampleMapView;
 */
public class MapEventsTestActivity extends MapActivity {

    private static final String ZOOM_EVENT = "Zoom event";
    private static final String PAN_EVENT = "Pan event";
    private static final String PAN_ZOOM_EVENT = "Zoom & pan event";

    private SampleMapView mapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_events);

        mapView = (SampleMapView) findViewById(R.id.map_view);
        initializeMap();

        mapView.setBuiltInZoomControls(true);

        mapView.setOnChangeListener(new MapChangeListener());
    }

    private void initializeMap() {
        mapView.setSatellite(false);
        mapView.getController().setCenter(new GeoPoint(
                (int) (Constants.mapCenterLatitude * 1E6),
                (int) (Constants.mapCenterLongitude * 1E6)));
        mapView.getController().setZoom(10);
        mapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    private static class MapChangeListener extends
            SampleMapView.OnChangeListener {

        @Override
        public void onZoomChange(final MapView mapView,
                                 final int oldZoom,
                                 final int newZoom) {
            Toast.makeText(mapView.getContext(), ZOOM_EVENT,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPanChange(final MapView mapView,
                                final int oldCenter,
                                final int newCenter) {
            Toast.makeText(mapView.getContext(), PAN_EVENT,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onZoomPanChange(final MapView mapView,
                                    final GeoPoint oldCenter,
                                    final GeoPoint newCenter,
                                    final int oldZoom,
                                    final int newZoom) {
            Toast.makeText(mapView.getContext(), PAN_ZOOM_EVENT,
                    Toast.LENGTH_SHORT).show();
        }
    }
}