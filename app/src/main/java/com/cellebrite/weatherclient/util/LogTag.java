package com.cellebrite.weatherclient.util;

import android.util.Log;

public class LogTag {

    private static final boolean SHOW_LOG = true;

    private static final String LOG_TAG = "LOG_TAG";

    public static void d(String msg) {
        if (SHOW_LOG) Log.d(LOG_TAG, msg);
    }

    public static void e(String msg) {
        if (SHOW_LOG) Log.e(LOG_TAG, msg);
    }

    public static void i(String msg) {
        if (SHOW_LOG) Log.i(LOG_TAG, msg);
    }

    public static void w(String msg) {
        if (SHOW_LOG) Log.w(LOG_TAG, msg);
    }

    public static void v(String msg) {
        if (SHOW_LOG) Log.v(LOG_TAG, msg);
    }

}