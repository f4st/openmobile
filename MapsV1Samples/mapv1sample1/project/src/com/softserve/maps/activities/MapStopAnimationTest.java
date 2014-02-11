package com.softserve.maps.activities;

import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;

/**
 * Activity with Google Maps V1 API MapView.
 * Provides controls to animate map to different points.
 * <p/>
 * In test used MapController.animateTo(GeoPoint point,
 * java.lang.Runnable runnable) method for  animating to points.
 * Animation can be interrupted by clicking on "Stop animation" button
 * (using MapController.stopAnimation() method).
 * "Stop animation" button shows on view only when animation is performing.
 */
public class MapStopAnimationTest extends MapActivity {

    private MapView mapView;
    private View stopAnimationButton;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_stop_animation);

        mapView = (MapView) findViewById(R.id.map_view);
        initializeMap();

        stopAnimationButton = findViewById(R.id.stop_animation);
        stopAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mapView.getController().stopAnimation(false);
                view.setVisibility(View.GONE);
            }
        });
        setAnimateButtonsOptions();
    }

    private void initializeMap() {
        mapView.setSatellite(false);
        mapView.getController().setCenter(new GeoPoint(
                (int) (Constants.mapCenterLatitude * 1E6),
                (int) (Constants.mapCenterLongitude * 1E6)));
        mapView.getController().setZoom(5);
        mapView.invalidate();
    }

    private void setAnimateButtonsOptions() {
        final View animateToPointA = findViewById(R.id.animate_to_a);
        final View animateToPointB = findViewById(R.id.animate_to_b);
        animateToPointA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                stopAnimationButton.setVisibility(View.VISIBLE);
                animateToPoint(Constants.pointALatitude,
                        Constants.pointALongitude);
            }
        });
        animateToPointB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                stopAnimationButton.setVisibility(View.VISIBLE);
                animateToPoint(Constants.pointCLatitude,
                        Constants.pointCLongitude);
            }
        });
    }

    private void animateToPoint(final double geoPointLatitude,
                                final double geoPointLongitude) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                stopAnimationButton.setVisibility(View.GONE);
            }
        };

        final GeoPoint point = new GeoPoint(
                (int) (geoPointLatitude * 1E6),
                (int) (geoPointLongitude * 1E6));

        mapView.getController().animateTo(point, runnable);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}