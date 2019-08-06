package com.samsung.android.ui.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/*
 * Cerberus Core App
 *
 * Coded by Samsung. All rights reserved to their respective owners.
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

public abstract class SeslAbsIndicatorView extends View {
    int mIndicatorColor;

    abstract void onHide();

    abstract void onPressed();

    abstract void onReleased();

    abstract void onSetSelectedIndicatorColor(int i);

    abstract void onShow();

    public SeslAbsIndicatorView(Context context) {
        super(context);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SeslAbsIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSelectedIndicatorColor(int color) {
        mIndicatorColor = color;
        onSetSelectedIndicatorColor(mIndicatorColor);
    }

    public void setPressed() {
        onPressed();
    }

    public void setReleased() {
        onReleased();
    }

    public void setHide() {
        onHide();
    }

    public void setHideImmediatly() { }

    public void setShow() {
        onShow();
    }
}
