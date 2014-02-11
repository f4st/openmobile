/**
 * Copyright (C) 2013 Openmobile World Wide, Inc.
 * All rights reserved.
 * www.openmobileww.com
 */
package com.openmobileww.maps.v2.mapdrawing.bitmaps;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides methods for copying test bitmap from Assets to Files directory
 */
public class TestBitmapFileManager {

    private static final String TEST_IMAGE_NAME = "orange_icon.png";
    private static final String ERROR_MESSAGE = "Error copying file";

    private final Context context;

    public TestBitmapFileManager(final Context context) {
        this.context = context;
    }

    /**
     * Returns path to test bitmap file.
     *
     * @return path to test bitmap.
     */
    public String getTestBitmapPath() {
        if (!isTestBitmapExist()) {
            copyTestBitmap();
        }
        return context.getFilesDir() + "/" + TEST_IMAGE_NAME;
    }

    private boolean isTestBitmapExist() {
        final File file = new File(context.getFilesDir() + "/" + TEST_IMAGE_NAME);
        return file.exists();
    }

    private void copyTestBitmap() {
        final AssetManager assetManager = context.getAssets();

        final InputStream in;
        final OutputStream out;
        try {
            in = assetManager.open(TEST_IMAGE_NAME);
            final File outFile = new File(context.getFilesDir() + "/" + TEST_IMAGE_NAME);
            outFile.createNewFile();
            out = new FileOutputStream(outFile);

            copyFile(in, out);

            in.close();
            out.flush();
            out.close();
        } catch (final IOException exception) {
            throw new Error(ERROR_MESSAGE);
        }
    }

    private void copyFile(final InputStream in, final OutputStream out)
            throws IOException {
        final byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}