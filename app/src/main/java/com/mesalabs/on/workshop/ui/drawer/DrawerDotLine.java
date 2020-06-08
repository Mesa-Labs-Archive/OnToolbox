package com.mesalabs.on.workshop.ui.drawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mesalabs.on.workshop.R;

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

// kang from SeslTabDotLineIndicator
public class DrawerDotLine extends View {
    private int mDiameter = 1;
    private int mInterval = 2;
    private Paint mPaint = new Paint();
    private float mScaleFrom;
    private final float mScaleFromDiff;
    private int mWidth;

    public DrawerDotLine(Context context) {
        this(context, null);
    }

    public DrawerDotLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerDotLine(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DrawerDotLine(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mDiameter = (int) TypedValue.applyDimension(1, 2.5f, context.getResources().getDisplayMetrics());
        mInterval = (int) TypedValue.applyDimension(1, 2.5f, context.getResources().getDisplayMetrics());

        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.mesa_tb_drawer_item_divider_color, null));

        mScaleFromDiff = TypedValue.applyDimension(1, 5.0f, context.getResources().getDisplayMetrics());
    }

    private void updateDotLineScaleFrom() {
        if (mWidth != getWidth() || mWidth == 0) {
            mWidth = getWidth();
            if (mWidth <= 0) {
                mScaleFrom = 0.9f;
            } else {
                mScaleFrom = (((float) mWidth) - mScaleFromDiff) / ((float) mWidth);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateDotLineScaleFrom();
        int width = getWidth();
        int height = getHeight();
        int circleCount = ((width - mDiameter) / (mInterval + mDiameter)) + 1;
        int spaceCount = circleCount - 1;
        int startInterval = (int) ((((float) mDiameter) / 2.0f) + 0.5f);
        int leftSpace = (width - mDiameter) - ((mInterval + mDiameter) * (circleCount - 1));
        if (mDiameter % 2 != 0) {
            leftSpace--;
        }
        int offSet = 0;
        int offSetOnePixel = 0;
        if (spaceCount > 0) {
            offSet = leftSpace / spaceCount;
            offSetOnePixel = leftSpace % spaceCount;
        }
        int dis = 0;
        for (int i = 0; i < circleCount; i++) {
            canvas.drawCircle((float) (startInterval + dis), (float) (height / 2), ((float) mDiameter) / 2.0f, mPaint);
            dis += mDiameter + mInterval + offSet;
            if (i < offSetOnePixel) {
                dis++;
            }
        }
        invalidate();
    }
}