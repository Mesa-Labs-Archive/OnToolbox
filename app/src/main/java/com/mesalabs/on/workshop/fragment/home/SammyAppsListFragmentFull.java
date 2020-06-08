package com.mesalabs.on.workshop.fragment.home;

import java.util.ArrayList;
import java.util.List;

import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.base.BaseAppsListFragment;
import com.mesalabs.on.workshop.ui.appslist.GenericAppsListViewModel;

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

public class SammyAppsListFragmentFull extends BaseAppsListFragment {
    @Override
    protected List<GenericAppsListViewModel> getItemsList() {
        List<GenericAppsListViewModel> modelList = new ArrayList<>();

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.camico, null),
                new String[] {"Samsung Camera","10000","com.sec.android.camera", "Just stock cam"}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.sfico, null),
                new String[] {"Samsung Secure Folder","10000","com.sec.android.sf", ""}));

        modelList.add(new GenericAppsListViewModel());

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.camico, null),
                new String[] {"Samsung Camera","10000","com.sec.android.camera", "Just stock cam"}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.sfico, null),
                new String[] {"Samsung Secure Folder","10000","com.sec.android.sf", ""}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.camico, null),
                new String[] {"Samsung Camera","10000","com.sec.android.camera", "Just stock cam"}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.sfico, null),
                new String[] {"Samsung Secure Folder","10000","com.sec.android.sf", ""}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.camico, null),
                new String[] {"Samsung Camera","10000","com.sec.android.camera", "Just stock cam"}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.sfico, null),
                new String[] {"Samsung Secure Folder","10000","com.sec.android.sf", ""}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.camico, null),
                new String[] {"Samsung Camera","10000","com.sec.android.camera", "Just stock cam"}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.sfico, null),
                new String[] {"Samsung Secure Folder","10000","com.sec.android.sf", ""}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.camico, null),
                new String[] {"Samsung Camera","10000","com.sec.android.camera", "Just stock cam"}));

        modelList.add(new GenericAppsListViewModel(false,
                getResources().getDrawable(R.drawable.sfico, null),
                new String[] {"Samsung Secure Folder","10000","com.sec.android.sf", ""}));


        return modelList;
    }
}
