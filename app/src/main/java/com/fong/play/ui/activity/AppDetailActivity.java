package com.fong.play.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.imageloader.ImageLoader;
import com.fong.play.common.utils.DensityUtil;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.di.component.AppComponent;
import com.fong.play.presenter.AppDetailPresenter;
import com.fong.play.ui.fragment.AppDetailFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppDetailActivity extends BaseActivity<AppDetailPresenter> {


    @BindView(R.id.view_temp)
    View viewTemp;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.view_coordinator)
    CoordinatorLayout viewCoordinator;
    @BindView(R.id.view_content)
    FrameLayout viewContent;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.detail_nested_scrollview)
    NestedScrollView detailNestedScrollview;

    private AppInfo mAppInfo;

    @Override
    protected int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    protected void init() {
        mAppInfo = (AppInfo) getIntent().getSerializableExtra(Constant.APPINFO);

        ImageLoader.load(Constant.BASE_IMG_URL + mAppInfo.getIcon(), imgIcon);
        mTxtName.setText(mAppInfo.getDisplayName());


        toolbar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView();

        detailNestedScrollview.scrollTo(0,0);

        // initFragment();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        View view = mAppApplication.getCacheView();
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];

        ViewGroup.MarginLayoutParams marginLayoutParams =
                new ViewGroup.MarginLayoutParams(viewTemp.getLayoutParams());
        marginLayoutParams.topMargin = top - DensityUtil.getStatusBarH(this);
        marginLayoutParams.leftMargin = left;
        marginLayoutParams.width = view.getWidth();
        marginLayoutParams.height = view.getHeight();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginLayoutParams);
        viewTemp.setLayoutParams(params);

        Bitmap mBitmap = getViewImageCache(view);
        viewTemp.setBackgroundDrawable(new BitmapDrawable(mBitmap));
        open();
    }

    /**
     * 获取缓存图片
     *
     * @param view
     * @return
     */
    private Bitmap getViewImageCache(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();
        if (bitmap == null) {
            return null;
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 上下展开动画
     */
    private void open() {
        ObjectAnimator animator
                = ObjectAnimator.ofFloat(viewTemp, "scaleY", 1, DensityUtil.getScreenH(this));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

                viewTemp.setBackgroundColor(ContextCompat.getColor(AppDetailActivity.this, R.color.white));

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewTemp.setVisibility(View.GONE);
                viewCoordinator.setVisibility(View.VISIBLE);
                initFragment();
            }


        });
        animator.setStartDelay(500);
        animator.setDuration(1000);
        animator.start();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.view_content, new AppDetailFragment(mAppInfo.getId()));
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
