package com.fong.play.di.component;

import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.data.http.ApiService;
import com.fong.play.di.module.AppModule;
import com.fong.play.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by FANGDINGJIE
 * 2018/3/19.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    public ApiService getApiService();



    public RxErrorHnadler getRxErrorHandler();

}
