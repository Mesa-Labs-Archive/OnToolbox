package com.mesalabs.on.workshop.activity.tb;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mesalabs.cerberus.base.BaseAppBarActivity;
import com.mesalabs.cerberus.ui.app.ProgressDialog;
import com.mesalabs.cerberus.ui.callback.OnSingleClickListener;
import com.mesalabs.cerberus.ui.utils.ActionBarUtils;
import com.mesalabs.cerberus.ui.widget.RoundFrameLayout;
import com.mesalabs.cerberus.update.utils.AppUpdateUtils;
import com.mesalabs.cerberus.utils.CerberusException;
import com.mesalabs.cerberus.utils.ViewUtils;
import com.mesalabs.on.workshop.OnWorkshopApp;
import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.ui.drawer.HomeDrawerFragment;
import com.mesalabs.on.workshop.ui.widget.ToolboxSwipeRefreshLayout;
import com.mesalabs.on.workshop.utils.PreferencesUtils;
import com.samsung.android.ui.swiperefreshlayout.widget.SeslSwipeRefreshLayout;

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

public class HomeActivity extends BaseAppBarActivity implements SeslSwipeRefreshLayout.OnRefreshListener {
    private String mAppName;

    private String [] mDrawerTitles;
    private String [] mDrawerFragments;
    private String [] mDrawerTags;

    private AppUpdateUtils mAppUpdate;
    private AppUpdateUtils.StubListener mStubListener = new AppUpdateUtils.StubListener() {
        public void onUpdateCheckCompleted(int status) {
            boolean available = status == AppUpdateUtils.STATE_NEW_VERSION_AVAILABLE;
            PreferencesUtils.setIsAppUpdateAvailable(available);
            mDrawerFragment.setUpdateAvailable(available);
        }
    };

    private AlphaAnimation mFadeInAnim;
    private AlphaAnimation mFadeOutAnim;

    private FragmentManager mFragmentManager;
    private HomeDrawerFragment mDrawerFragment;

    private DrawerLayout mDrawerLayout;
    private RoundFrameLayout mHomeMainParent;
    private ToolboxSwipeRefreshLayout mSwipeRefreshLayout;
    private FrameLayout mHomeFragmentContainer;
    private View mHomeDrawerDim;
    private FrameLayout mHomeDrawerContainer;
    private View mHomeDrawerDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAppName = getString(R.string.mesa_onworkshop);

