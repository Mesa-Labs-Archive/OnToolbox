package com.mesalabs.on.workshop.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.ui.deviceinfo.DeviceInfoAdapter;
import com.mesalabs.on.workshop.ui.deviceinfo.DeviceInfoItemDecoration;
import com.mesalabs.on.workshop.ui.deviceinfo.DeviceInfoViewModel;
import com.samsung.android.ui.recyclerview.widget.SeslLinearLayoutManager;
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

public class DeviceInfoFragment extends Fragment {
    private Context mContext;
    private SeslRecyclerView mListView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView = (SeslRecyclerView) inflater.inflate(R.layout.mesa_tb_baselayout_listfragment, container, false);
        return mListView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mListView != null) {
            initListView();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mListView == null) {
            mListView = (SeslRecyclerView) getView();
        }
    }

    private void initListView() {
        List<DeviceInfoViewModel> itemsList = getItemsList();

        SeslRecyclerView.Adapter adapter = new DeviceInfoAdapter(this, itemsList);
        DeviceInfoItemDecoration decoration = new DeviceInfoItemDecoration(this);
        TypedValue divider = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.listDivider, divider, true);

        mListView.setLayoutManager(new SeslLinearLayoutManager(mContext));
        mListView.setAdapter(adapter);

        mListView.addItemDecoration(decoration);
        decoration.setDivider(mContext.getDrawable(divider.resourceId));
        mListView.setItemAnimator(null);
        mListView.seslSetFillBottomEnabled(true);
        mListView.seslSetLastOutlineStrokeEnabled(false);
    }

    protected List<DeviceInfoViewModel> getItemsList() {
        List<DeviceInfoViewModel> modelList = new ArrayList<>();

        modelList.add(new DeviceInfoViewModel("rom_version",
                getResources().getString(R.string.mesa_fwinfo_on_rom_version_title)));
        modelList.add(new DeviceInfoViewModel("enso_version",
                getResources().getString(R.string.mesa_fwinfo_on_enso_version_title)));

        modelList.add(new DeviceInfoViewModel());

        modelList.add(new DeviceInfoViewModel("sep_version",
                getResources().getString(R.string.mesa_fwinfo_oneui_version_title)));
        modelList.add(new DeviceInfoViewModel("os_version",
                getResources().getString(R.string.mesa_fwinfo_android_version_title)));
        modelList.add(new DeviceInfoViewModel("bootloader_version",
                getResources().getString(R.string.mesa_fwinfo_bootloader_title)));
        modelList.add(new DeviceInfoViewModel("modem_version",
                getResources().getString(R.string.mesa_fwinfo_modem_title)));
        modelList.add(new DeviceInfoViewModel("kernel_version",
                getResources().getString(R.string.mesa_fwinfo_kernel_version_title)));
        modelList.add(new DeviceInfoViewModel("build_version",
                getResources().getString(R.string.mesa_fwinfo_build_number_title)));
        modelList.add(new DeviceInfoViewModel("patches_version",
                getResources().getString(R.string.mesa_fwinfo_patches_title)));

        modelList.add(new DeviceInfoViewModel());

        modelList.add(new DeviceInfoViewModel("device_name",
                getResources().getString(R.string.mesa_fwinfo_devicename_title)));
        modelList.add(new DeviceInfoViewModel("model_number",
                getResources().getString(R.string.mesa_fwinfo_modelnum_title)));
        modelList.add(new DeviceInfoViewModel("device_soc",
                getResources().getString(R.string.mesa_fwinfo_soc_title)));
        modelList.add(new DeviceInfoViewModel("manufacturing_date",
                getResources().getString(R.string.mesa_fwinfo_manufact_title)));

        modelList.add(new DeviceInfoViewModel());


        return modelList;
    }

    public SeslRecyclerView getListView() {
        return mListView;
    }
}
