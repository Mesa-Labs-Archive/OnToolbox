package com.mesalabs.on.toolbox.fragment.home;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mesalabs.cerberus.utils.ViewUtils;
import com.mesalabs.on.toolbox.R;
//import com.samsung.android.ui.recyclerview.widget.SeslLinearLayoutManager;

/*
 * On Toolbox
 *
 * Coded by BlackMesa @2020
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

public class HomeDrawerFragment extends Fragment {
    private Context mContext;
    private View mRootView;
    private ConstraintLayout mParentView;

    private Drawable mDrawerBackground;
    /*private static final String LOG_TAG = "PARTICULAR";
    private IDrawerActionsListener mActionsListener;
    private ParticularsDrawerFragmentBinding mBinding;
    private ParticularsModel mModel;
    private IDrawerNavigator mNavigatorListener;
    private ParticularsViewModel mViewModel;*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mRootView == null) {
            mRootView = getView();
        }

        /*if (getActivity() != null && getContext() != null) {
            if (mViewModel != null) {
                mViewModel.loadDrawerLocations();
                mViewModel.updateDrawerBadgeCount(BadgeCount.getMarketUpdateBadgeCount(getContext()));
            }
        }*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setFontScale(newConfig.orientation);
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
        updateDrawer();
    }

    private void updateDrawer() {
        if (mParentView != null) {
            mParentView.setBackground(ViewUtils.isRTLMode(mContext) ? mContext.getDrawable(R.drawable.mesa_tb_drawer_bg_rtl) : mContext.getDrawable(R.drawable.mesa_tb_drawer_bg));
        }
    }


    public static HomeDrawerFragment newInstance() {
        return new HomeDrawerFragment();
    }









    /*@Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = ParticularsDrawerFragmentBinding.inflate(inflater, container, false);
        mViewModel = ParticularsDrawerActivity.obtainViewModel((ParticularsDrawerActivity) getActivity());
        mModel = ParticularsDrawerActivity.obtainModel(getContext());
        mNavigatorListener = new ParticularsDrawerFragment.DrawerNavigatorListener();
        mActionsListener = new ParticularsDrawerFragment.DrawerActionsListener();
        mBinding.setViewModel(mViewModel);
        mBinding.setListener(mNavigatorListener);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mViewModel = null;
        mBinding = null;
        mNavigatorListener = null;
        mActionsListener = null;
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

        mBinding.particularsDrawerLocationList.setLayoutManager(null);
        DrawerListRecyclerAdapter adapter = (DrawerListRecyclerAdapter) mBinding.particularsDrawerLocationList.getAdapter();
        mBinding.particularsDrawerLocationList.setAdapter(null);
        mBinding.particularsDrawerLocationList.setLayoutManager(new SeslLinearLayoutManager(getContext().getApplicationContext()));
        mBinding.particularsDrawerLocationList.setAdapter(adapter);
    }*/



    /*private void initView() {
        if (AppUtils.isRTL(this.getContext().getApplicationContext())) {
            this.mBinding.particularsDrawerLayout.setBackground(this.getActivity().getDrawable(drawable.drawer_bg_rtl));
        } else {
            this.mBinding.particularsDrawerLayout.setBackground(this.getActivity().getDrawable(drawable.drawer_bg));
        }

        this.mBinding.particularsDrawerLocationList.setLayoutManager(new LinearLayoutManager(this.getContext().getApplicationContext()));
        DrawerListRecyclerAdapter var1 = new DrawerListRecyclerAdapter(this.mViewModel, this.mModel, new ArrayList(), this.mNavigatorListener, this.mActionsListener);
        this.mBinding.particularsDrawerLocationList.setAdapter(var1);
        RecyclerView var3 = this.mBinding.particularsDrawerLocationList;
        byte var2 = 0;
        var3.seslSetOutlineStrokeEnabled(false, false);
        ParticularsUtil.setHoverPopupType(this.mBinding.particularsDrawerSetting, true);
        if (this.mViewModel != null) {
            FrameLayout var4 = this.mBinding.particularsDrawerLocationSmartTip;
            if (this.mViewModel.checkDrawerInformationTip()) {
                var2 = 8;
            }

            var4.setVisibility(var2);
        }

        this.setFontScale(ParticularsUtil.getOrientation(this.getActivity()));
        this.mBinding.particularsDrawerLocationSmartTipCloseBtn.setOnClickListener(new mlpFnExcrsDyfaTndaL7pnrNAZE(this));
    }

    */

    /*private void setFontScale(int var1) {
        if (this.getContext() != null) {
            ParticularsDrawerFragmentBinding var2 = this.mBinding;
            if (var2 != null && var2.particularsDrawerLocationSmartTip != null && this.mBinding.particularsDrawerLocationSmartTipMsg != null && this.mBinding.particularsDrawerLocationSmartTip.getVisibility() == 0) {
                SizeLimitedTextView var6 = this.mBinding.particularsDrawerLocationSmartTipMsg;
                StringBuilder var3 = new StringBuilder();
                var3.append(this.getString(string.drawer_tips_message_1));
                var3.append(" ");
                var3.append(this.getString(string.drawer_tips_message_2));
                var6.setText(var3.toString());
                Context var7 = this.getContext();
                float var4 = (float)this.getResources().getDimensionPixelSize(dimen.particulars_drawer_location_smart_tip_msg_text_size);
                float var5;
                if (var1 == 2) {
                    var5 = 1.1F;
                } else {
                    var5 = 1.3F;
                }

                ParticularsUtil.setTextViewSize(var7, var4, var5, this.mBinding.particularsDrawerLocationSmartTipMsg);
                this.mBinding.particularsDrawerLocationSmartTipMsg.invalidate();
            }
        }

    }


    private class DrawerActionsListener implements IDrawerActionsListener {
        private DrawerActionsListener() {
        }

        public void onClickLocationItem(DrawerLocationEntity var1) {
            StringBuilder var2 = new StringBuilder();
            var2.append("onLocationClick] ");
            var2.append(var1.getKey());
            SLog.d("PARTICULAR", var2.toString());
            ParticularsDrawerFragment.this.mViewModel.updateCurrent(var1.getKey());
        }

        public void onStartFindCurrentLocation() {
            SLog.d("PARTICULAR", "onStartFindCurrentLocation] ");
        }
    }

    private class DrawerNavigatorListener implements IDrawerNavigator {
        private DrawerNavigatorListener() {
        }

        public void onStartContactUs() {
            SLog.d("PARTICULAR", "onStartContactUs] ");
            ParticularsDrawerFragment.this.mViewModel.drawerClose(true);
            ParticularsDrawerFragment.this.mViewModel.startContactUsCommand().call();
        }

        public void onStartInfo() {
            SLog.d("PARTICULAR", "onStartInfo] ");
            ParticularsDrawerFragment.this.mViewModel.drawerClose(true);
            ParticularsDrawerFragment.this.mViewModel.getStartHelpFavoriteLocationCommand().call();
        }

        public void onStartManageLocations() {
            SLog.d("PARTICULAR", "startManageLocations] ");
            ParticularsDrawerFragment.this.mViewModel.drawerClose(true);
            ParticularsDrawerFragment.this.mViewModel.startLocationsCommand().call();
        }

        public void onStartReportWrongCityName() {
            SLog.d("PARTICULAR", "onStartReportWrongCityName] ");
            ParticularsDrawerFragment.this.mViewModel.drawerClose(true);
            ParticularsDrawerFragment.this.mViewModel.callStartReportWrongCityCommand();
        }

        public void onStartSearch() {
            SLog.d("PARTICULAR", "onStartSearch] ");
            ParticularsDrawerFragment.this.mViewModel.startSearch(false);
        }

        public void onStartSettings() {
            SLog.d("PARTICULAR", "startSettings] ");
            ParticularsDrawerFragment.this.mViewModel.drawerClose(true);
            ParticularsDrawerFragment.this.mViewModel.startSettingCommand().call();
        }
    }*/
}
