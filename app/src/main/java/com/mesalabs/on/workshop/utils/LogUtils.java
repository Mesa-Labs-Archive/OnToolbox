package com.mesalabs.on.workshop.utils;

import android.util.Log;

import com.mesalabs.on.workshop.OnWorkshopApp;

/*
 * On Workshop
 *
 * Coded by BlackMesa @2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 */

public class LogUtils {
    // Verbose
    public static void v(String tag, String msg) {
        if (OnWorkshopApp.isDebugBuild())
            Log.v("OnToolbox: " + tag, msg);
    }

    // Debug
    public static void d(String tag, String msg) {
        if (OnWorkshopApp.isDebugBuild())
            Log.d("OnToolbox: " + tag, msg);
    }

    public static void d(String tag, String msg, Exception e) {
        if (OnWorkshopApp.isDebugBuild())
            Log.d("OnToolbox: " + tag, msg, e);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (OnWorkshopApp.isDebugBuild())
            Log.d("OnToolbox: " + tag, msg, t);
    }

    // Info
    public static void i(String tag, String msg) {
        if (OnWorkshopApp.isDebugBuild())
            Log.i("OnToolbox: " + tag, msg);
    }

    public static void i(String tag, String msg, Exception e) {
        if (OnWorkshopApp.isDebugBuild())
            Log.i("OnToolbox: " + tag, msg, e);
    }

    // Warn
    public static void w(String tag, String msg) {
        if (OnWorkshopApp.isDebugBuild())
            Log.w("OnToolbox: " + tag, msg);
    }

    public static void w(String tag, String msg, Exception e) {
        if (OnWorkshopApp.isDebugBuild())
            Log.w("OnToolbox: " + tag, msg, e);
    }

    // Error
    public static void e(String tag, String msg) {
        if (OnWorkshopApp.isDebugBuild())
            Log.e("OnToolbox: " + tag, msg);
    }
}
