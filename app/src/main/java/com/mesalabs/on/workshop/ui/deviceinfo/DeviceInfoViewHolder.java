package com.mesalabs.on.workshop.ui.deviceinfo;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mesalabs.cerberus.ui.callback.OnSingleClickListener;
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

public class DeviceInfoViewHolder extends SeslRecyclerView.ViewHolder {
    private boolean mIsItem;

    private LinearLayout mParentView;
    private TextView mItemTitleTextView;
    private TextView mItemSummaryTextView;

    DeviceInfoViewHolder(View itemView, int viewType) {
        super(itemView);

        mIsItem = viewType == 0;

        if (mIsItem) {
            mParentView = (LinearLayout) itemView;
            mParentView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) { }
            });
            mItemTitleTextView = mParentView.findViewById(android.R.id.title);
            mItemSummaryTextView = mParentView.findViewById(android.R.id.summary);
        }
    }

    public void onBindViewHolder(DeviceInfoViewModel viewModel) {
        if (mIsItem)  {
            mItemTitleTextView.setText(viewModel.getItemTitle());
        }
    }

    public boolean getIsItem() {
        return mIsItem;
    }

    public void setItemOnClickListener(View.OnClickListener ocl) {
        if (mIsItem)  {
            mParentView.setOnClickListener(ocl);
        }
    }

    public void setItemSummaryText(CharSequence text) {
        if (mIsItem)  {
            mItemSummaryTextView.setText(text);
        }
    }

}
