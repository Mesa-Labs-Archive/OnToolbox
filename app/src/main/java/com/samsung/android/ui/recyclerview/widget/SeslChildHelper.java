package com.samsung.android.ui.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

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

class SeslChildHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "ChildrenHelper";
    final Callback mCallback;
    final Bucket mBucket;
    final List<View> mHiddenViews;

    SeslChildHelper(Callback callback) {
        mCallback = callback;
        mBucket = new Bucket();
        mHiddenViews = new ArrayList<View>();
    }

    private void hideViewInternal(View child) {
        mHiddenViews.add(child);
        mCallback.onEnteredHiddenState(child);
    }

    private boolean unhideViewInternal(View child) {
        if (mHiddenViews.remove(child)) {
            mCallback.onLeftHiddenState(child);
            return true;
        } else {
            return false;
        }
    }

    void addView(View child, boolean hidden) {
        addView(child, -1, hidden);
    }

    void addView(View child, int index, boolean hidden) {
        final int offset;
        if (index < 0) {
            offset = mCallback.getChildCount();
        } else {
            offset = getOffset(index);
        }
        mBucket.insert(offset, hidden);
        if (hidden) {
            hideViewInternal(child);
        }
        mCallback.addView(child, offset);
        if (DEBUG) {
            LogUtils.d(TAG, "addViewAt " + index + ",h:" + hidden + ", " + this);
        }
    }

    private int getOffset(int index) {
        if (index < 0) {
            return -1;
        }
        final int limit = mCallback.getChildCount();
        int offset = index;
        while (offset < limit) {
            final int removedBefore = mBucket.countOnesBefore(offset);
            final int diff = index - (offset - removedBefore);
            if (diff == 0) {
                while (mBucket.get(offset)) {
                    offset++;
                }
                return offset;
            } else {
                offset += diff;
            }
        }
        return -1;
    }

    void removeView(View view) {
        int index = mCallback.indexOfChild(view);
        if (index < 0) {
            return;
        }
        if (mBucket.remove(index)) {
            unhideViewInternal(view);
        }
        mCallback.removeViewAt(index);
        if (DEBUG) {
            LogUtils.d(TAG, "remove View off:" + index + "," + this);
        }
    }

    void removeViewAt(int index) {
        final int offset = getOffset(index);
        final View view = mCallback.getChildAt(offset);
        if (view == null) {
            return;
        }
        if (mBucket.remove(offset)) {
            unhideViewInternal(view);
        }
        mCallback.removeViewAt(offset);
        if (DEBUG) {
            LogUtils.d(TAG, "removeViewAt " + index + ", off:" + offset + ", " + this);
        }
    }

    View getChildAt(int index) {
        final int offset = getOffset(index);
        return mCallback.getChildAt(offset);
    }

    void removeAllViewsUnfiltered() {
        mBucket.reset();
        for (int i = mHiddenViews.size() - 1; i >= 0; i--) {
            mCallback.onLeftHiddenState(mHiddenViews.get(i));
            mHiddenViews.remove(i);
        }
        mCallback.removeAllViews();
        if (DEBUG) {
            LogUtils.d(TAG, "removeAllViewsUnfiltered");
        }
    }

    View findHiddenNonRemovedView(int position) {
        final int count = mHiddenViews.size();
        for (int i = 0; i < count; i++) {
            final View view = mHiddenViews.get(i);
            SeslRecyclerView.ViewHolder holder = mCallback.getChildViewHolder(view);
            if (holder.getLayoutPosition() == position && !holder.isInvalid() && !holder.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    void attachViewToParent(View child, int index, ViewGroup.LayoutParams layoutParams,
            boolean hidden) {
        final int offset;
        if (index < 0) {
            offset = mCallback.getChildCount();
        } else {
            offset = getOffset(index);
        }
        mBucket.insert(offset, hidden);
        if (hidden) {
            hideViewInternal(child);
        }
        mCallback.attachViewToParent(child, offset, layoutParams);
        if (DEBUG) {
            LogUtils.d(TAG, "attach view to parent index:" + index + ",off:" + offset + "," + "h:" + hidden + ", " + this);
        }
    }

    int getChildCount() {
        return mCallback.getChildCount() - mHiddenViews.size();
    }

    int getUnfilteredChildCount() {
        return mCallback.getChildCount();
    }

    View getUnfilteredChildAt(int index) {
        return mCallback.getChildAt(index);
    }

    void detachViewFromParent(int index) {
        final int offset = getOffset(index);
        mBucket.remove(offset);
        mCallback.detachViewFromParent(offset);
        if (DEBUG) {
            LogUtils.d(TAG, "detach view from parent " + index + ", off:" + offset);
        }
    }

    int indexOfChild(View child) {
        final int index = mCallback.indexOfChild(child);
        if (index == -1) {
            return -1;
        }
        if (mBucket.get(index)) {
            if (DEBUG) {
                throw new IllegalArgumentException("cannot get index of a hidden child");
            } else {
                return -1;
            }
        }
        return index - mBucket.countOnesBefore(index);
    }

    boolean isHidden(View view) {
        return mHiddenViews.contains(view);
    }

    void hide(View view) {
        final int offset = mCallback.indexOfChild(view);
        if (offset < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        if (DEBUG && mBucket.get(offset)) {
            throw new RuntimeException("trying to hide same view twice, how come ? " + view);
        }
        mBucket.set(offset);
        hideViewInternal(view);
        if (DEBUG) {
            LogUtils.d(TAG, "hiding child " + view + " at offset " + offset + ", " + this);
        }
    }

    void unhide(View view) {
        final int offset = mCallback.indexOfChild(view);
        if (offset < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        if (!mBucket.get(offset)) {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
        mBucket.clear(offset);
        unhideViewInternal(view);
    }

    @Override
    public String toString() {
        return mBucket.toString() + ", hidden list:" + mHiddenViews.size();
    }

    boolean removeViewIfHidden(View view) {
        final int index = mCallback.indexOfChild(view);
        if (index == -1) {
            if (unhideViewInternal(view) && DEBUG) {
                throw new IllegalStateException("view is in hidden list but not in view group");
            }
            return true;
        }
        if (mBucket.get(index)) {
            mBucket.remove(index);
            if (!unhideViewInternal(view) && DEBUG) {
                throw new IllegalStateException("removed a hidden view but it is not in hidden views list");
            }
            mCallback.removeViewAt(index);
            return true;
        }
        return false;
    }


    static class Bucket {
        static final int BITS_PER_WORD = Long.SIZE;
        static final long LAST_BIT = 1L << (Long.SIZE - 1);
        long mData = 0;
        Bucket mNext;

        void set(int index) {
            if (index >= BITS_PER_WORD) {
                ensureNext();
                mNext.set(index - BITS_PER_WORD);
            } else {
                mData |= 1L << index;
            }
        }

        private void ensureNext() {
            if (mNext == null) {
                mNext = new Bucket();
            }
        }

        void clear(int index) {
            if (index >= BITS_PER_WORD) {
                if (mNext != null) {
                    mNext.clear(index - BITS_PER_WORD);
                }
            } else {
                mData &= ~(1L << index);
            }

        }

        boolean get(int index) {
            if (index >= BITS_PER_WORD) {
                ensureNext();
                return mNext.get(index - BITS_PER_WORD);
            } else {
                return (mData & (1L << index)) != 0;
            }
        }

        void reset() {
            mData = 0;
            if (mNext != null) {
                mNext.reset();
            }
        }

        void insert(int index, boolean value) {
            if (index >= BITS_PER_WORD) {
                ensureNext();
                mNext.insert(index - BITS_PER_WORD, value);
            } else {
                final boolean lastBit = (mData & LAST_BIT) != 0;
                long mask = (1L << index) - 1;
                final long before = mData & mask;
                final long after = ((mData & ~mask)) << 1;
                mData = before | after;
                if (value) {
                    set(index);
                } else {
                    clear(index);
                }
                if (lastBit || mNext != null) {
                    ensureNext();
                    mNext.insert(0, lastBit);
                }
            }
        }

        boolean remove(int index) {
            if (index >= BITS_PER_WORD) {
                ensureNext();
                return mNext.remove(index - BITS_PER_WORD);
            } else {
                long mask = (1L << index);
                final boolean value = (mData & mask) != 0;
                mData &= ~mask;
                mask = mask - 1;
                final long before = mData & mask;
                final long after = Long.rotateRight(mData & ~mask, 1);
                mData = before | after;
                if (mNext != null) {
                    if (mNext.get(0)) {
                        set(BITS_PER_WORD - 1);
                    }
                    mNext.remove(0);
                }
                return value;
            }
        }

        int countOnesBefore(int index) {
            if (mNext == null) {
                if (index >= BITS_PER_WORD) {
                    return Long.bitCount(mData);
                }
                return Long.bitCount(mData & ((1L << index) - 1));
            }
            if (index < BITS_PER_WORD) {
                return Long.bitCount(mData & ((1L << index) - 1));
            } else {
                return mNext.countOnesBefore(index - BITS_PER_WORD) + Long.bitCount(mData);
            }
        }

        @Override
        public String toString() {
            return mNext == null ? Long.toBinaryString(mData) : mNext.toString() + "xx" + Long.toBinaryString(mData);
        }
    }

    interface Callback {
        int getChildCount();

        void addView(View child, int index);

        int indexOfChild(View view);

        void removeViewAt(int index);

        View getChildAt(int offset);

        void removeAllViews();

        SeslRecyclerView.ViewHolder getChildViewHolder(View view);

        void attachViewToParent(View child, int index, ViewGroup.LayoutParams layoutParams);

        void detachViewFromParent(int offset);

        void onEnteredHiddenState(View child);

        void onLeftHiddenState(View child);
    }
}
