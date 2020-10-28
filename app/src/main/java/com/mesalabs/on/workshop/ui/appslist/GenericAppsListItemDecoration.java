package com.mesalabs.on.workshop.ui.appslist;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.mesalabs.cerberus.utils.CerberusException;
import com.mesalabs.cerberus.utils.Utils;
import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.base.BaseAppsListFragment;
import com.samsung.android.ui.recyclerview.widget.SeslLinearLayoutManager;
import com.samsung.android.ui.recyclerview.widget.SeslRecyclerView;
import com.samsung.android.ui.util.SeslRoundedCorner;

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

public class GenericAppsListItemDecoration extends SeslRecyclerView.ItemDecoration {
    private BaseAppsListFragment mListFragment;
    private SeslRoundedCorner mSeslRoundedCornerTop;
    private SeslRoundedCorner mSeslRoundedCornerBottom;
    private Drawable mDivider;
    private int mDividerHeight;

    public GenericAppsListItemDecoration(BaseAppsListFragment fragment) {
        mListFragment = fragment;

        if (mListFragment == null) {
            throw new CerberusException("Fragment is null!!!");
        }

        mSeslRoundedCornerTop = new SeslRoundedCorner(mListFragment.getContext(), true);
        mSeslRoundedCornerTop.setRoundedCornerColor(15 /* all */,
                mListFragment.getResources().getColor(Utils.isNightMode(mListFragment.getContext()) ?
                        R.color.sesl_round_and_bgcolor_dark :
                        R.color.sesl_round_and_bgcolor_light,
                        null));
        mSeslRoundedCornerTop.setRoundedCorners(3 /* top_left|top_right */);
        mSeslRoundedCornerBottom = new SeslRoundedCorner(mListFragment.getContext(), true);
        mSeslRoundedCornerBottom.setRoundedCornerColor(15 /* all */,
                mListFragment.getResources().getColor(Utils.isNightMode(mListFragment.getContext()) ?
                                R.color.sesl_round_and_bgcolor_dark :
                                R.color.sesl_round_and_bgcolor_light,
                        null));
        mSeslRoundedCornerBottom.setRoundedCorners(12 /* bottom_left|bottom_right */);
    }

    @Override
    public void seslOnDispatchDraw(Canvas canvas, SeslRecyclerView recyclerView, SeslRecyclerView.State state) {
        super.seslOnDispatchDraw(canvas, recyclerView, state);

        int childCount = recyclerView.getChildCount();
        int width = recyclerView.getWidth();

        // draw divider for each item
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            GenericAppsListViewHolder viewHolder = (GenericAppsListViewHolder) recyclerView.getChildViewHolder(childAt);
            int y = ((int) childAt.getY()) + childAt.getHeight();

            boolean shallDrawDivider;

            if (recyclerView.getChildAt(i + 1) != null)
                shallDrawDivider = ((GenericAppsListViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i + 1))).getIsItem();
            else
                shallDrawDivider = false;

            if (mDivider != null && viewHolder.getIsItem() && shallDrawDivider) {
                mDivider.setBounds(0, y, width, mDividerHeight + y);
                mDivider.draw(canvas);
            }

            if (!viewHolder.getIsItem()) {
                if (recyclerView.getChildAt(i + 1) != null)
                    mSeslRoundedCornerTop.drawRoundedCorner(recyclerView.getChildAt(i + 1), canvas);
                if (recyclerView.getChildAt(i - 1) != null)
                    mSeslRoundedCornerBottom.drawRoundedCorner(recyclerView.getChildAt(i - 1), canvas);
            }
        }

        mSeslRoundedCornerTop.drawRoundedCorner(canvas);
    }

    private boolean canScrollUp(SeslRecyclerView recyclerView) {
        SeslRecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof SeslLinearLayoutManager)) {
            return false;
        }

        boolean isntFirstItem = ((SeslLinearLayoutManager) layoutManager).findFirstVisibleItemPosition() > 0;
        View childAt = recyclerView.getChildAt(0);

        if (isntFirstItem || childAt == null) {
            return isntFirstItem;
        }
        if (childAt.getTop() < recyclerView.getPaddingTop()) {
            return true;
        } else {
            return false;
        }
    }

    public void setDivider(Drawable d) {
        mDivider = d;
        mDividerHeight = d.getIntrinsicHeight();
        mListFragment.getListView().invalidateItemDecorations();
    }

}
