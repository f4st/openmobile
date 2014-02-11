package com.softserve.maps.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
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
 * with 3 OverlayItems in it and shows them on map. Each OverlayItem
 * represent different GeoPoint. Every item has same icon.
 * <p/>
 * Buttons provide info about ItemizedOverlay items focus by clicking on them.
 */
public class ItemizedOverlayFocusTestActivity extends MapActivity {

    private final static String CURRENT_FOCUSED_ITEM_MESSAGE = "Current " +
            "focused item: ";
    private final static String MAP_FOCUSED_MESSAGE = "ItemizedOverlay is " +
            "not focused";
    private final static String LAST_FOCUSED_ITEM_MESSAGE = "Last focused " +
            "item index: ";
    private final static String NEXT_FOCUSED_ITEM_MESSAGE = "Next item to be " +
            "focused: ";
    private final static String LAST_ITEM_MESSAGE = "We're at the end of the " +
            "line";
    private final static String OVERLAY_COORDINATES = "ItemizedOverlay " +
            "coordinates: ";

    private Toast toast;
    private MapView mapView;
    private MapItemizedOverlay itemizedOverlay;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_itemized_overlay_test);
        mapView = (MapView) findViewById(R.id.map_view);

        initializeMap();

        itemizedOverlay = createMapItemizedOverlay();
        mapView.getOverlays().add(itemizedOverlay);
        try {
            mapView.getController().animateTo(itemizedOverlay.getCenter());
        } catch (final Error error) {
            showIncompatibleTypeMessage();
            mapView.getController().animateTo(itemizedOverlay
                    .getItem(0).getPoint());
        }

        initButtons();
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

    private void showIncompatibleTypeMessage() {
        toast = Toast.makeText(this, R.string.cant_get_overlay_center_message,
                Toast.LENGTH_LONG);
        toast.show();
    }

    private void initButtons() {
        final View getFocusButton = findViewById(R.id.get_focus);
        final View getLastFocusedItemButton = findViewById
                (R.id.get_last_focused_item);
        final View getOverlayNextFocus = findViewById(R.id.overlay_next_focus);
        final View getOverlayCoordinatesButton = findViewById
                (R.id.show_overlay_coordinates);

        final Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        getFocusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String message;

                final OverlayItem item = itemizedOverlay.getFocus();
                if (item != null) {
                    message = CURRENT_FOCUSED_ITEM_MESSAGE + item.getTitle();
                } else {
                    message = MAP_FOCUSED_MESSAGE;
                }
                toast.setText(message);
                toast.show();
            }
        });

        getLastFocusedItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String message;
                final int index = itemizedOverlay.getLastFocusedIndex();
                if (index == -1) {
                    message = "ItemizedOverlay is not focused";
                } else {
                    message = LAST_FOCUSED_ITEM_MESSAGE +
                            itemizedOverlay.getLastFocusedIndex();
                }
                toast.setText(message);
                toast.show();
            }
        });

        getOverlayNextFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String message;
                final OverlayItem item = itemizedOverlay.nextFocus(true);
                if (item != null) {
                    message = NEXT_FOCUSED_ITEM_MESSAGE + item.getTitle();
                    itemizedOverlay.setFocus(item);
                } else {
                    message = LAST_ITEM_MESSAGE;
                }
                toast.setText(message);
                toast.show();
            }
        });

        getOverlayCoordinatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String message = OVERLAY_COORDINATES
                        + itemizedOverlay.getLatSpanE6()
                        + ", "
                        + itemizedOverlay.getLonSpanE6();

                toast.setText(message);
                toast.show();
            }
        });
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