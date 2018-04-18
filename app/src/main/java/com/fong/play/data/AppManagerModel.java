package com.fong.play.data;

import android.annotation.SuppressLint;
import android.content.Context;

import com.fong.play.common.Constant;
import com.fong.play.common.utils.ACache;
import com.fong.play.common.apkparest.AndroidApk;
import com.fong.play.common.utils.AppUtils;
import com.fong.play.presenter.constract.AppManagerContract;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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

    @SuppressLint("CheckResult")
    @Override
    public Observable<List<AndroidApk>> getLocalApp() {
        final String dir = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR);
        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {
                e.onNext(scanApks(dir));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<AndroidApk>> getInstalledApp() {

        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {
                e.onNext(AppUtils.getInstalledApps(context));
                e.onComplete();
            }
        });
    }


    /**
     * 扫描apk文件
     * @param dir
     * @return
     */
    private List<AndroidApk> scanApks(String dir) {
        File file = new File(dir);
        if (!file.isDirectory()) {
            throw new RuntimeException("file is not Dir");
        }

        File[] apks = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return false;
                }
                return file.getName().endsWith(".apk");
            }
        });

        List<AndroidApk> androidApks = new ArrayList<>();

        for (File apk : apks) {
            AndroidApk androidApk = AndroidApk.read(context, apk.getPath());
            androidApks.add(androidApk);
        }

        return androidApks;
    }
}
