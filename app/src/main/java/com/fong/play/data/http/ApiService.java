package com.fong.play.data.http;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.Category;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.data.bean.LoginBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.bean.SearchResult;
import com.fong.play.data.bean.Subject;
import com.fong.play.data.bean.SubjectDetail;
import com.fong.play.data.bean.requestbean.LoginRequestBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public interface ApiService {
    public static final String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    @GET("featured2")
    Observable<BaseBean<PageBean<AppInfo>>> getApps(@Query("p") String jsonParams);

    @GET("index")
    Observable<BaseBean<IndexBean>> getIndex();

    @GET("toplist")
    Observable<BaseBean<PageBean<AppInfo>>> getTopList(@Query("page") int page);

    @GET("game")
    Observable<BaseBean<PageBean<AppInfo>>> getGames(@Query("page") int page);

    @POST("login")
    Observable<BaseBean<LoginBean>> login(@Body LoginRequestBean param);

    @GET("category")
    Observable<BaseBean<List<Category>>> getCategory();

    @GET("category/featured/{categoryid}")
    Observable<BaseBean<PageBean<AppInfo>>> getFeaturedAppsByCategory(@Path("categoryid") int categoryId, @Query("page") int page);

    @GET("category/toplist/{categoryid}")
    Observable<BaseBean<PageBean<AppInfo>>> getTopListAppsByCategory(@Path("categoryid") int categoryid, @Query("page") int page);

    @GET("category/newlist/{categoryid}")
    Observable<BaseBean<PageBean<AppInfo>>> getNewListAppsByCategory(@Path("categoryid") int categoryid, @Query("page") int page);

    @GET("app/{id}")
    Observable<BaseBean<AppInfo>> getAppDetail(@Path("id") int id);

    @GET("apps/updateinfo")
    Observable<BaseBean<List<AppInfo>>> getAppsUpdateinfo(@Query("packageName") String packageName,@Query("versionCode") String versionCode);

    @GET("subject/hot")
    Observable<BaseBean<PageBean<Subject>>> getSubjects(@Query("page") int page);

    @GET("subject/{id}")
    Observable<BaseBean<SubjectDetail>> getSubjectDetail(@Path("id") int id);

    @GET("search/suggest")
    Observable<BaseBean<List<String>>> searchSuggest(@Query("keyword") String keyword);

    @GET("search")
    Observable<BaseBean<SearchResult>> search(@Query("keyword") String keyword);




}
