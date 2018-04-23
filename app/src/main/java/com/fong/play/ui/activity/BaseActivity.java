package com.fong.play.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.fong.play.AppApplication;
import com.fong.play.di.component.AppComponent;
import com.fong.play.presenter.BasePresenter;
import com.fong.play.ui.BaseView;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by FANGDINGJIE
 * 2018/3/20.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{
    private Unbinder mUnbinder;
    protected AppApplication mAppApplication;

    @Inject
    T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        mUnbinder = ButterKnife.bind(this);
        mAppApplication = (AppApplication) getApplication();
        setupActivityComponent(mAppApplication.getAppComponent());
        init();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    protected abstract int setLayout();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected abstract void init();
}
