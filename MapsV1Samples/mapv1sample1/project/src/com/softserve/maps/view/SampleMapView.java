package com.softserve.maps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * Class extends com.google.android.maps.MapView. So you will able to add map
 * event listener from outside.
 * ComputeScroll method gets called internally when there are pan or
 * zoom changes.
 */
public class SampleMapView extends MapView {

    private final long mEventsTimeout = 250L;

    private boolean mIsTouched = false;
    private GeoPoint mLastCenterPosition;
    private int mLastZoomLevel;

    private SampleMapView.OnChangeListener mChangeListener;

    private final Runnable mOnChangeTask = new Runnable() {
        @Override
        public void run() {
            if (mChangeListener != null)
                mChangeListener.onChange(SampleMapView.this,
                        mLastCenterPosition, getMapCenter(),
                        mLastZoomLevel, getZoomLevel());

            mLastCenterPosition = getMapCenter();
            mLastZoomLevel = getZoomLevel();
        }
    };

    public SampleMapView(final Context context, final String apiKey) {
        super(context, apiKey);
        init();
    }

    public SampleMapView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SampleMapView(final Context context, final AttributeSet attrs,
                         final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLastCenterPosition = getMapCenter();
        mLastZoomLevel = getZoomLevel();
    }

    public void setOnChangeListener(final SampleMapView.OnChangeListener
                                            listener) {
        mChangeListener = listener;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        mIsTouched = (event.getAction() != MotionEvent.ACTION_UP);

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (isSpanChange() || isZoomChange()) {
            resetMapChangeTimer();
        }
    }

    private void resetMapChangeTimer() {
        removeCallbacks(mOnChangeTask);
        postDelayed(mOnChangeTask, mEventsTimeout);
    }

    private boolean isSpanChange() {
        return !mIsTouched && !getMapCenter().equals(mLastCenterPosition);
    }

    private boolean isZoomChange() {
        return (getZoomLevel() != mLastZoomLevel);
    }

    /**
     * Listener that will receive zoom and pan events.
     */
    public static abstract class OnChangeListener {
        private void onChange(final MapView view, final GeoPoint oldCenter,
                              final GeoPoint newCenter, final int oldZoom,
                              final int newZoom) {
            if ((!newCenter.equals(oldCenter)) && (newZoom != oldZoom)) {
                onZoomPanChange(view, oldCenter, newCenter, oldZoom, newZoom);
            } else if (!newCenter.equals(oldCenter)) {
                onPanChange(view, oldZoom, newZoom);
            } else if (newZoom != oldZoom) {
                onZoomChange(view, oldZoom, newZoom);
            }
        }

        public abstract void onZoomChange(final MapView mapView,
                                          final int oldZoom,
                                          final int newZoom);

        public abstract void onPanChange(final MapView mapView,
                                         final int oldCenter,
                                         final int newCenter);

        public abstract void onZoomPanChange(final MapView mapView,
                                             final GeoPoint oldCenter,
                                             final GeoPoint newCenter,
                                             final int oldZoom,
                                             final int newZoom);
    }
}