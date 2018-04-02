package com.fong.play.di.module;

import android.app.Application;

import com.fong.play.common.http.CommonParamsInterceptor;
import com.fong.play.common.http.HttpLogger;
import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.data.http.ApiService;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FANGDINGJIE
 * 2018/3/20.
 */
@Singleton
@Module
public class HttpModule {

    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient(Application application, Gson gson){
        // log用拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();//new HttpLogger()

        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
//        SSLSocketFactory sslSocketFactory = null;

        return new OkHttpClient.Builder()
                // HeadInterceptor实现了Interceptor，用来往Request Header添加一些业务相关数据，如APP版本，token信息
//                .addInterceptor(new HeadInterceptor())
                .addInterceptor(new CommonParamsInterceptor(application,gson))
                //.addInterceptor(logging)
                .addNetworkInterceptor(logging)
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)

                .build();


    }


    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient okHttpClient){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient);


        return builder.build();

    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public RxErrorHnadler provideRxErrorHandler(Application application){
        return new RxErrorHnadler(application);
    }
}