        mDrawerTitles = getResources().getStringArray(R.array.mesa_tb_title_drawer);
        mDrawerFragments = getResources().getStringArray(R.array.mesa_tb_fragment_drawer);
        mDrawerTags = getResources().getStringArray(R.array.mesa_tb_tag_drawer);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fragment = extras.getString("mesa_tb_category", "");
            for (int i = 0; i < mDrawerTags.length; i++)  {
                if (fragment.equals(mDrawerTags[i]))
                    PreferencesUtils.setLatestFragment(i);
            }
        }

        //  init AppUpdateUtils
        mAppUpdate = new AppUpdateUtils(this, OnWorkshopApp.getAppPackageName(), mStubListener);
        mAppUpdate.checkUpdates();

        // init UX
        removeViewRoundedCorners();

        setBaseContentView(R.layout.mesa_tb_activity_homeactivity_layout);

        appBar = new ActionBarUtils(this);
        appBar.initAppBar(true);
        appBar.setHomeAsUpButton(R.drawable.sesl_ic_ab_drawer,
                R.string.mesa_tb_drawer_description,
                new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View view) {
                        openDrawer();
                    }
                });

        initAnimationFields();
        initViews();
        initDrawer();

        //onRefresh();
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewUtils.updateListBothSideMargin(this, mHomeFragmentContainer);
        updateDrawerLayout();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        appBar.setTitleText(mAppName, "");
        ProgressDialog.show(mContext, "", "", true, false);
    }


    private void initAnimationFields() {
        mFadeInAnim = new AlphaAnimation(0.0f, 1.0f);
        mFadeInAnim.setDuration(250);
        mFadeOutAnim = new AlphaAnimation(1.0f, 0.0f);
        mFadeOutAnim.setDuration(250);
    }

    private void initViews() {
        mFragmentManager = getSupportFragmentManager();

        mHomeDrawerDim = findViewById(R.id.mesa_drawer_dim_homeactivity);
        mDrawerLayout = findViewById(R.id.mesa_drawerlayout_homeactivity);
        mHomeMainParent = findViewById(R.id.mesa_content_main_homeactivity);
        mSwipeRefreshLayout = findViewById(R.id.mesa_drawer_swiperefresh_homeactivity);
        mHomeFragmentContainer = findViewById(R.id.mesa_content_container_homeactivity);
        mHomeDrawerContainer = findViewById(R.id.mesa_drawer_fragment_homeactivity);
        mHomeDrawerDivider = ((ViewStub) findViewById(R.id.mesa_home_divider_homeactivity)).inflate();

        int offset = getResources().getDimensionPixelSize(R.dimen.mesa_tb_home_swipe_refresh_offset);
        mSwipeRefreshLayout.setProgressViewOffset(true, offset, offset + 1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.mesa_tb_primary_color_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        ViewUtils.updateListBothSideMargin(this, mHomeFragmentContainer);
    }

    private void initDrawer() {
        mDrawerFragment = HomeDrawerFragment.newInstance(this);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.mesa_drawer_fragment_homeactivity, mDrawerFragment);
        transaction.commit();

        mDrawerLayout.setScrimColor(getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerElevation(0.0f);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                int fragmentWidth = mHomeDrawerContainer.getWidth();
                float xAxisPos = mHomeDrawerContainer.getX();
                float measuredWidth;

                if (ViewUtils.isRTLMode(mContext)) {
                    measuredWidth = (float) mHomeMainParent.getMeasuredWidth();
                    if (measuredWidth - xAxisPos <= measuredWidth) {
                        xAxisPos -= measuredWidth;
                    } else  {
                        xAxisPos = 0.0f;
                    }
                } else {
                    measuredWidth = (float) fragmentWidth + xAxisPos;
                    if (measuredWidth < 0.0f) {
                        xAxisPos = 0.0f;
                    } else {
                        xAxisPos = measuredWidth;
                    }
                }

                mHomeMainParent.setTranslationX(xAxisPos);
                mHomeDrawerDim.setAlpha(slideOffset);
                mHomeDrawerDivider.setAlpha(slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) { }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) { }

            @Override
            public void onDrawerStateChanged(int newState) { }
        });

        updateDrawerLayout();
    }

    private int getDrawerWidth() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        int displayWidth = p.x;
        float density = getResources().getDisplayMetrics().density;
        float dpi = (float) displayWidth / density;

        double widthRate;
        if (dpi >= 1920.0F) {
            widthRate = 0.22D;
        } else if (dpi >= 960.0F && dpi < 1920.0F) {
            widthRate = 0.2734D;
        } else if (dpi >= 600.0F && dpi < 960.0F) {
            widthRate = 0.46D;
        } else if (dpi >= 480.0F && dpi < 600.0F) {
            widthRate = 0.5983D;
        } else {
            widthRate = 0.844D;
        }

        return (int) ((double) displayWidth * widthRate);
    }

    private boolean isDrawerOpen() {
        if (mDrawerLayout != null) {
            return mDrawerLayout.isDrawerOpen(GravityCompat.START);
        } else {
            return false;
        }
    }

    private void openDrawer() {
        if (!isDrawerOpen()) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private boolean closeDrawer() {
        if (isDrawerOpen()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }

    private void updateDrawerLayout() {
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) mHomeDrawerContainer.getLayoutParams();
        int drawerWidth = getDrawerWidth();
        lp.width = drawerWidth;
        if (isDrawerOpen()) {
            if (ViewUtils.isRTLMode(mContext)) {
                mHomeMainParent.setTranslationX((float) (-drawerWidth));
            } else {
                mHomeMainParent.setTranslationX((float) drawerWidth);
            }

            mHomeDrawerDim.setAlpha(1.0f);
            mHomeDrawerDivider.setAlpha(1.0f);

            mDrawerLayout.invalidate();
        } else {
            mHomeMainParent.setTranslationX(0.0f);
            mHomeDrawerDim.setAlpha(0.0f);
            mHomeDrawerDivider.setAlpha(0.0f);
        }
    }


    public void replaceFragmentInActivity(int index) {
        replaceFragmentInActivity(index, false);
    }

    public void replaceFragmentInActivity(int index, boolean force) {
        if (force || PreferencesUtils.getLatestFragment() != index)  {
            Fragment fragment;
            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            appBar.setTitleText(mAppName, mDrawerTitles[index]);
            appBar.setSubtitleText(mDrawerTitles[index]);
            try {
                fragment = (Fragment) Class.forName(mDrawerFragments[index]).newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                throw new CerberusException(e.toString());
            }
            transaction.replace(R.id.mesa_content_container_homeactivity, fragment);
            transaction.commit();
            mFragmentManager.executePendingTransactions();
        }

        closeDrawer();
    }

}
