package com.mesalabs.on.toolbox.activity.tb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mesalabs.cerberus.base.BaseAppBarActivity;
import com.mesalabs.cerberus.ui.callback.OnSingleClickListener;
import com.mesalabs.cerberus.ui.utils.ActionBarUtils;
import com.mesalabs.cerberus.utils.ViewUtils;
import com.mesalabs.on.toolbox.R;
import com.mesalabs.on.toolbox.fragment.home.HomeDrawerFragment;
import com.samsung.android.ui.recyclerview.widget.SeslLinearLayoutManager;
import com.samsung.android.ui.recyclerview.widget.SeslRecyclerView;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

public class HomeActivity extends BaseAppBarActivity {
    private FragmentManager mFragmentManager;
    private HomeDrawerFragment mDrawerFragment;

    private DrawerLayout mDrawerLayout;
    private View mHomeDrawerDim;
    private FrameLayout mHomeDrawerLayout;
    private FrameLayout mHomeMainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        removeViewRoundedCorners();

        setBaseContentView(R.layout.mesa_tb_activity_homeactivity_layout);

        appBar = new ActionBarUtils(this);
        appBar.initAppBar(true);
        appBar.setTitleText(getString(R.string.mesa_ontoolbox), "");
        appBar.setSubtitleText("#poweryourgalaxy");

