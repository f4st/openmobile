package com.softserve.maps.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;

/**
 * Activity with MapView. If MapView is instance of
 * com.google.android.maps.MapView - provides controls to change map type
 * (Satellite/StreetView) and switch traffic on map. Otherwise you will see
 * Toast with message about MapView incompatibility.
 */
public class MapTypesTestActivity extends MapActivity {

    private Toast toast;
    private MapView googleMapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_types);

        final View view = findViewById(R.id.map_view);

        try {
            googleMapView = (MapView) view;
            initializeMap();
            setButtonsOptions();
        } catch (final Error error) {
            showIncompatibleTypeMessage();
            findViewById(R.id.tabs).setVisibility(View.GONE);
        }
    }

    private void initializeMap() {
        googleMapView.setSatellite(false);
        googleMapView.getController().setCenter(new GeoPoint(
                (int) (Constants.mapCenterLatitude * 1E6),
                (int) (Constants.mapCenterLongitude * 1E6)));
        googleMapView.getController().setZoom(9);
    }

    private void setButtonsOptions() {
        final Button satelliteButton = (Button) findViewById
                (R.id.tab_satellite_street_view);
        final Button trafficButton = (Button) findViewById(R.id.tab_traffic);

        satelliteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMapView.isSatellite()) {
                    googleMapView.setSatellite(false);
                    satelliteButton.setText(getResources().getString
                            (R.string.map_satellite));
                } else {
                    googleMapView.setSatellite(true);
                    satelliteButton.setText(getResources().getString
                            (R.string.map_street_view));
                }
            }
        });

        trafficButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (googleMapView.isTraffic()) {
                    googleMapView.setTraffic(false);
                    trafficButton.setText(getResources().getString
                            (R.string.map_traffic_on));
                } else {
                    googleMapView.setTraffic(true);
                    trafficButton.setText(getResources().getString
                            (R.string.map_traffic_off));
                }
            }
        });
    }

    private void showIncompatibleTypeMessage() {
        toast = Toast.makeText(this, R.string.cant_change_map_type_message,
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