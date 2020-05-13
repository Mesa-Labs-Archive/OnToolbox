package com.mesalabs.on.toolbox.utils;

import android.util.Log;

import com.mesalabs.on.toolbox.OnToolboxApp;

/*
 * On Toolbox
 *
 * Coded by BlackMesa @2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * ULTRA-MEGA-PRIVATE SOURCE CODE. SHARING TO DEVKINGS TEAM
 * EXTERNALS IS PROHIBITED AND WILL BE PUNISHED WITH ANAL ABUSE.
 */

public class LogUtils {
    // Verbose
    public static void v(String tag, String msg) {
        if (OnToolboxApp.isDebugBuild())
            Log.v("OnToolbox: " + tag, msg);
    }

    // Debug
    public static void d(String tag, String msg) {
        if (OnToolboxApp.isDebugBuild())
            Log.d("OnToolbox: " + tag, msg);
    }

    public static void d(String tag, String msg, Exception e) {
        if (OnToolboxApp.isDebugBuild())
            Log.d("OnToolbox: " + tag, msg, e);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (OnToolboxApp.isDebugBuild())
            Log.d("OnToolbox: " + tag, msg, t);
    }

    // Info
    public static void i(String tag, String msg) {
        if (OnToolboxApp.isDebugBuild())
            Log.i("OnToolbox: " + tag, msg);
    }

    public static void i(String tag, String msg, Exception e) {
        if (OnToolboxApp.isDebugBuild())
            Log.i("OnToolbox: " + tag, msg, e);
    }

    // Warn
    public static void w(String tag, String msg) {
        if (OnToolboxApp.isDebugBuild())
            Log.w("OnToolbox: " + tag, msg);
    }

    public static void w(String tag, String msg, Exception e) {
        if (OnToolboxApp.isDebugBuild())
            Log.w("OnToolbox: " + tag, msg, e);
    }

    // Error
    public static void e(String tag, String msg) {
        if (OnToolboxApp.isDebugBuild())
            Log.e("OnToolbox: " + tag, msg);
    }
}
