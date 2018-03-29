package com.fong.play.di.module;

import com.fong.play.data.AppInfoModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.AppInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */
@Module
public class AppInfoModule {

    private AppInfoContract.AppInfoView mView;

    public AppInfoModule(AppInfoContract.AppInfoView mView) {
        this.mView = mView;
    }

    @Provides
    public AppInfoContract.AppInfoView provideView(){
        return mView;
    }

    @Provides
    public AppInfoModel provideModel(ApiService apiService){
        return new AppInfoModel(apiService);
    }
}
