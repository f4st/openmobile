package com.softserve.maps.overlay;

import android.content.Context;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.softserve.maps.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ItemizedOverlay class. Every OverlayItem added to
 * MapItemizedOverlay will have same marker, custom title,
 * snippet and GeoPoint coordinates.
 * <p/>
 * Class provides possibility to add implementation of OnTapListener to
 * instance of MapItemizedOverlay.
 */
public class MapItemizedOverlay extends ItemizedOverlay {

    private final List<OverlayItem> overlays;
    private OnTapListener listener;

    public MapItemizedOverlay(final Context context) {
        super(boundCenterBottom(context.getResources()
                .getDrawable(R.drawable.user_location_marker)));
        overlays = new ArrayList<OverlayItem>();
    }

    @Override
    protected OverlayItem createItem(final int position) {
        return overlays.get(position);
    }

    @Override
    public int size() {
        return overlays.size();
    }

    public void addItem(final OverlayItem item) {
        overlays.add(item);
        populate();
    }

    @Override
    protected boolean onTap(final int position) {
        if (listener != null) {
            listener.onTap(this, overlays.get(position));
        }
        return true;
    }

    /**
     * Adds implementation of OnTapListener interface to class instance
     *
     * @param listener - instance of OnTapListener implementation.
     */
    public void setOnTapListener(final OnTapListener listener) {
        this.listener = listener;
    }

    /**
     * Every time when tap event occurs, OnTapListener.onTap() method
     * will be called.
     */
    public interface OnTapListener {
        void onTap(final ItemizedOverlay itemizedOverlay,
                   final OverlayItem item);
    }
}