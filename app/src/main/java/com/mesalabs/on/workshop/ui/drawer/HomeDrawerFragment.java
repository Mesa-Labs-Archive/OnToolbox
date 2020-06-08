package com.mesalabs.on.workshop.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mesalabs.cerberus.ui.callback.OnSingleClickListener;
import com.mesalabs.cerberus.utils.Utils;
import com.mesalabs.cerberus.utils.ViewUtils;
import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.activity.settings.SettingsActivity;
import com.mesalabs.on.workshop.activity.tb.HomeActivity;
import com.samsung.android.ui.recyclerview.widget.SeslLinearLayoutManager;
import com.samsung.android.ui.recyclerview.widget.SeslRecyclerView;

import java.util.ArrayList;
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

public class HomeDrawerFragment extends Fragment {
    private boolean mUpdateAvailable = false;

    private TypedArray mDrawerIcons;
    private String [] mDrawerTitles;
    private String [] mDrawerTags;
    private int [] mDrawerNeedsInternet;

    private HomeActivity mActivity;
    private Context mContext;
    private View mRootView;
    private ConstraintLayout mParentView;
    private ImageButton mSettingsIcon;
    private TextView mSettingsIconBadge;
    private SeslRecyclerView mListView;

    public HomeDrawerFragment() { }

    public HomeDrawerFragment(HomeActivity activity) {
        mActivity = activity;
    }

    public static HomeDrawerFragment newInstance(HomeActivity activity) {
        return new HomeDrawerFragment(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mRootView == null) {
            mRootView = getView();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.mesa_tb_layout_home_drawer, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mParentView = mRootView.findViewById(R.id.mesa_parent_home_drawer);
        mSettingsIcon = mRootView.findViewById(R.id.mesa_settings_home_drawer);
        mSettingsIconBadge = mRootView.findViewById(R.id.mesa_settings_badge_home_drawer);
        mListView = mRootView.findViewById(R.id.mesa_recyclerview_home_drawer);

        if (mParentView != null) {
            mParentView.setBackground(ViewUtils.isRTLMode(mContext) ? mContext.getDrawable(R.drawable.mesa_tb_drawer_bg_rtl) : mContext.getDrawable(R.drawable.mesa_tb_drawer_bg));
        }
        if (mSettingsIcon != null) {
            Utils.genericInvokeMethod(View.class, mSettingsIcon, "semSetHoverPopupType", 1 /* SemHoverPopupWindow.TYPE_TOOLTIP */);
            mSettingsIcon.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    startActivity(new Intent(mContext, SettingsActivity.class));
                }
            });
        }
        if (mSettingsIconBadge != null) {
            mSettingsIconBadge.setVisibility(mUpdateAvailable ? View.VISIBLE : View.GONE);
            mSettingsIconBadge.setText(mUpdateAvailable ? "N" : "");
        }
        if (mListView != null) {
            initListView();
        }
    }

    private void initListView() {
        mDrawerIcons = getResources().obtainTypedArray(R.array.mesa_tb_icon_drawer);
        mDrawerTitles = getResources().getStringArray(R.array.mesa_tb_title_drawer);
        mDrawerTags = getResources().getStringArray(R.array.mesa_tb_tag_drawer);
        mDrawerNeedsInternet = getResources().getIntArray(R.array.mesa_tb_internet_drawer);
        SeslRecyclerView.Adapter adapter = new HomeDrawerListAdapter(mActivity, getHomeDrawerList());
        mListView.setLayoutManager(new SeslLinearLayoutManager(mContext));
        mListView.setAdapter(adapter);
        mListView.seslSetOutlineStrokeEnabled(false, false);
    }

    public void setUpdateAvailable(boolean available) {
        mUpdateAvailable = available;

        if (mSettingsIconBadge != null) {
            mSettingsIconBadge.setVisibility(mUpdateAvailable ? View.VISIBLE : View.GONE);
            mSettingsIconBadge.setText(mUpdateAvailable ? "N" : "");
        }
    }

    private List<HomeDrawerListViewModel> getHomeDrawerList() {
        List<HomeDrawerListViewModel> modelList = new ArrayList<>();

        for (int i = 0; i < mDrawerTags.length; i++) {
            modelList.add(new HomeDrawerListViewModel(!mDrawerTitles[i].isEmpty(),
                    getResources().getDrawable(mDrawerIcons.getResourceId(i, R.drawable.nul), null),
                    mDrawerTitles[i],
                    mDrawerNeedsInternet[i]));
        }

        return modelList;
    }

}
