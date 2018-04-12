package com.fong.play.presenter.constract;

import com.fong.play.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

public class AppManagerContract {


    public interface IAppManagerModel{
        RxDownload getRxDownload();
        Observable<List<DownloadRecord>> getDownloadRecord();
    }

    public interface AppManagerView extends BaseView{
        void showDownloading(List<DownloadRecord> downloadRecords);
    }
}
