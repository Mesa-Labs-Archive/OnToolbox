package com.mesalabs.on.workshop.ui.drawer;

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

public class HomeDrawerListViewModel {
    private boolean mIsItem;
    private Drawable mIcon;
    private String mText;
    private boolean mNeedsInternet;

    public HomeDrawerListViewModel(boolean isItem, Drawable icon, String text, int needsInternet) {
        mIsItem = isItem;

        if (mIsItem) {
            mIcon = icon;
            mText = text;
            mNeedsInternet = needsInternet == 1;
        } else {
            mIcon = null;
            mText = "";
            mNeedsInternet = false;
        }
    }

    public boolean getIsItem() {
        return mIsItem;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public String getText() {
        return mText;
    }

    public boolean isInternetNeeded() {
        return mNeedsInternet;
    }

}