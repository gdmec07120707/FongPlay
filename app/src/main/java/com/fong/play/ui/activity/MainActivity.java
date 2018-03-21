package com.fong.play.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fong.play.R;
import com.fong.play.di.component.AppComponent;
import com.fong.play.ui.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nv_main_menu)
    NavigationView nvMainMenu;
    @BindView(R.id.dl_mian)
    DrawerLayout dlMian;
    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.tl_mian)
    TabLayout tlMian;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        nvMainMenu.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "header", Toast.LENGTH_SHORT).show();
            }
        });

        nvMainMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.favorite:
                        Toast.makeText(MainActivity.this, "favorite", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        //初始化菜单栏
        mainToolbar.inflateMenu(R.menu.menu_main_toolbar);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, dlMian, mainToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        dlMian.addDrawerListener(mActionBarDrawerToggle);


        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpMain.setAdapter(mViewPagerAdapter);
        tlMian.setupWithViewPager(vpMain);
    }
}
