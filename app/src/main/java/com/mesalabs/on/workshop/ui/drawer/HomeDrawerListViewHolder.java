package com.mesalabs.on.workshop.ui.drawer;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mesalabs.on.workshop.R;
import com.samsung.android.ui.recyclerview.widget.SeslRecyclerView;

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

public class HomeDrawerListViewHolder extends SeslRecyclerView.ViewHolder {
    private boolean mIsItem;

    private FrameLayout mParentView;
    //private LinearLayout mItemContainer;
    private ImageView mIcon;
    private TextView mTitle;

    HomeDrawerListViewHolder(View itemView, int viewType) {
        super(itemView);

        mIsItem = viewType == 0;

        if (mIsItem)  {
            mParentView = (FrameLayout) itemView;
            //mItemContainer = mParentView.findViewById(R.id.item_container);
            mIcon = mParentView.findViewById(android.R.id.icon);
            mTitle = mParentView.findViewById(android.R.id.title);
        }
    }

    public void onBindViewHolder(HomeDrawerListViewModel viewModel) {
        if (mIsItem)  {
            mIcon.setImageDrawable(viewModel.getIcon());
            mTitle.setText(viewModel.getText());
        }
    }

    public FrameLayout getItemView() {
        return mParentView;
    }

    public ImageView getIcon() {
        return mIcon;
    }

    public TextView getTitleText() {
        return mTitle;
    }

    public boolean getIsItem() {
        return mIsItem;
    }

    public void setItemOnClickListener(View.OnClickListener ocl) {
        if (mIsItem)  {
            mParentView.setOnClickListener(ocl);
        }
    }

}
