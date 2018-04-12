package com.fong.play.data;

import android.content.Context;

import com.fong.play.presenter.constract.AppManagerContract;

import java.util.List;

import io.reactivex.Observable;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

public class AppManagerModel implements AppManagerContract.IAppManagerModel {
    private RxDownload mRxDownload;
    private Context context;

    public AppManagerModel(RxDownload mRxDownload, Context context) {
        this.mRxDownload = mRxDownload;
        this.context = context;
    }

    @Override
    public RxDownload getRxDownload() {
        return mRxDownload;
    }

    @Override
    public Observable<List<DownloadRecord>> getDownloadRecord() {
        return mRxDownload.getTotalDownloadRecords();
    }
}
