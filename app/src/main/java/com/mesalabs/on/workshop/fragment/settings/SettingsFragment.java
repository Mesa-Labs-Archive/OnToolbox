package com.mesalabs.on.workshop.fragment.settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.activity.aboutpage.AboutActivity;
import com.mesalabs.on.workshop.utils.PreferencesUtils;
import com.samsung.android.ui.preference.SeslPreference;
import com.samsung.android.ui.preference.SeslPreferenceFragmentCompat;

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

public class SettingsFragment extends SeslPreferenceFragmentCompat implements
        SeslPreference.OnPreferenceChangeListener,
        SeslPreference.OnPreferenceClickListener {
    private long mLastClickTime = 0L;

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getListView().seslSetLastOutlineStrokeEnabled(false);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.mesa_tb_prefs_settingsactivity);
        seslSetRoundedCornerType(SESL_ROUNDED_CORNER_TYPE_STROKE);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        SeslPreference aboutActivityPref = findPreference("mesa_aboutactivity_pref");
        if (PreferencesUtils.getIsAppUpdateAvailable()) {
            aboutActivityPref.setWidgetLayoutResource(R.layout.mesa_preference_prefs_badge_layout);
        }
        aboutActivityPref.setOnPreferenceClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onPreferenceChange(SeslPreference preference, Object newValue) {
        return false;
    }

    @Override
    public boolean onPreferenceClick(SeslPreference preference) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 600L) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (preference.getKey()) {
            case "mesa_aboutactivity_pref":
                startActivity(new Intent(getContext(), AboutActivity.class));
                return true;
        }

        return false;
    }

}