package com.example.bastian.tienda.Tools;

import android.util.Log;

import com.example.bastian.tienda.Constants;

import static android.content.ContentValues.TAG;

/**
 * Created by Bastian on 08-10-2017.
 */

public class Tools {
    private static final String TAG = "Tools";

    /**
     * Print a line
     *
     * @param tag  The tag
     * @param text The text
     */
    public static void logLine(String tag, String text) {
        if (Constants.TRACE_ALLOWED) {
            Log.d(tag, text);
        }
    }
}
