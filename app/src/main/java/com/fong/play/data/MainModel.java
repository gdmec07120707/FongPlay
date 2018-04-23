package com.fong.play.data;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.AppsUpdateBean;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.MainContract;

import java.util.List;

import io.reactivex.Observable;

public class MainModel implements MainContract.IMainModel {

    private ApiService mApiService;

    public MainModel(ApiService apiService){
        this.mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<AppInfo>>> getUpdateApps(AppsUpdateBean updateBean) {
        return mApiService.getAppsUpdateinfo(updateBean.getPackageName(),updateBean.getVersionCode());
    }
}
