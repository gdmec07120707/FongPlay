package com.fong.play.data;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.http.ApiService;
import rx.Observable;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public class AppInfoModel {

    private ApiService mApiService;


    public AppInfoModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getApps() {
        return mApiService.getApps("{'page':0}");
    }

    public Observable<BaseBean<IndexBean>> getIndex() {
        return mApiService.getIndex();
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getTopList(int page){
        return mApiService.getTopList(page);
    };

    public Observable<BaseBean<PageBean<AppInfo>>> getGames(int page){
        return mApiService.getGames(page);
    }

}