package com.fong.play.di.module;

import android.app.Application;

import com.fong.play.data.AppManagerModel;
import com.fong.play.presenter.constract.AppManagerContract;

import dagger.Module;
import dagger.Provides;
import zlc.season.rxdownload2.RxDownload;

@Module
public class AppManagerModule {

    private AppManagerContract.AppManagerView mView;

    public AppManagerModule(AppManagerContract.AppManagerView mView) {
        this.mView = mView;
    }

    @Provides
    public AppManagerContract.AppManagerView providerView(){
        return mView;
    }

    @Provides
    public AppManagerContract.IAppManagerModel providerModel(Application mApplication, RxDownload rxDownload){
        return new AppManagerModel(rxDownload,mApplication);
    }
}
