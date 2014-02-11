package com.softserve.maps.activities;

import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;

/**
 * Activity with Google Maps V1 API MapView.
 * When button "Animate to user location" gets clicked, point on map
 * with current user will be added (using MyLocationOverlay).
 * <p/>
 * Determining user location may take few moments.
 */
public class UserLocationTestActivity extends MapActivity {

    private MapView mapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_user_location);
        mapView = (MapView) findViewById(R.id.map_view);

        initializeMap();
        setUserLocationButtonOptions();
    }

    private void setUserLocationButtonOptions() {
        final View userLocationButton = findViewById(R.id.user_location_button);
        userLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                addUserLocationOverlay();
            }
        });
    }

    private void initializeMap() {
        mapView.setSatellite(false);
        mapView.getController().setCenter(new GeoPoint(
                (int) (Constants.mapCenterLatitude * 1E6),
                (int) (Constants.mapCenterLongitude * 1E6)));
        mapView.getController().setZoom(9);
    }

    private void addUserLocationOverlay() {
        final MyLocationOverlay overlay = new MyLocationOverlay(this, mapView);
        overlay.enableMyLocation();
        overlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapView.getOverlays().add(overlay);
                mapView.getController().animateTo(overlay.getMyLocation());
                mapView.getController().setZoom(15);
            }
        });
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}