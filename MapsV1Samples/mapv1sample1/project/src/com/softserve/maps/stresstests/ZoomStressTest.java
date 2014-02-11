package com.softserve.maps.stresstests;

import com.google.android.maps.MapController;

/**
 * Stress test for map zooming. Performs map zooming with MapController
 * .zoomToSpan(int latSpanE6, int lonSpanE6) method 10000 times in a row.
 */
public class ZoomStressTest extends StressTestMapActivity {

    @Override
    protected void startTest() {
        final MapController controller = getMapView().getController();
        for (int i = 0; i < 10000; i++) {
            controller.zoomToSpan(i, 0);
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}