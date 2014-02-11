/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.testlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.openmobileww.maps.v2.R;
import com.openmobileww.maps.v2.locationdata.MapLocationSourceTestActivity;
import com.openmobileww.maps.v2.mapchangeview.MapAnimationStoppingTestActivity;
import com.openmobileww.maps.v2.mapchangeview.MapCameraUpdateTest1Activity;
import com.openmobileww.maps.v2.mapchangeview.MapCameraUpdateTest2Activity;
import com.openmobileww.maps.v2.mapchangeview.MapCameraUpdateTest3Activity;
import com.openmobileww.maps.v2.mapchangeview.MapCameraUpdateTest4Activity;
import com.openmobileww.maps.v2.mapdrawing.MapCircleTestActivity;
import com.openmobileww.maps.v2.mapdrawing.MapGroundOverlayTestActivity;
import com.openmobileww.maps.v2.mapdrawing.MapMarkersTestActivity;
import com.openmobileww.maps.v2.mapdrawing.MapPolygonTestActivity;
import com.openmobileww.maps.v2.mapdrawing.MapPolylineTestActivity;
import com.openmobileww.maps.v2.mapdrawing.MapTileOverlayTestActivity;
import com.openmobileww.maps.v2.mapdrawing.bitmaps.MapBitmapDescriptorTest1Activity;
import com.openmobileww.maps.v2.mapdrawing.bitmaps.MapBitmapDescriptorTest2Activity;
import com.openmobileww.maps.v2.mapdrawing.bitmaps.MapBitmapDescriptorTest3Activity;
import com.openmobileww.maps.v2.mapdrawing.bitmaps.MapBitmapDescriptorTest4Activity;
import com.openmobileww.maps.v2.mapdrawing.bitmaps.MapBitmapDescriptorTest5Activity;
import com.openmobileww.maps.v2.mapinteractions.MapCallbacksTestActivity;
import com.openmobileww.maps.v2.mapinteractions.MapUiControlsTestActivity;
import com.openmobileww.maps.v2.mapinteractions.MapUiGesturesTest1Activity;
import com.openmobileww.maps.v2.mapinteractions.MapUiGesturesTest2Activity;
import com.openmobileww.maps.v2.mapobjects.MapBuildingsTestActivity;
import com.openmobileww.maps.v2.mapobjects.MapIndoorTestActivity;
import com.openmobileww.maps.v2.mapobjects.MapOptionsTestActivity;
import com.openmobileww.maps.v2.mapobjects.MapTrafficTestActivity;
import com.openmobileww.maps.v2.mapobjects.MapTypeTestActivity;
import com.openmobileww.maps.v2.mapobjects.MapViewTestActivity;
import com.openmobileww.maps.v2.projection.MapProjectionTestActivity;
import com.openmobileww.maps.v2.snapshot.MapSnapshotTestActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity with list of available tests. Appears at the start of application.
 */
public class StartActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final ListView listView = (ListView) findViewById(R.id.tests_list);
        listView.setAdapter(new ArrayAdapter<Test>(this,
                R.layout.test_item, R.id.test_title, createTestList()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView,
                                    final View view,
                                    final int position,
                                    final long l) {
                final Intent intent = new Intent(adapterView.getContext(),
                        ((Test) adapterView.getItemAtPosition(position)).activityClass);
                startActivity(intent);
            }
        });
    }

    private List<Test> createTestList() {
        final List<Test> tests = new ArrayList<Test>();

        tests.add(new Test(getString(R.string.map_ui_controls_test),
                MapUiControlsTestActivity.class));
        tests.add(new Test(getString(R.string.map_ui_gestures_test_part1),
                MapUiGesturesTest1Activity.class));
        tests.add(new Test(getString(R.string.map_ui_gestures_test_part2),
                MapUiGesturesTest2Activity.class));
        tests.add(new Test(getString(R.string.map_types_test),
                MapTypeTestActivity.class));
        tests.add(new Test(getString(R.string.map_callbacks_test),
                MapCallbacksTestActivity.class));
        tests.add(new Test(getString(R.string.map_snapshot_test),
                MapSnapshotTestActivity.class));
        tests.add(new Test(getString(R.string.map_camera_update_test1),
                MapCameraUpdateTest1Activity.class));
        tests.add(new Test(getString(R.string.map_camera_update_test2),
                MapCameraUpdateTest2Activity.class));
        tests.add(new Test(getString(R.string.map_camera_update_test3),
                MapCameraUpdateTest3Activity.class));
        tests.add(new Test(getString(R.string.map_camera_update_test4),
                MapCameraUpdateTest4Activity.class));
        tests.add(new Test(getString(R.string.map_animation_stopping_test),
                MapAnimationStoppingTestActivity.class));
        tests.add(new Test(getString(R.string.map_location_source_test),
                MapLocationSourceTestActivity.class));
        tests.add(new Test(getString(R.string.map_projection_test),
                MapProjectionTestActivity.class));
        tests.add(new Test(getString(R.string.map_view_test),
                MapViewTestActivity.class));
        tests.add(new Test(getString(R.string.map_traffic_test),
                MapTrafficTestActivity.class));
        tests.add(new Test(getString(R.string.map_indoor_test),
                MapIndoorTestActivity.class));
        tests.add(new Test(getString(R.string.map_buildings_test),
                MapBuildingsTestActivity.class));
        tests.add(new Test((getString(R.string.map_options_test)),
                MapOptionsTestActivity.class));
        tests.add(new Test(getString(R.string.map_markers_test),
                MapMarkersTestActivity.class));
        tests.add(new Test(getString(R.string.map_ground_overlay_test),
                MapGroundOverlayTestActivity.class));
        tests.add(new Test(getString(R.string.map_circle_test),
                MapCircleTestActivity.class));
        tests.add(new Test(getString(R.string.map_polygon_test),
                MapPolygonTestActivity.class));
        tests.add(new Test(getString(R.string.map_polyline_test),
                MapPolylineTestActivity.class));
        tests.add(new Test(getString(R.string.map_tiles_test),
                MapTileOverlayTestActivity.class));
        tests.add(new Test(getString(R.string.bitmap_descriptor_test1),
                MapBitmapDescriptorTest1Activity.class));
        tests.add(new Test(getString(R.string.bitmap_descriptor_test2),
                MapBitmapDescriptorTest2Activity.class));
        tests.add(new Test(getString(R.string.bitmap_descriptor_test3),
                MapBitmapDescriptorTest3Activity.class));
        tests.add(new Test(getString(R.string.bitmap_descriptor_test4),
                MapBitmapDescriptorTest4Activity.class));
        tests.add(new Test(getString(R.string.bitmap_descriptor_test5),
                MapBitmapDescriptorTest5Activity.class));

        return tests;
    }

    private static class Test {
        private final String testName;
        private final Class activityClass;

        private Test(final String testName, final Class activityClass) {
            this.testName = testName;
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return testName;
        }
    }
}