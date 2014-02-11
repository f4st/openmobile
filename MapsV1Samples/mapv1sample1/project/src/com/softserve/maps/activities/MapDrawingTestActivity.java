package com.softserve.maps.activities;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.softserve.maps.R;
import com.softserve.maps.util.Constants;

/**
 * Activity with Google Maps V1 API MapView. Uses Overlay subclasses to draw
 * lines, and bitmaps on mapView using Overlay.draw() method.
 */
public class MapDrawingTestActivity extends MapActivity {

    private MapView mapView;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_drawing);

        mapView = (MapView) findViewById(R.id.map_view);
        initializeMap();
        setDrawControlsButtons();
    }

    private void initializeMap() {
        mapView.setSatellite(false);
        mapView.getController().setCenter(new GeoPoint(
                (int) (Constants.florenceLatitude * 1E6),
                (int) (Constants.florenceLongitude * 1E6)));
        mapView.getController().setZoom(6);
    }

    private void setDrawControlsButtons() {
        final View drawLinesButton = findViewById(R.id.draw_lines);
        final View drawBitmapButton = findViewById(R.id.draw_bitmap);
        drawLinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                drawLines();
            }
        });
        drawBitmapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                drawBitmap();
            }
        });
    }

    private void drawLines() {
        mapView.getOverlays().clear();
        mapView.getOverlays().add(new DrawingOverlay());
        mapView.invalidate();
    }

    private void drawBitmap() {
        mapView.getOverlays().clear();
        mapView.getOverlays().add(new MarkerOverlay(this));
        mapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    private static class DrawingOverlay extends Overlay {

        @Override
        public void draw(final Canvas canvas,
                         final MapView mapView,
                         boolean shadow) {
            super.draw(canvas, mapView, shadow);

            final Paint paint = createPaint();
            final Projection projection = mapView.getProjection();

            final GeoPoint nancy = new GeoPoint(
                    (int) (Constants.nancyLatitude * 1E6),
                    (int) (Constants.nancyLongitude * 1E6));
            final GeoPoint florence = new GeoPoint(
                    (int) (Constants.florenceLatitude * 1E6),
                    (int) (Constants.florenceLongitude * 1E6));
            final GeoPoint prague = new GeoPoint(
                    (int) (Constants.pragueLatitude * 1E6),
                    (int) (Constants.pragueLongitude * 1E6));

            final Point pointPrague = new Point();
            final Point pointFlorence = new Point();
            final Point pointNancy = new Point();
            final Path nancyPrague = new Path();
            final Path nancyFlorence = new Path();

            projection.toPixels(florence, pointFlorence);
            projection.toPixels(nancy, pointNancy);

            nancyFlorence.moveTo(pointNancy.x, pointNancy.y);
            nancyFlorence.lineTo(pointFlorence.x, pointFlorence.y);

            projection.toPixels(prague, pointPrague);

            nancyPrague.moveTo(pointNancy.x, pointNancy.y);
            nancyPrague.lineTo(pointPrague.x, pointPrague.y);

            canvas.drawPath(nancyFlorence, paint);
            canvas.drawPath(nancyPrague, paint);
        }

        private Paint createPaint() {
            final Paint paint = new Paint();
            paint.setDither(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(4);

            return paint;
        }
    }

    private static class MarkerOverlay extends Overlay {

        private final GeoPoint geoPoint;
        private final Context context;

        public MarkerOverlay(final Context context) {
            super();
            this.context = context;
            geoPoint = new GeoPoint(
                    (int) (Constants.florenceLatitude * 1E6),
                    (int) (Constants.florenceLongitude * 1E6));
        }

        @Override
        public void draw(final Canvas canvas,
                         final MapView mapView,
                         final boolean shadow) {

            super.draw(canvas, mapView, shadow);
            final Point screenPoint = new Point();
            mapView.getProjection().toPixels(this.geoPoint, screenPoint);

            canvas.drawBitmap(BitmapFactory.decodeResource(
                    context.getResources(),
                    R.drawable.umbrella),
                    screenPoint.x,
                    screenPoint.y,
                    null);
        }
    }
}