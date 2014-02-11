package com.softserve.maps.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;

/**
 * Activity with Google Maps V1 API MapView.
 * <p/>
 * To MapView applied custom View.onTouchListener which analyzes MotionEvent
 * and shows Toast message with touched point coordinates.
 */
public class MapProjectionTestActivity extends MapActivity {

    private MapView mapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_events);

        mapView = (MapView) findViewById(R.id.map_view);
        initializeMap();

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                if (event.getAction() == 1) {
                    final GeoPoint touchedPoint = mapView
                            .getProjection().fromPixels(
                                    (int) event.getX(),
                                    (int) event.getY());
                    final String message = touchedPoint.getLatitudeE6() / 1E6 +
                            ", " + touchedPoint.getLongitudeE6() / 1E6;

                    Toast.makeText(MapProjectionTestActivity.this, message,
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
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
}