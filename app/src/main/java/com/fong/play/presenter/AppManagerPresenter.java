package com.fong.play.presenter;

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

    public RxDownload getRxDownlad() {
        return mModel.getRxDownload();
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
