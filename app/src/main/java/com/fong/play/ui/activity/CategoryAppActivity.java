package com.fong.play.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.data.bean.Category;
import com.fong.play.di.component.AppComponent;
import com.fong.play.ui.adapter.CategoryAppViewPagerAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAppActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.activity_cateogry_app)
    LinearLayout activityCateogryApp;

    private Category category;

    @Override
    protected int setLayout() {
        return R.layout.activity_cateogry_app;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        getData();
        initTablayout();
    }

   private void getData(){
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra(Constant.CATEGORY);
   }

    private void initTablayout() {
        toolBar.setTitle(category.getName());
        toolBar.setNavigationIcon(
                new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(getResources().getColor(R.color.md_white_1000)
        ));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CategoryAppViewPagerAdapter adapter = new CategoryAppViewPagerAdapter(getSupportFragmentManager(),category.getId());
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }


}
