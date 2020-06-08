package com.mesalabs.on.workshop.utils;

import com.mesalabs.cerberus.utils.SharedPreferencesUtils;

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

public class PreferencesUtils {
    public static String KEY_LATEST_FRAGMENT = "mesa_fragment";
    public static String KEY_IS_APP_UPDATE_AVAILABLE = "mesa_is_app_update_available";

    private static SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance();


    public static boolean getIsAppUpdateAvailable() {
        return sp.getBoolean(KEY_IS_APP_UPDATE_AVAILABLE, false);
    }

    public static void setIsAppUpdateAvailable(boolean value) {
        sp.put(KEY_IS_APP_UPDATE_AVAILABLE, value);
    }

    public static int getLatestFragment() {
        return sp.getInt(KEY_LATEST_FRAGMENT, 0);
    }

    public static void setLatestFragment(int value) {
        sp.put(KEY_LATEST_FRAGMENT, value);
    }

}
