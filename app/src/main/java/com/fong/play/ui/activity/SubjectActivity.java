package com.fong.play.ui.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fong.play.R;
import com.fong.play.common.rx.RxBus;
import com.fong.play.data.bean.Subject;
import com.fong.play.di.component.AppComponent;
import com.fong.play.ui.fragment.SubjectDetailFragment;
import com.fong.play.ui.fragment.SubjectFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class SubjectActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;

    private FragmentManager fragmentManager;

    public static final int FRAGMENT_SUBJECT = 0;
    public static final int FRAGMENT_DETAIL = 1;

    private int fragmentIndex = FRAGMENT_SUBJECT;

    private SubjectFragment mSubjectFragment;
    private SubjectDetailFragment mSubjectDetailFragment;

    @Override
    protected int setLayout() {
        return R.layout.template_toolbar_framelayout;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        initToolbar();

        fragmentManager = getSupportFragmentManager();

        showSubjectsFragment();

        showSubjectDetailFragmentRxBus();

    }

    /**
     * 监听切换SubjectDetailFragment
     */
    @SuppressLint("CheckResult")
    private void showSubjectDetailFragmentRxBus() {
        RxBus.getDefault().toObservable( Subject.class )
                .subscribe( new Consumer<Subject>() {
                    @Override
                    public void accept(Subject subject) throws Exception {
                        showSubjectDetailFragment(subject);
                    }
                } );

    }

    /**
     * 显示主题列表页面
     */
    private void showSubjectsFragment() {
        fragmentIndex = FRAGMENT_SUBJECT;
        mToolBar.setTitle( "主题" );
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragment(ft);
        if (mSubjectFragment == null) {
            mSubjectFragment = new SubjectFragment();
            ft.add( R.id.content, mSubjectFragment );
        } else {
            ft.show( mSubjectFragment );
        }
        ft.commit();
    }


    /**
     * 显示主题详情页面
     * @param subject
     */
    private void showSubjectDetailFragment(Subject subject){
        fragmentIndex = FRAGMENT_DETAIL;
        mToolBar.setTitle(subject.getTitle());
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if(mSubjectDetailFragment!=null){
            ft.remove( mSubjectDetailFragment );
        }
        mSubjectDetailFragment = new SubjectDetailFragment(subject);
        ft.add(R.id.content, mSubjectDetailFragment );
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft){
        if(mSubjectFragment!=null){
            ft.hide(mSubjectFragment);
        }
        if(mSubjectDetailFragment!=null){
            ft.hide(mSubjectDetailFragment);
        }

    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        mToolBar.setNavigationIcon(
                new IconicsDrawable( this )
                        .icon( Ionicons.Icon.ion_ios_arrow_back )
                        .sizeDp( 16 )
                        .color( getResources().getColor( R.color.md_white_1000 ) )
        );
        mToolBar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragmentIndex==FRAGMENT_SUBJECT){
                    finish();
                }else{
                    showSubjectsFragment();
                }
            }
        } );
    }


}
