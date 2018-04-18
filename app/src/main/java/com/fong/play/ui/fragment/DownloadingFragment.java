package com.fong.play.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.fong.play.common.apkparest.AndroidApk;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppManagerComponent;
import com.fong.play.di.module.AppManagerModule;
import com.fong.play.ui.adapter.DownloadingAdapter;

import java.util.List;

import zlc.season.rxdownload2.entity.DownloadRecord;


public class DownloadingFragment extends AppManagerFragment {

    private DownloadingAdapter mAdapter;


    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder()
                .appComponent(appComponent)
                .appManagerModule(new AppManagerModule(this))
                .build().injectDownloading(this);

    }

    @Override
    public void init() {
        super.init();
        mPresenter.getDownloadingApps();
    }


    @Override
    public void showDownloading(List<DownloadRecord> downloadRecords) {
        mAdapter.addData(downloadRecords);
    }

    @Override
    public void showApp(List<AndroidApk> apps) {

    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        mAdapter = new DownloadingAdapter(mPresenter.getRxDownlad());
        return mAdapter;
    }
}
