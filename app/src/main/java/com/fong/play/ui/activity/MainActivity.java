package com.fong.play.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.font.Cniao5Font;
import com.fong.play.common.imageloader.GlideCircleTransform;
import com.fong.play.common.imageloader.ImageLoader;
import com.fong.play.common.rx.RxBus;
import com.fong.play.common.utils.ACache;
import com.fong.play.data.bean.FragmentInfo;
import com.fong.play.data.bean.User;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerMainComponent;
import com.fong.play.di.module.MainModule;
import com.fong.play.presenter.MainPresenter;
import com.fong.play.presenter.constract.MainContract;
import com.fong.play.ui.adapter.ViewPagerAdapter;
import com.fong.play.ui.fragment.CategoryFragment;
import com.fong.play.ui.fragment.GamesFragment;
import com.fong.play.ui.fragment.RecommendFragment;
import com.fong.play.ui.fragment.TopListFragment;
import com.fong.play.ui.widget.BadgeActionProvider;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.orhanobut.logger.Logger;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.MainView{

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

    private View headerView;
    private ImageView mUserHeadView;
    private TextView mTextUserName;

    private BadgeActionProvider badgeActionProvider;

    private List<FragmentInfo> mFragments = new ArrayList<>( 4 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {


        RxBus.getDefault().toObservable( User.class ).subscribe( new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                initUserHeadView( user );
            }
        } );

        mPresenter.requestPermisson();

        mPresenter.getAppUpdateInfo();


    }

    private void initTabLayout() {
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager(), initFragments() );
        vpMain.setAdapter( mViewPagerAdapter );
        tlMian.setupWithViewPager( vpMain );
    }

    private void initToolbar() {
        mainToolbar.inflateMenu( R.menu.menu_main_toolbar );
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle( this, dlMian, mainToolbar, R.string.open, R.string.close );
        mActionBarDrawerToggle.syncState();
        dlMian.addDrawerListener( mActionBarDrawerToggle );

        MenuItem downloadMenuItem = mainToolbar.getMenu().findItem( R.id.action_download );
        MenuItem mSearchItem = mainToolbar.getMenu().findItem( R.id.action_search);
        badgeActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider( downloadMenuItem );
        badgeActionProvider.setIcon( DrawableCompat.wrap(
                new IconicsDrawable( this, Cniao5Font.Icon.cniao_download )
                        .color( ContextCompat.getColor( this, R.color.white)))
        );
        // badgeActionProvider.setText( 11+"" );
        badgeActionProvider.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAPPManagerActivity(badgeActionProvider.getBadgeNum()>0?2:0);
            }
        } );

        mSearchItem.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.action_search){
                    startActivity(new Intent(MainActivity.this,SearchActivity.class));
                }
                return false;
            }
        } );
    }

    private void initDrawerLayout() {
        headerView = nvMainMenu.getHeaderView( 0 );
        mUserHeadView = (ImageView) headerView.findViewById( R.id.img_avatar );
        mUserHeadView.setImageDrawable( new IconicsDrawable( this, Cniao5Font.Icon.cniao_head ).colorRes( R.color.white ) );
        mTextUserName = (TextView) headerView.findViewById( R.id.txt_username );

        nvMainMenu.getMenu().findItem( R.id.menu_app_update ).setIcon( new IconicsDrawable( this, Ionicons.Icon.ion_ios_loop ) );
        nvMainMenu.getMenu().findItem( R.id.menu_download_manager ).setIcon( new IconicsDrawable( this, Cniao5Font.Icon.cniao_download ) );
        nvMainMenu.getMenu().findItem( R.id.menu_app_uninstall ).setIcon( new IconicsDrawable( this, Ionicons.Icon.ion_ios_trash_outline ) );
        nvMainMenu.getMenu().findItem( R.id.menu_setting ).setIcon( new IconicsDrawable( this, Ionicons.Icon.ion_ios_gear_outline ) );

        nvMainMenu.getMenu().findItem( R.id.menu_logout ).setIcon( new IconicsDrawable( this, Cniao5Font.Icon.cniao_shutdown ) );

        //菜单点击时间
        nvMainMenu.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_logout:
                        logout();
                        break;
                    case R.id.menu_download_manager:
                        startActivity( new Intent( MainActivity.this, AppManagerActivity.class ) );
                        break;
                    case R.id.menu_setting:
                        startActivity( new Intent( MainActivity.this, SettingActivity.class ) );
                        break;
                }
                return false;
            }
        } );

        headerView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object objUser = ACache.get( MainActivity.this ).getAsObject( Constant.USER );
                if (objUser == null) {
                    startActivity( new Intent( MainActivity.this, LoginActivity.class ) );
                }
            }
        } );

    }

    private List<FragmentInfo> initFragments() {
        mFragments.add( new FragmentInfo( "推荐", RecommendFragment.class ) );
        mFragments.add( new FragmentInfo( "排行", TopListFragment.class ) );
        mFragments.add( new FragmentInfo( "游戏", GamesFragment.class ) );
        mFragments.add( new FragmentInfo( "分类", CategoryFragment.class ) );
        return mFragments;
    }

    /**
     * 退出登陆
     */
    private void logout() {
        ACache aCache = ACache.get( this );

        aCache.put( Constant.TOKEN, "" );
        aCache.put( Constant.USER, "" );

        mUserHeadView.setImageDrawable( new IconicsDrawable( this, Cniao5Font.Icon.cniao_head ).colorRes( R.color.white ) );
        mTextUserName.setText( "未登录" );

        Toast.makeText( MainActivity.this, "您已退出登录", Toast.LENGTH_LONG ).show();
    }

    /**
     * 判断登陆状态
     */
    private void initUser() {
        Object objUser = ACache.get( this ).getAsObject( Constant.USER );
        if (objUser != null) {
            User user = (User) objUser;
            initUserHeadView( user );
        }
    }

    private void initUserHeadView(User user) {
        Logger.d( "头像地址：" + user.getLogo_url() );
        Glide.with( this ).load( "http:" + user.getLogo_url() ).transform( new GlideCircleTransform( this ) ).into( mUserHeadView );
        mTextUserName.setText( user.getUsername() );
    }


    private void toAPPManagerActivity(int position){
        Intent intent = new Intent( MainActivity.this,AppManagerActivity.class );
        intent.putExtra( Constant.POSITION,position );
        startActivity( new Intent( intent ) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void requestPermissonSuccess() {
        initDrawerLayout();
        initUser();
        //初始化菜单栏
        initToolbar();
        //初始化tablayout
        initTabLayout();
    }

    @Override
    public void requestPermissonFail() {
        Toast.makeText(MainActivity.this,"授权失败....",Toast.LENGTH_LONG).show();
    }

    @Override
    public void changneAppNeedUpdateCount(int count) {
        if(count>0){
            badgeActionProvider.setText(count+"");
        } else{
            badgeActionProvider.hideBadge();
        }
    }
}
