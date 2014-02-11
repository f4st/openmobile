package com.softserve.maps.stresstests;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.softserve.maps.overlay.MapItemizedOverlay;

import java.util.List;

/**
 * Stress test for map overlays. Adds 100 ItemizedOverlay objects to MapView
 * overlays;
 */
public class ItemizedOverlayStressTest extends StressTestMapActivity {

    @Override
    protected void startTest() {
        final MapView mapView = getMapView();
        final List<Overlay> overlays = mapView.getOverlays();
        final MapItemizedOverlay mapItemizedOverlay = new MapItemizedOverlay
                (this);

        for (int i = 0; i < 100; i++) {
            final GeoPoint pointA = new GeoPoint(50000000, i * 2000000);
            final OverlayItem item = new OverlayItem(pointA, null, null);

            mapItemizedOverlay.addItem(item);
            overlays.add(mapItemizedOverlay);
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}