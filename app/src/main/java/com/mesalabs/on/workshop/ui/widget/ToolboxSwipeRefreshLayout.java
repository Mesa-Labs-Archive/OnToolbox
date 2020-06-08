package com.mesalabs.on.workshop.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.samsung.android.ui.swiperefreshlayout.widget.SeslSwipeRefreshLayout;

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

public class ToolboxSwipeRefreshLayout extends SeslSwipeRefreshLayout {
    private float mPrevX;
    private int mTouchSlop;

    public ToolboxSwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            mPrevX = obtain.getX();
            obtain.recycle();
        } else if (action == MotionEvent.ACTION_MOVE && Math.abs(motionEvent.getX() - mPrevX) > ((float) mTouchSlop)) {
            return false;
        }

        return super.onInterceptTouchEvent(motionEvent);
    }
}