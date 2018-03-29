package com.fong.play.data.http;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.data.bean.PageBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public interface ApiService {
    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    @GET("featured2")
    public Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParams);

    @GET("index")
    public  Observable<BaseBean<IndexBean>> getIndex();

    @GET("toplist")
    public Observable<BaseBean<PageBean<AppInfo>>> getTopList(@Query("page") int page);

    @GET("game")
    public Observable<BaseBean<PageBean<AppInfo>>> getGames(@Query("page") int page);
}
