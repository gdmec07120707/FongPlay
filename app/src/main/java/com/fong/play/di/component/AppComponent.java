package com.fong.play.di.component;

import android.app.Application;

import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.data.http.ApiService;
import com.fong.play.di.module.AppModule;
import com.fong.play.di.module.DownloadModule;
import com.fong.play.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by FANGDINGJIE
 * 2018/3/19.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class, DownloadModule.class})
public interface AppComponent {
    public ApiService getApiService();

    public Application getApplication();

    public RxErrorHnadler getRxErrorHandler();

    public RxDownload getRxDownload();

}
