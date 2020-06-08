package com.mesalabs.on.workshop.ui.deviceinfo;

import android.os.Build;
import android.text.BidiFormatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.fragment.home.DeviceInfoFragment;
import com.mesalabs.on.workshop.utils.DeviceInfoUtils;
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

public class DeviceInfoAdapter extends SeslRecyclerView.Adapter<DeviceInfoViewHolder> {
    private DeviceInfoFragment mFragment;
    private List<DeviceInfoViewModel> mModel;

    public DeviceInfoAdapter(DeviceInfoFragment fragment, List<DeviceInfoViewModel> model) {
        mFragment = fragment;
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
    public DeviceInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = 0;

        switch (viewType) {
            case 0:
                resId = R.layout.mesa_tb_view_home_device_info_item;
                break;
            case 1:
                resId = R.layout.mesa_tb_view_spacing_layout;
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new DeviceInfoViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(DeviceInfoViewHolder holder, final int position) {
        holder.onBindViewHolder(mModel.get(position));
        setupItemFromId(holder, mModel.get(position).getItemId());
    }

    private void setupItemFromId(DeviceInfoViewHolder holder, String itemId) {
        switch (itemId) {
            case "bootloader_version":
                holder.setItemSummaryText(Build.BOOTLOADER);
                break;
            case "build_version":
                holder.setItemSummaryText(BidiFormatter.getInstance().unicodeWrap(Build.DISPLAY));
                break;
            case "device_name":
                holder.setItemSummaryText(DeviceInfoUtils.getDeviceModelName());
                break;
            case "device_soc":
                holder.setItemSummaryText(DeviceInfoUtils.getDeviceSocName());
                break;
            case "enso_version":
                holder.setItemSummaryText(DeviceInfoUtils.getEnsoVersion());
                break;
            case "kernel_version":
                holder.setItemSummaryText(DeviceInfoUtils.getKernelVersion());
                break;
            case "manufacturing_date":
                holder.setItemSummaryText(DeviceInfoUtils.getDeviceManufacturingDate());
                break;
            case "model_number":
                holder.setItemSummaryText(DeviceInfoUtils.getDeviceModelNumber());
                break;
            case "modem_version":
                holder.setItemSummaryText(DeviceInfoUtils.getBasebandVersion());
                break;
            case "os_version":
                holder.setItemSummaryText(Build.VERSION.RELEASE);
                break;
            case "patches_version":
                holder.setItemSummaryText(DeviceInfoUtils.getSecurityPatchVersion());
                break;
            case "rom_version":
                holder.setItemSummaryText(DeviceInfoUtils.getROMVersion());
                break;
            case "sep_version":
                holder.setItemSummaryText(DeviceInfoUtils.getOneUIVersion());
                break;
        }
    }

}
