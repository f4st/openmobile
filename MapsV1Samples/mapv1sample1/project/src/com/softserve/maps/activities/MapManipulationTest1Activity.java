package com.softserve.maps.activities;

import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;

/**
 * Activity with Google Maps V1 API MapView.
 * Provides controls to zoom map and animate to different points.
 * <p/>
 * In test used MapController.zoomIn() and MapController.zoomOut() methods
 * for zooming and MapController.animateTo(GeoPoint point) method for
 * animating to points.
 */
public class MapManipulationTest1Activity extends MapActivity {

    private MapView mapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_manipulation);
        mapView = (MapView) findViewById(R.id.map_view);

        initializeMap();
        setZoomButtonsOptions();
        setAnimateButtonsOptions();
    }

    private void initializeMap() {
        mapView.setSatellite(false);
        mapView.getController().setCenter(new GeoPoint(
                (int) (Constants.mapCenterLatitude * 1E6),
                (int) (Constants.mapCenterLongitude * 1E6)));
        mapView.getController().setZoom(9);
    }

    private void setZoomButtonsOptions() {
        final View zoomIn = findViewById(R.id.zoom_in);
        final View zoomOut = findViewById(R.id.zoom_out);
        final MapController controller = mapView.getController();

        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                controller.zoomIn();
            }
        });

        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                controller.zoomOut();
            }
        });
    }

    private void setAnimateButtonsOptions() {
        final View animateToPointA = findViewById(R.id.point_a);
        final View animateToPointB = findViewById(R.id.point_b);
        animateToPointA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                animateToPoint(Constants.pointALatitude,
                        Constants.pointALongitude);
            }
        });
        animateToPointB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                animateToPoint(Constants.pointBLatitude,
                        Constants.pointBLongitude);
            }
        });
    }

    private void animateToPoint(final double geoPointLatitude,
                                final double geoPointLongitude) {
        final GeoPoint point = new GeoPoint(
                (int) (geoPointLatitude * 1E6),
                (int) (geoPointLongitude * 1E6));

        mapView.getController().animateTo(point);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}