package com.fong.play.data;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.http.ApiService;
import rx.Observable;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public class RecommendModel {

    private ApiService mApiService;


    public RecommendModel(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getApps() {
        return mApiService.getApps("{'page':0}");
    }
}
