package com.fong.play.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.fong.play.R;
import com.fong.play.data.bean.FragmentInfo;
import com.fong.play.di.component.AppComponent;
import com.fong.play.ui.adapter.ViewPagerAdapter;
import com.fong.play.ui.fragment.DownloadedFragment;
import com.fong.play.ui.fragment.DownloadingFragment;
import com.fong.play.ui.fragment.InstalledAppFragment;
import com.fong.play.ui.fragment.UpgradeAppFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppManagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.activity_download_manager)
    LinearLayout activityDownloadManager;

    @Override
    protected int setLayout() {
        return R.layout.activity_app_manager;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        initToolbar();
        initTabLayout();
    }



    private void initToolbar() {
        toolbar.setNavigationIcon(
                new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this,R.color.white))
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setTitle("下载管理");
    }


    private void initTabLayout() {
        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),initFragment());
        viewpager.setOffscreenPageLimit(adapter.getCount());
        viewpager.setAdapter(adapter);

        tabs.setupWithViewPager(viewpager);
    }

    private List<FragmentInfo> initFragment() {
        List<FragmentInfo> mFragments = new ArrayList<>();
        mFragments.add(new FragmentInfo("下载",DownloadingFragment.class));
        mFragments.add(new FragmentInfo("已完成",DownloadedFragment.class));
        mFragments.add(new FragmentInfo("升级", UpgradeAppFragment.class));
        mFragments.add(new FragmentInfo("已安装",InstalledAppFragment.class));
        return  mFragments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
