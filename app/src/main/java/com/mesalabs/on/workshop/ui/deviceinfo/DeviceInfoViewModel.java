package com.mesalabs.on.workshop.ui.deviceinfo;

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

public class DeviceInfoViewModel {
    private boolean mIsItem;
    private String mItemId;
    private String mItemTitle;

    public DeviceInfoViewModel() {
        mIsItem = false;
        mItemId = "spacing";
        mItemTitle = "";
    }

    public DeviceInfoViewModel(String id, String title) {
        mIsItem = true;
        mItemId = id;
        mItemTitle = title;
    }

    public boolean getIsItem() {
        return mIsItem;
    }

    public String getItemId() {
        return mItemId;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

}
