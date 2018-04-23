package com.fong.play.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.fong.play.common.apkparest.AndroidApk;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppManagerComponent;
import com.fong.play.di.module.AppManagerModule;
import com.fong.play.ui.adapter.AndroidApkAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class DownloadedFragment extends  AppManagerFragment{
    private AndroidApkAdapter mAdapter;


    @Override
    public void init() {
        super.init();
        mPresenter.getLocalApks();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        mAdapter = new AndroidApkAdapter(AndroidApkAdapter.FLAG_APK);
        return mAdapter;
    }

    @Override
    public void showApp(List<AndroidApk> apps) {
        Logger.d(apps.size()+"======");
        mAdapter.addData(apps);
    }
}
