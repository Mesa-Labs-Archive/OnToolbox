package com.mesalabs.on.workshop.base;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import androidx.fragment.app.Fragment;

import com.mesalabs.cerberus.utils.CerberusException;
import com.mesalabs.on.workshop.R;
import com.mesalabs.on.workshop.ui.appslist.GenericAppsListAdapter;
import com.mesalabs.on.workshop.ui.appslist.GenericAppsListItemDecoration;
import com.mesalabs.on.workshop.ui.appslist.GenericAppsListViewModel;
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

public class BaseAppsListFragment extends Fragment {
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
        List<GenericAppsListViewModel> itemsList = getItemsList();
        itemsList.add(new GenericAppsListViewModel());

        SeslRecyclerView.Adapter adapter = new GenericAppsListAdapter(mContext, itemsList);
        GenericAppsListItemDecoration decoration = new GenericAppsListItemDecoration(this);
        TypedValue divider = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.listDivider, divider, true);

        mListView.setLayoutManager(new SeslLinearLayoutManager(mContext));
        mListView.setAdapter(adapter);

        mListView.addItemDecoration(decoration);
        decoration.setDivider(mContext.getDrawable(divider.resourceId));
        mListView.setItemAnimator(null);
        mListView.seslSetFillBottomEnabled(true);
        mListView.seslSetGoToTopEnabled(true);
        mListView.seslSetLastOutlineStrokeEnabled(false);
    }

    protected List<GenericAppsListViewModel> getItemsList() {
        throw new CerberusException("Item list hasn't been added to the class!");
    }

    public SeslRecyclerView getListView() {
        return mListView;
    }
}
