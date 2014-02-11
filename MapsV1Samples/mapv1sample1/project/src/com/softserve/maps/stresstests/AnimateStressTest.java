package com.softserve.maps.stresstests;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

/**
 * Stress test for map animation. Performs map animating with MapController
 * .animateTo(GeoPoint geoPoint) method 10000 times;
 */
public class AnimateStressTest extends StressTestMapActivity {

    @Override
    protected void startTest() {
        final MapController controller = getMapView().getController();
        for (int i = 0; i < 10000; i++) {
            final GeoPoint geoPoint = new GeoPoint(0, i * 100);
            controller.animateTo(geoPoint);
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}