        appBar.setHomeAsUpButton(R.drawable.sesl_ic_ab_drawer,
                R.string.mesa_tb_drawer_description,
                new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View view) {
                        openDrawer();
                    }
                });

        mFragmentManager = getSupportFragmentManager();

        mHomeDrawerDim = findViewById(R.id.mesa_drawer_dim_homeactivity);
        mDrawerLayout = findViewById(R.id.mesa_drawerlayout_homeactivity);
        mHomeMainContainer = findViewById(R.id.mesa_content_main_homeactivity);
        mHomeDrawerLayout = findViewById(R.id.mesa_drawer_fragment_homeactivity);

        initDrawer();
        replaceFragmentInActivity(R.id.mesa_content_container_homeactivity, new TestFragment());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateDrawerLayout();
    }

    private void initDrawer() {
        mDrawerFragment = HomeDrawerFragment.newInstance();
        replaceFragmentInActivity(R.id.mesa_drawer_fragment_homeactivity, mDrawerFragment);

        mDrawerLayout.setScrimColor(getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerElevation(0.0F);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                int fragmentWidth = mHomeDrawerLayout.getWidth();
                float xAxisPos = mHomeDrawerLayout.getX();
                float measuredWidth;

                if (ViewUtils.isRTLMode(mContext)) {
                    measuredWidth = (float) mHomeMainContainer.getMeasuredWidth();
                    if (measuredWidth - xAxisPos <= measuredWidth) {
                        xAxisPos -= measuredWidth;
                    } else  {
                        xAxisPos = 0.0F;
                    }
                } else {
                    measuredWidth = (float) fragmentWidth + xAxisPos;
                    if (measuredWidth < 0.0F) {
                        xAxisPos = 0.0F;
                    } else {
                        xAxisPos = measuredWidth;
                    }
                }

                mHomeMainContainer.setTranslationX(xAxisPos);
                mHomeDrawerDim.setAlpha(slideOffset);
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
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) mHomeDrawerLayout.getLayoutParams();
        int drawerWidth = getDrawerWidth();
        lp.width = drawerWidth;
        if (isDrawerOpen()) {
            if (ViewUtils.isRTLMode(mContext)) {
                mHomeMainContainer.setTranslationX((float) (-drawerWidth));
            } else {
                mHomeMainContainer.setTranslationX((float) drawerWidth);
            }

            mDrawerLayout.invalidate();
        }
    }


    public void replaceFragmentInActivity(int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.commit();
    }







    public static class TestFragment extends Fragment {
        private Context mContext;
        private View mRootView;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            mContext = context;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.mesa_tb_fragment_home_layout, container, false);
            return mRootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            if (mRootView == null) {
                mRootView = getView();
            }
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            SeslRecyclerView recyclerView = mRootView.findViewById(R.id.test_recycler_view);
            SeslLinearLayoutManager layoutManager = new SeslLinearLayoutManager(getActivity());

            recyclerView.setLayoutManager(layoutManager);
            ArrayList<TestData> testData = TestDataHelper.generateTestData(30);

            SeslRecyclerView.Adapter adapter = new TestDataAdapter(testData, new TestDataAdapter.IItemClickListener() {
                @Override
                public void onClick(TestData testData) { }

                @Override
                public void onLongClick(TestData testData) { }
            });
            recyclerView.setAdapter(adapter);

            recyclerView.seslSetGoToTopEnabled(true);
            recyclerView.seslSetFastScrollerEnabled(true);
        }

    }


















    public static class TestDataAdapter extends SeslRecyclerView.Adapter<TestDataViewHolder>
    {
        public interface IItemClickListener
        {
            public void onClick(TestData testData);
            public void onLongClick(TestData testData);
        }

        private ArrayList<TestData> dataset;

        private IItemClickListener itemClickListener;

        public TestDataAdapter(ArrayList<TestData> dataset, IItemClickListener itemClickListener)
        {
            this.dataset = dataset;
            this.itemClickListener = itemClickListener;
        }

        @Override
        public TestDataViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_item_list_row, viewGroup, false);

            TestDataViewHolder testDataViewHolder = new TestDataViewHolder(view, itemClickListener);

            return testDataViewHolder;
        }

        @Override
        public void onBindViewHolder(TestDataViewHolder testDataViewHolder, int position)
        {
            TestData testData = dataset.get(position);

            testDataViewHolder.bindData(testData);

        }

        @Override
        public int getItemCount()
        {
            return dataset.size();
        }
    }

    public static class TestDataViewHolder extends SeslRecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView      image;
        private TextView       locationText;
        private TextView       titleText;
        private TextView       categoryText;
        private RelativeLayout relativeLayout;

        private TestData testData;

        private View itemView;

        private TestDataAdapter.IItemClickListener clickListener;

        private static HashMap<String, Bitmap> images = new HashMap<String, Bitmap>();

        private static final StringBuilder iconName = new StringBuilder(30);

        private Map<String, String> imageMap = new HashMap<String, String>() {
            {
                put("roadwork", "roadwork");
                put("accident", "accident");
                put("incident", "incident");
            }
        };

        public TestDataViewHolder(View itemView, TestDataAdapter.IItemClickListener clickListener) {
            super(itemView);

            this.itemView = itemView;
            this.clickListener = clickListener;

            images = new HashMap<String, Bitmap>();

            image = (ImageView) itemView.findViewById(R.id.event_image);
            locationText = (TextView) itemView.findViewById(R.id.event_location);
            titleText = (TextView) itemView.findViewById(R.id.event_description);
            categoryText = (TextView) itemView.findViewById(R.id.event_category);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.event_row);

            relativeLayout.setOnClickListener(this);
            relativeLayout.setOnLongClickListener(this);
        }

        public void bindData(TestData testData) {
            this.testData = testData;

            String category = testData.getCategory();

            categoryText.setText(category);

            String severity = testData.getSeverity();

            String imagePrefix = imageMap.get(category);

            iconName.setLength(0);
            iconName.append(imagePrefix);
            iconName.append("_");
            iconName.append(severity.toLowerCase(Locale.ENGLISH));

            Bitmap icon = get(itemView.getContext(), iconName.toString(), TestData.DEFAULT_ICON);

            if (icon != null) {
                image.setImageBitmap(icon);
            }

            locationText.setText(testData.getLocation());

            titleText.setText(testData.getTitle());
        }

        public Bitmap get(Context context, String name, String defaultName) {
            Bitmap target = images.get(name);

            if (target == null) {
                target = ImageHelper.fetchIcon(context, name, defaultName);

                images.put(name, target);
            }

            return target;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(testData);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onLongClick(testData);
            return true;
        }
    }

    public static class TestData implements Serializable {
        public static final String DEFAULT_ICON = "incident_low";

        private String severity;
        private String category;
        private String location;
        private String title;

        public TestData(String category, String location, String title, String severity) {
            this.category = category;
            this.location = location;
            this.title = title;
            this.severity = severity;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getLocation() {
            return location;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ImageHelper {
        public static Bitmap fetchIcon(Context context, String name, String defaultName) {
            Bitmap target = null;

            target = getImage(context, name);

            if (target == null) {
                target = getImage(context, defaultName);
            }

            return target;
        }

        public static Bitmap getImage(Context context, String name) {
            Bitmap target = null;

            try {
                int value = getFieldValue(name, R.drawable.class);
                InputStream is = context.getResources().openRawResource(value);
                target = BitmapFactory.decodeStream(is);
            }
            catch (Throwable e) { }

            return target;
        }

        @SuppressWarnings("rawtypes")
        public static int getFieldValue(String name, Class obj) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
            Field field = obj.getDeclaredField(name);

            int value = field.getInt(obj);

            return value;
        }

        public String buildIconName(String category, String severity) {
            return category + "_" + severity;
        }
    }

    public static class TestDataHelper {

        private static String[] category = {
                        "incident",
                        "accident",
                        "roadwork"
                };

        private static String[] severity = {
                        "low",
                        "medium",
                        "severe"
                };

        private static final String[] roads = {
                        "M1", "M2", "M3", "M4", "M5", "M6",
                        "M8", "M9", "M11", "M18", "M20", "M23",
                        "M25", "M26", "M27", "M32", "M40", "M42",
                        "M45", "M48", "M49", "M50", "M53", "M54",
                        "M55", "M56", "M57", "M58", "M60", "M61",
                        "M62", "M65", "M66", "M67", "M69", "M73",
                        "M74", "M77", "M80", "M90", "M180", "M181",
                        "M271", "M275", "M602", "M606", "M621",
                        "M876", "M898"
                };

        public static ArrayList<TestData> generateTestData(int count)
        {
            ArrayList<TestData> data = new ArrayList<>();

            for (int index = 0; index < count; index++)
            {
                int categoryIndex = (int) (Math.random() * category.length);
                int severityIndex = (int) (Math.random() * severity.length);
                int roadIndex = (int) (Math.random() * roads.length);

                String categoryText = category[categoryIndex];
                String severityText = severity[severityIndex];
                String location = roads[roadIndex];

                String testDesc = buildTestDescription(categoryText, severityText);

                TestData testData = new TestData(categoryText, location, testDesc, severityText);

                data.add(testData);
            }

            return data;
        }

        public static String buildTestDescription(String category, String severity)
        {
            String text = "Lorem ipsum dolor sit amet, arcu mi fermentum nam, pede est integer, pharetra sed a, adipiscing in viverra eu sagittis nec laoreet, ante maiores euismod ultrices diam urna. Commodo faucibus. Rhoncus mi ut felis, ante dolor cras scelerisque a vestibulum vitae, amet sagittis velit lacus ipsum lorem";
            return text;
        }
    }

}
