package com.softserve.maps.stresstests;

import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;

/**
 * Base class for all activities which perform stress tests.
 */
public abstract class StressTestMapActivity extends MapActivity {

    private MapView mapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activitiy_stress_test);

        mapView = (MapView) findViewById(R.id.map_view);
        initializeMap();
        setTestButtonOption();
    }

    public MapView getMapView() {
        return mapView;
    }

    private void initializeMap() {
        mapView.setSatellite(false);
        mapView.getController().setCenter(new GeoPoint(
                (int) (Constants.florenceLatitude * 1E6),
                (int) (Constants.florenceLongitude * 1E6)));
        mapView.getController().setZoom(6);
    }

    private void setTestButtonOption() {
        final View startTestButton = findViewById(R.id.start_test_button);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startTest();
            }
        });
    }

    protected abstract void startTest();
}