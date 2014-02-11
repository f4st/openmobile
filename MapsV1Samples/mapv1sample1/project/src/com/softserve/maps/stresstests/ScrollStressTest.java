package com.softserve.maps.stresstests;

import com.google.android.maps.MapController;

/**
 * Stress test for map scrolling. Performs map scrolling with MapController
 * .scrollBy(int x, int y) method 10000 times in a row;
 */
public class ScrollStressTest extends StressTestMapActivity {

    @Override
    protected void startTest() {
        final MapController controller = getMapView().getController();
        for (int i = 0; i < 10000; i++) {
            controller.scrollBy(i, 0);
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}