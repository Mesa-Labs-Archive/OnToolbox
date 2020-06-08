package com.mesalabs.on.workshop.ui.appslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import com.mesalabs.cerberus.ui.callback.OnSingleClickListener;
import com.mesalabs.on.workshop.R;
import com.samsung.android.ui.app.SeslAlertDialog;
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

public class GenericAppsListAdapter extends SeslRecyclerView.Adapter<GenericAppsListViewHolder> {
    private Context mContext;
    private List<GenericAppsListViewModel> mModel;

    public GenericAppsListAdapter(Context context, List<GenericAppsListViewModel> model) {
        mContext = context;
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
        if (position + 1 == getItemCount()) return 2;
        return 1;
    }

    @Override
    public GenericAppsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = 0;

        switch (viewType) {
            case 0:
                resId = R.layout.mesa_tb_view_home_app_item_layout;
                break;
            case 1:
                resId = R.layout.mesa_tb_view_spacing_layout;
                break;
            case 2:
                resId = R.layout.mesa_tb_view_bottom_spacing_layout;
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new GenericAppsListViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(GenericAppsListViewHolder holder, final int position) {
        holder.onBindViewHolder(mModel.get(position));
        if (holder.getIsItem()) {
            holder.setItemOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    View dialogView = inflater.inflate(R.layout.mesa_tb_layout_dialog_app_item, null);

                    new SeslAlertDialog.Builder(mContext)
                            .setCancelable(true)
                            .setView(dialogView)
                            .setPositiveButton("Install", null)
                            .show();
                }
            });
        }
    }

}
