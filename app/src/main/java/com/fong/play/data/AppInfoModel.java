package com.fong.play.data;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.http.ApiService;

import io.reactivex.Observable;
import retrofit2.http.GET;


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

    /*首页*/
    public Observable<BaseBean<IndexBean>> getIndex() {
        return mApiService.getIndex();
    }

    /*排行榜*/
    public Observable<BaseBean<PageBean<AppInfo>>> getTopList(int page) {
        return mApiService.getTopList(page);
    }

    /*游戏*/
    public Observable<BaseBean<PageBean<AppInfo>>> getGames(int page) {
        return mApiService.getGames(page);
    }

    /*指定类别精品*/
    public Observable<BaseBean<PageBean<AppInfo>>> getFeaturedAppsByCategory(int categoryid, int page) {

        return mApiService.getFeaturedAppsByCategory(categoryid, page);
    }

    /*指定类别排行*/
    public Observable<BaseBean<PageBean<AppInfo>>> getTopListAppsByCategory(int categoryid, int page) {

        return mApiService.getTopListAppsByCategory(categoryid, page);
    }

    /*指定类别新品*/
    public Observable<BaseBean<PageBean<AppInfo>>> getNewListAppsByCategory(int categoryid, int page) {
        return mApiService.getNewListAppsByCategory(categoryid, page);
    }

    /*app详情*/
    public Observable<BaseBean<AppInfo>> getAppDetail(int id) {
        return mApiService.getAppDetail(id);
    }

}
