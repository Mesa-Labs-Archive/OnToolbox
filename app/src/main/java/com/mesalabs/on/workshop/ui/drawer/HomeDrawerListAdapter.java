package com.mesalabs.on.workshop.ui.drawer;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesalabs.cerberus.ui.callback.OnSingleClickListener;
import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.activity.tb.HomeActivity;
import com.mesalabs.on.workshop.ui.appslist.GenericAppsListViewHolder;
import com.mesalabs.on.workshop.utils.PreferencesUtils;
import com.samsung.android.ui.recyclerview.widget.SeslRecyclerView;

import java.util.List;

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

public class HomeDrawerListAdapter extends SeslRecyclerView.Adapter<HomeDrawerListViewHolder> {
    private int mCurrentItem = PreferencesUtils.getLatestFragment();
    private HomeActivity mActivity;
    private List<HomeDrawerListViewModel> mModel;

    HomeDrawerListAdapter(HomeActivity activity, List<HomeDrawerListViewModel> model) {
        mActivity = activity;
        mModel = model;
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public int getItemViewType(final int position) {
        if (mModel.get(position).getIsItem()) return 0;
        return 1;
    }

    @Override
    public HomeDrawerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = 0;

        switch (viewType) {
            case 0:
                resId = R.layout.mesa_tb_view_drawer_item_layout;
                break;
            case 1:
                resId = R.layout.mesa_tb_view_drawer_divider_layout;
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new HomeDrawerListViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(HomeDrawerListViewHolder holder, final int position) {
        holder.onBindViewHolder(mModel.get(position));
        if (holder.getIsItem()) {
            holder.setItemOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    notifyItemChanged(mCurrentItem);
                    mCurrentItem = position;
                    mActivity.replaceFragmentInActivity(mCurrentItem);
                    PreferencesUtils.setLatestFragment(mCurrentItem);
                    notifyItemChanged(mCurrentItem);
                }
            });

            setSelected(holder, mCurrentItem == position);
        }
    }

    private void setSelected(HomeDrawerListViewHolder holder, boolean selected) {
        TypedValue colorPrimaryDark = new TypedValue();
        mActivity.getTheme().resolveAttribute(R.attr.colorPrimaryDark, colorPrimaryDark, true);
        int greyed = mActivity.getColor(R.color.mesa_tb_drawer_things_color);

        holder.getIcon().setImageTintList(ColorStateList.valueOf(selected ? colorPrimaryDark.data : greyed));
        holder.getTitleText().setTextColor(selected ? colorPrimaryDark.data : greyed);
        holder.getTitleText().setTypeface(Typeface.create(holder.getTitleText().getTypeface(), selected ? Typeface.BOLD : Typeface.NORMAL));
    }
}
