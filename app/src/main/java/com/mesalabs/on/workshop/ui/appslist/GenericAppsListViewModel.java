package com.mesalabs.on.workshop.ui.appslist;

import android.graphics.drawable.Drawable;

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

public class GenericAppsListViewModel {
    public static int PACKAGE_APP_NAME = 0;
    public static int PACKAGE_VERSION_CODE = 1;
    public static int PACKAGE_NAME = 2;
    public static int PACKAGE_DESCRIPTION = 3;

    private boolean mIsItem;
    private boolean mShowBadge;
    private Drawable mAppIcon;
    private String mAppName;
    private int mAppVersionCode;
    private String mAppPackageName;
    private String mAppDescription;

    public GenericAppsListViewModel() {
        mIsItem = false;
        mShowBadge = false;
        mAppIcon = null;
        mAppName = "";
        mAppVersionCode = -1;
        mAppPackageName = "";
        mAppDescription = "";
    }

    public GenericAppsListViewModel(boolean badge, Drawable icon, String[] texts) {
        mIsItem = true;
        mShowBadge = badge;
        mAppIcon = icon;
        mAppName = texts[PACKAGE_APP_NAME];
        mAppVersionCode = Integer.parseInt(texts[PACKAGE_VERSION_CODE]);
        mAppPackageName = texts[PACKAGE_NAME];
        mAppDescription = texts[PACKAGE_DESCRIPTION];
    }

    public boolean getIsItem() {
        return mIsItem;
    }

    public boolean showBadge() {
        return mShowBadge;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public String getAppName() {
        return mAppName;
    }

    public String getAppVersion() {
        return mAppVersionCode / 10000 + "." + (mAppVersionCode % 10000) / 100 + "." + (mAppVersionCode % 1000) / 100;
    }

    public int getAppVersionCode() {
        return mAppVersionCode;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public String getAppDescription() {
        return mAppDescription;
    }

}
