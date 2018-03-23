package com.fong.play.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fong.play.AppApplication;
import com.fong.play.R;
import com.fong.play.di.component.AppComponent;
import com.fong.play.presenter.BasePresenter;
import com.orhanobut.logger.Logger;
import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by FANGDINGJIE
 * 2018/3/20.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    private View mRootView;
    private Unbinder mUnbinder;
    private AppApplication mAppApplication;

    @Inject
    T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayout(), container, false);
        mUnbinder = ButterKnife.bind(this,mRootView);
        Log.d("BaseFragment","onCreateView");

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAppApplication = (AppApplication) getActivity().getApplication();
        setupActivityComponent(mAppApplication.getAppComponent());
        init();
        Logger.d("onActivityCreated");
     //   Log.d("BaseFragment","onActivityCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }

    protected abstract int setLayout();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected abstract void init();
}
