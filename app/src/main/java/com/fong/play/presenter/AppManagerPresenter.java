package com.fong.play.presenter;

import com.fong.play.common.apkparest.AndroidApk;
import com.fong.play.common.rx.RxSchedulers;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.presenter.constract.AppManagerContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

public class AppManagerPresenter extends BasePresenter<AppManagerContract.IAppManagerModel, AppManagerContract.AppManagerView> {


    @Inject
    public AppManagerPresenter(AppManagerContract.IAppManagerModel mModel, AppManagerContract.AppManagerView mView) {
        super(mModel, mView);
    }

    /**
     * 获取下载中的数据
     */
    public void getDownloadingApps() {
        mModel.getDownloadRecord()
                .compose(RxSchedulers.<List<DownloadRecord>>io_main())
                .subscribe(new ProgressSubcriber<List<DownloadRecord>>(mContext, mView) {
                    @Override
                    public void onNext(List<DownloadRecord> downloadRecords) {
                        mView.showDownloading(downloadRecordFilter(downloadRecords));
                    }
                });
    }


    /**
     * 获取已安装数据
     */
    public void getInstalledApps(){
        mModel.getInstalledApp().compose(RxSchedulers.<List<AndroidApk>>io_main())
                .subscribe(new ProgressSubcriber<List<AndroidApk>>(mContext,mView) {
                    @Override
                    public void onNext(List<AndroidApk> androidApks) {
                        mView.showApp(androidApks);
                    }
                });

    }

    public RxDownload getRxDownlad() {
        return mModel.getRxDownload();
    }


    /**
     * 获取本地apk文件
     */
    public void getLocalApks(){
        mModel.getLocalApp().compose(RxSchedulers.<List<AndroidApk>>io_main())
                .subscribe(new ProgressSubcriber<List<AndroidApk>>(mContext,mView) {
                    @Override
                    public void onNext(List<AndroidApk> androidApks) {
                        mView.showApp(androidApks);
                    }
                });
    }




    /**
     * 筛选数据
     *
     * @param downloadRecords
     * @return
     */
    private List<DownloadRecord> downloadRecordFilter(List<DownloadRecord> downloadRecords) {
        List<DownloadRecord> newList = new ArrayList<>();
        for (DownloadRecord r : downloadRecords) {
            if (r.getFlag() != DownloadFlag.COMPLETED) {
                newList.add(r);
            }
        }
        return newList;
    }
}
