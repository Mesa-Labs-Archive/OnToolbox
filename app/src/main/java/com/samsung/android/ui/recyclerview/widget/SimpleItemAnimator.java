package com.samsung.android.ui.recyclerview.widget;

import android.view.View;

import com.samsung.android.ui.recyclerview.widget.SeslRecyclerView.ViewHolder;
import com.mesalabs.on.workshop.utils.LogUtils;

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

public abstract class SimpleItemAnimator extends SeslRecyclerView.ItemAnimator {
    private static final boolean DEBUG = false;
    private static final String TAG = "SimpleItemAnimator";
    boolean mSupportsChangeAnimations = true;

    @SuppressWarnings("unused")
    public boolean getSupportsChangeAnimations() {
        return mSupportsChangeAnimations;
    }

    public void setSupportsChangeAnimations(boolean supportsChangeAnimations) {
        mSupportsChangeAnimations = supportsChangeAnimations;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(SeslRecyclerView.ViewHolder viewHolder) {
        return !mSupportsChangeAnimations || viewHolder.isInvalid();
    }

    @Override
    public boolean animateDisappearance(ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        int oldLeft = preLayoutInfo.left;
        int oldTop = preLayoutInfo.top;
        View disappearingItemView = viewHolder.itemView;
        int newLeft = postLayoutInfo == null ? disappearingItemView.getLeft() : postLayoutInfo.left;
        int newTop = postLayoutInfo == null ? disappearingItemView.getTop() : postLayoutInfo.top;
        if (!viewHolder.isRemoved() && (oldLeft != newLeft || oldTop != newTop)) {
            disappearingItemView.layout(newLeft, newTop, newLeft + disappearingItemView.getWidth(), newTop + disappearingItemView.getHeight());
            if (DEBUG) {
                LogUtils.d(TAG, "DISAPPEARING: " + viewHolder + " with view " + disappearingItemView);
            }
            return animateMove(viewHolder, oldLeft, oldTop, newLeft, newTop);
        } else {
            if (DEBUG) {
                LogUtils.d(TAG, "REMOVED: " + viewHolder + " with view " + disappearingItemView);
            }
            return animateRemove(viewHolder);
        }
    }

    @Override
    public boolean animateAppearance(ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        if (preLayoutInfo != null && (preLayoutInfo.left != postLayoutInfo.left || preLayoutInfo.top != postLayoutInfo.top)) {
            if (DEBUG) {
                LogUtils.d(TAG, "APPEARING: " + viewHolder + " with view " + viewHolder);
            }
            return animateMove(viewHolder, preLayoutInfo.left, preLayoutInfo.top, postLayoutInfo.left, postLayoutInfo.top);
        } else {
            if (DEBUG) {
                LogUtils.d(TAG, "ADDED: " + viewHolder + " with view " + viewHolder);
            }
            return animateAdd(viewHolder);
        }
    }

    @Override
    public boolean animatePersistence(ViewHolder viewHolder, ItemHolderInfo preInfo, ItemHolderInfo postInfo) {
        if (preInfo.left != postInfo.left || preInfo.top != postInfo.top) {
            if (DEBUG) {
                LogUtils.d(TAG, "PERSISTENT: " + viewHolder
                        + " with view " + viewHolder.itemView);
            }
            return animateMove(viewHolder, preInfo.left, preInfo.top, postInfo.left, postInfo.top);
        }
        dispatchMoveFinished(viewHolder);
        return false;
    }

    @Override
    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, ItemHolderInfo preInfo, ItemHolderInfo postInfo) {
        if (DEBUG) {
            LogUtils.d(TAG, "CHANGED: " + oldHolder + " with view " + oldHolder.itemView);
        }
        final int fromLeft = preInfo.left;
        final int fromTop = preInfo.top;
        final int toLeft, toTop;
        if (newHolder.shouldIgnore()) {
            toLeft = preInfo.left;
            toTop = preInfo.top;
        } else {
            toLeft = postInfo.left;
            toTop = postInfo.top;
        }
        return animateChange(oldHolder, newHolder, fromLeft, fromTop, toLeft, toTop);
    }

    public abstract boolean animateRemove(ViewHolder holder);

    public abstract boolean animateAdd(ViewHolder holder);

    public abstract boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY);

    public abstract boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop);

    public final void dispatchRemoveFinished(ViewHolder item) {
        onRemoveFinished(item);
        dispatchAnimationFinished(item);
    }

    public final void dispatchMoveFinished(ViewHolder item) {
        onMoveFinished(item);
        dispatchAnimationFinished(item);
    }

    public final void dispatchAddFinished(ViewHolder item) {
        onAddFinished(item);
        dispatchAnimationFinished(item);
    }

    public final void dispatchChangeFinished(ViewHolder item, boolean oldItem) {
        onChangeFinished(item, oldItem);
        dispatchAnimationFinished(item);
    }

    public final void dispatchRemoveStarting(ViewHolder item) {
        onRemoveStarting(item);
    }

    public final void dispatchMoveStarting(ViewHolder item) {
        onMoveStarting(item);
    }

    public final void dispatchAddStarting(ViewHolder item) {
        onAddStarting(item);
    }

    public final void dispatchChangeStarting(ViewHolder item, boolean oldItem) {
        onChangeStarting(item, oldItem);
    }

    @SuppressWarnings("UnusedParameters")
    public void onRemoveStarting(ViewHolder item) { }

    public void onRemoveFinished(ViewHolder item) { }

    @SuppressWarnings("UnusedParameters")
    public void onAddStarting(ViewHolder item) {
    }

    public void onAddFinished(ViewHolder item) { }

    @SuppressWarnings("UnusedParameters")
    public void onMoveStarting(ViewHolder item) { }

    public void onMoveFinished(ViewHolder item) { }

    @SuppressWarnings("UnusedParameters")
    public void onChangeStarting(ViewHolder item, boolean oldItem) { }

    public void onChangeFinished(ViewHolder item, boolean oldItem) { }
}

