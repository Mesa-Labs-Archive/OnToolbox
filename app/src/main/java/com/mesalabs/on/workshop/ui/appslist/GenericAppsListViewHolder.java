package com.mesalabs.on.workshop.ui.appslist;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class GenericAppsListViewHolder extends SeslRecyclerView.ViewHolder {
    private boolean mIsItem;

    private RelativeLayout mParentView;
    private ImageView mAppIconView;
    private TextView mAppNameTextView;
    private TextView mSummaryTextView;
    private TextView mBadgeView;

    GenericAppsListViewHolder(View itemView, int viewType) {
        super(itemView);

        mIsItem = viewType == 0;

        if (mIsItem)  {
            mParentView = (RelativeLayout) itemView;
            mAppIconView = mParentView.findViewById(R.id.mesa_tb_icon_appitem);
            mAppNameTextView = mParentView.findViewById(R.id.mesa_tb_title_text_appitem);
            mSummaryTextView = mParentView.findViewById(R.id.mesa_tb_summary_text_appitem);
            mBadgeView = mParentView.findViewById(R.id.mesa_tb_badge_appitem);
        }
    }

    public void onBindViewHolder(GenericAppsListViewModel viewModel) {
        if (mIsItem) {
            mAppIconView.setImageDrawable(viewModel.getAppIcon());
            mAppNameTextView.setText(viewModel.getAppName());
            if (!viewModel.getAppDescription().isEmpty()) {
                mSummaryTextView.setVisibility(View.VISIBLE);
                mSummaryTextView.setText(viewModel.getAppDescription());
            }
            showBadge(viewModel.showBadge());
        }
    }

    private void showBadge(boolean show) {
        mBadgeView.setVisibility(show ? View.VISIBLE : View.GONE);
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
