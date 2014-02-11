package com.softserve.maps.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.softserve.maps.R;
import com.softserve.maps.overlay.MapItemizedOverlay;
import com.softserve.maps.util.Constants;

/**
 * Activity with Google Maps V1 API MapView.
 * <p/>
 * Creates {@link com.softserve.maps.overlay.MapItemizedOverlay} object
 * with 3 OverlayItems in it and shows them on map.
 * <p/>
 * Every time the focus changed you will see Toast message with new
 * focused Item title (ItemizedOverlay.OnFocusChangeListener usage) or
 * message about focus on map.
 */
public class OverlayItemFocusChangeTestActivity extends MapActivity {

    private final static String FOCUS_CHANGED_MESSAGE = "Focus changed. New " +
            "focused item: ";
    private final static String MAP_FOCUSED_MESSAGE = "Map focused";

    private Toast toast;
    private MapView mapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map_view);

        initializeMap();

        final MapItemizedOverlay itemizedOverlay = createMapItemizedOverlay();
        setOverlayOnFocusChangeListener(itemizedOverlay);

        mapView.getOverlays().add(itemizedOverlay);
        try {
            mapView.getController().animateTo(itemizedOverlay.getCenter());
        } catch (final Error error) {
            showIncompatibleTypeMessage();
            mapView.getController().animateTo(itemizedOverlay
                    .getItem(0).getPoint());
        }
    }

    private void initializeMap() {
        mapView.setSatellite(false);
        mapView.getController().setCenter(new GeoPoint(
                (int) (Constants.mapCenterLatitude * 1E6),
                (int) (Constants.mapCenterLongitude * 1E6)));
        mapView.getController().setZoom(10);
        mapView.invalidate();
    }

    private MapItemizedOverlay createMapItemizedOverlay() {
        final MapItemizedOverlay mapItemizedOverlay = new MapItemizedOverlay
                (this);

        final GeoPoint pointA = new GeoPoint(
                (int) (Constants.enfieldLatitude * 1E6),
                (int) (Constants.enfieldLogitude * 1E6));
        final GeoPoint pointB = new GeoPoint(
                (int) (Constants.wembleyLatitude * 1E6),
                (int) (Constants.wembleyLongitude * 1E6));
        final GeoPoint pointC = new GeoPoint(
                (int) (Constants.croydonLatitude * 1E6),
                (int) (Constants.croydonLongitude * 1E6));

        mapItemizedOverlay.addItem(new OverlayItem(pointA,
                Constants.ENFIELD_TITLE, Constants.POINT_A));
        mapItemizedOverlay.addItem(new OverlayItem(pointB,
                Constants.WEMBLEY_TITLE, Constants.POINT_B));
        mapItemizedOverlay.addItem(new OverlayItem(pointC,
                Constants.CROYDON_TITLE, Constants.POINT_C));

        return mapItemizedOverlay;
    }

    private void setOverlayOnFocusChangeListener(final ItemizedOverlay overlay) {
        overlay.setOnFocusChangeListener(new ItemizedOverlay.OnFocusChangeListener() {
            @Override
            public void onFocusChanged(final ItemizedOverlay itemizedOverlay,
                                       final OverlayItem item) {
                if (item != null) {
                    Toast.makeText(OverlayItemFocusChangeTestActivity.this,
                            FOCUS_CHANGED_MESSAGE + item.getTitle(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OverlayItemFocusChangeTestActivity.this,
                            MAP_FOCUSED_MESSAGE,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showIncompatibleTypeMessage() {
        toast = Toast.makeText(this, R.string.cant_get_overlay_center_message,
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    protected void onDestroy() {
        if (toast != null) {
            toast.cancel();
        }
        super.onDestroy();
    }
}