package com.fong.play.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.RxSchedulers;
import com.fong.play.common.utils.ACache;
import com.fong.play.common.utils.AppUtils;
import com.fong.play.common.utils.PackageUtils;
import com.fong.play.common.utils.PermissionUtil;
import com.fong.play.data.bean.AppDownloadInfo;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.BaseBean;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadBean;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by FANGDINGJIE
 * 2018/4/9.
 */

public class DownloadButtonConntroller {


    private static RxDownload mRxDownload;

    public API mApi;

    public DownloadButtonConntroller(RxDownload downloader) {
        this.mRxDownload = downloader;
        if (mRxDownload != null) {
            mApi = mRxDownload.getRetrofit().create(API.class);
        }
    }



    public void handClick(final DownloadProgressButton btn,DownloadRecord record){
        AppInfo info = downloadRecord2AppInfo(record);
        receiveDownloadStatus(record.getUrl()).subscribe(new DownloadConsumer(btn,info));
    }

    public void handClick(final DownloadProgressButton btn, final AppInfo mAppInfo) {
        if (mApi == null) {
            return;
        }

        isAppInstalled(btn.getContext(), mAppInfo)
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(DownloadEvent downloadEvent) throws Exception {
                        if (DownloadFlag.UN_INSTALL == downloadEvent.getFlag()) {
                           return isApkFileExsit(btn.getContext(), mAppInfo);
                        }
                        return Observable.just(downloadEvent);
                    }
                })
                .flatMap(new Function<DownloadEvent, ObservableSource<DownloadEvent>>() {
                    @Override
                    public ObservableSource<DownloadEvent> apply(DownloadEvent downloadEvent) throws Exception {
                        if (DownloadFlag.FILE_EXIST == downloadEvent.getFlag()) {
                            return getAppDownloadInfo(mAppInfo)
                                    .flatMap(new Function<AppDownloadInfo, ObservableSource<DownloadEvent>>() {
                                        @Override
                                        public ObservableSource<DownloadEvent> apply(AppDownloadInfo appDownloadInfo) throws Exception {
                                            mAppInfo.setAppDownloadInfo(appDownloadInfo);
                                            return receiveDownloadStatus(appDownloadInfo.getDownloadUrl());
                                        }
                                    });
                        }
                        return Observable.just(downloadEvent);
                    }
                })
                .compose(RxSchedulers.<DownloadEvent>io_main())
                .subscribe(new DownloadConsumer(btn, mAppInfo));
    }

    /**
     * 判断应用是否安装
     *
     * @param context
     * @param mAppInfo
     * @return
     */
    private static Observable<DownloadEvent> isAppInstalled(Context context, AppInfo mAppInfo) {
        DownloadEvent event = new DownloadEvent();
        event.setFlag(AppUtils.isInstalled(context, mAppInfo.getPackageName()) ? DownloadFlag.INSTALLED : DownloadFlag.UN_INSTALL);
        return Observable.just(event);
    }

    /**
     * 判断是否存在该文件
     *
     * @param mAppInfo
     * @return
     */
    private static Observable<DownloadEvent> isApkFileExsit(Context context, AppInfo mAppInfo) {
        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + File.separator + mAppInfo.getReleaseKeyHash();
        File file = new File(path);
        DownloadEvent event = new DownloadEvent();
        event.setFlag(file.exists() ? DownloadFlag.FILE_EXIST : DownloadFlag.NORMAL);
        return Observable.just(event);
    }

    /**
     * 获取app信息
     *
     * @param appInfo
     * @return
     */
    private Observable<AppDownloadInfo> getAppDownloadInfo(AppInfo appInfo) {

        return mApi.getAppDownloadInfo(appInfo.getId()).compose(RxHttpResponseCompat.<AppDownloadInfo>compatResult());
    }

    private Observable<DownloadEvent> receiveDownloadStatus(String url) {
        return mRxDownload.receiveDownloadStatus(url);
    }

    class DownloadConsumer implements Consumer<DownloadEvent> {
        DownloadProgressButton btn;
        AppInfo mAppInfo;

        public DownloadConsumer(DownloadProgressButton b, AppInfo appInfo) {

            this.btn = b;
            this.mAppInfo = appInfo;
        }

        @Override
        public void accept(DownloadEvent downloadEvent) throws Exception {
            int flag = downloadEvent.getFlag();
            btn.setTag(R.id.tag_apk_flag, flag);
            //设置点击事件
            bindClick(btn, mAppInfo);

            switch (flag) {
                case DownloadFlag.INSTALLED:
                    btn.setText("运行");
                    break;
                case DownloadFlag.NORMAL:
                    btn.download();
                    break;
                case DownloadFlag.STARTED:
                    btn.setProgress((int) downloadEvent.getDownloadStatus().getPercentNumber());
                    break;
                case DownloadFlag.PAUSED:
                    btn.paused();
                    break;
                case DownloadFlag.COMPLETED: //已完成
                    btn.setText("安装");
                    break;
                case DownloadFlag.FAILED://下载失败
                    btn.setText("失败");
                    break;
                case DownloadFlag.DELETED: //已删除
                    break;

            }

        }
    }

    /**
     * 按钮点击事件
     *
     * @param btn
     * @param mAppInfo
     */
    @SuppressLint("CheckResult")
    private void bindClick(final DownloadProgressButton btn, final AppInfo mAppInfo) {
        RxView.clicks(btn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                int flag = (int) btn.getTag(R.id.tag_apk_flag);
                switch (flag) {
                    case DownloadFlag.INSTALLED:   //下载完成（点击安装）
                        runApp(btn.getContext(), mAppInfo.getPackageName());
                        break;
                    // 升级 还加上去
                    case DownloadFlag.STARTED:   //开始下载（点击下载）
                        pausedDownload(mAppInfo.getAppDownloadInfo().getDownloadUrl());
                        break;
                    case DownloadFlag.NORMAL:
                    case DownloadFlag.PAUSED:
                        startDownload(btn, mAppInfo);
                        break;
                    case DownloadFlag.COMPLETED:
                        installApp(btn.getContext(), mAppInfo);

                        break;

                }
            }
        });
    }


    /**
     * 开始下载
     *
     * @param btn
     * @param mAppInfo
     */
    @SuppressLint("CheckResult")
    private void startDownload(final DownloadProgressButton btn, final AppInfo mAppInfo) {
        PermissionUtil.requestPermisson(btn.getContext(), WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            final AppDownloadInfo downloadInfo = mAppInfo.getAppDownloadInfo();
                            if (downloadInfo == null) {
                                getAppDownloadInfo(mAppInfo).subscribe(new Consumer<AppDownloadInfo>() {
                                    @Override
                                    public void accept(AppDownloadInfo appDownloadInfo) throws Exception {
                                        mAppInfo.setAppDownloadInfo(appDownloadInfo);
                                        download(btn, mAppInfo);
                                    }
                                });
                            } else {
                                download(btn, mAppInfo);
                            }
                        }else{
                            Toast.makeText(btn.getContext(),"无权限",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void download(DownloadProgressButton btn, AppInfo info) {


        mRxDownload.serviceDownload(appInfo2DownloadBean(info)).subscribe();
        mRxDownload.receiveDownloadStatus(info.getAppDownloadInfo().getDownloadUrl()).subscribe(new DownloadConsumer(btn, info));
    }


    private DownloadBean appInfo2DownloadBean(AppInfo info){
        DownloadBean downloadBean = new DownloadBean();
        downloadBean.setUrl(info.getAppDownloadInfo().getDownloadUrl());
        downloadBean.setSaveName(info.getReleaseKeyHash()+".apk");

        downloadBean.setExtra1(info.getId()+ "");
        downloadBean.setExtra2(info.getIcon());
        downloadBean.setExtra3(info.getDisplayName());
        downloadBean.setExtra4(info.getPackageName());
        downloadBean.setExtra5(info.getReleaseKeyHash());
        return downloadBean;
    }

    /**
     * downloadRecord转化为AppInfo为了AppManager中的显示
     * @param bean
     * @return
     */
    public AppInfo downloadRecord2AppInfo(DownloadRecord bean) {
        AppInfo info = new AppInfo();
        info.setId(Integer.parseInt(bean.getExtra1()));
        info.setIcon(bean.getExtra2());
        info.setDisplayName(bean.getExtra3());
        info.setPackageName(bean.getExtra4());
        info.setReleaseKeyHash(bean.getExtra5());

        AppDownloadInfo downloadInfo = new AppDownloadInfo();
        downloadInfo.setDowanloadUrl(bean.getUrl());
        info.setAppDownloadInfo(downloadInfo);

        return info;


    }

    /**
     * 暂停下载
     *
     * @param downloadUrl
     */
    private void pausedDownload(String downloadUrl) {
        mRxDownload.pauseServiceDownload(downloadUrl).subscribe();
    }

    /**
     * 打开app
     *
     * @param context
     * @param packageName
     */
    private void runApp(Context context, String packageName) {
        AppUtils.runApp(context, packageName);
    }

    /**
     * 安装app
     *
     * @param context
     * @param mAppInfo
     */
    private void installApp(Context context, AppInfo mAppInfo) {
        String path = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR) + File.separator + mAppInfo.getReleaseKeyHash();
        AppUtils.installApk(context, path);
        PackageUtils.install(context,path);
    }

    interface API {
        @GET("download/{id}")
        Observable<BaseBean<AppDownloadInfo>> getAppDownloadInfo(@Path("id") int id);
    }
}
