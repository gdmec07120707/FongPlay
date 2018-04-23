package com.fong.play.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.fong.play.common.apkparest.AndroidApk;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.di.component.AppComponent;
import com.fong.play.ui.adapter.AppInfoAdapter;

import java.util.List;

public class UpgradeAppFragment extends AppManagerFragment {
    private AppInfoAdapter mAdapter;


    @Override
    public void init() {
        super.init();
        mPresenter.getUpdateApps();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        mAdapter = AppInfoAdapter.builder().updateStatus( true ).rxDownload( mPresenter.getRxDownlad() ).build();
        return mAdapter;
    }


    @Override
    public void showUpdateApps(List<AppInfo> appInfos) {
        mAdapter.addData( appInfos );
    }
}
