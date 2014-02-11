package com.softserve.maps.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.softserve.maps.R;
import com.softserve.maps.stresstests.AnimateStressTest;
import com.softserve.maps.stresstests.ItemizedOverlayStressTest;
import com.softserve.maps.stresstests.ScrollStressTest;
import com.softserve.maps.stresstests.ZoomStressTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity with menu of available map tests.
 */
public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        final ListView listView = (ListView) findViewById(R.id.tests_list);
        listView.setAdapter(new ArrayAdapter<Test>(this,
                R.layout.test_list_item, R.id.test_name, createTestList()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView,
                                    final View view,
                                    final int position,
                                    final long l) {
                final Intent intent = new Intent(adapterView.getContext(),
                        ((Test) adapterView.getItemAtPosition(position))
                                .activityClass);
                startActivity(intent);
            }
        });
    }

    private List<Test> createTestList() {
        final List<Test> tests = new ArrayList<Test>();
        tests.add(new Test(getString(R.string.map_type_test),
                MapTypesTestActivity.class));
        tests.add(new Test(getString(R.string.map_manipulating_test1),
                MapManipulationTest1Activity.class));
        tests.add(new Test(getString(R.string.map_manipulating_test2),
                MapManipulationTest2Activity.class));
        tests.add(new Test(getString(R.string.stop_animation_test),
                MapStopAnimationTest.class));
        tests.add(new Test((getString(R.string.map_projection_test)),
                MapProjectionTestActivity.class));
        tests.add(new Test(getString(R.string.map_motion_events_test),
                MapEventsTestActivity.class));
        tests.add(new Test(getString(R.string.user_location_test),
                UserLocationTestActivity.class));
        tests.add(new Test(getString(R.string.itemized_overlay_test),
                ItemizedOverlayTestActivity.class));
        tests.add(new Test(getString(R.string.itemized_overlay_focus_test),
                ItemizedOverlayFocusTestActivity.class));
        tests.add(new Test(getString(R.string.itemized_overlay_focus_change_test),
                OverlayItemFocusChangeTestActivity.class));
        tests.add(new Test(getString(R.string.drawing_test),
                MapDrawingTestActivity.class));
        tests.add(new Test(getString(R.string.scroll_stress_test),
                ScrollStressTest.class));
        tests.add(new Test(getString(R.string.animate_stress_test),
                AnimateStressTest.class));
        tests.add(new Test(getString(R.string.zoom_stress_test),
                ZoomStressTest.class));
        tests.add(new Test(getString(R.string.itemized_overlay_stress_test),
                ItemizedOverlayStressTest.class));

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