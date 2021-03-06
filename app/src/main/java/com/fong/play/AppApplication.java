package com.fong.play;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.fong.play.common.utils.LogUtil;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppComponent;
import com.fong.play.di.module.AppModule;
import com.fong.play.di.module.HttpModule;

/**
 * Created by FANGDINGJIE
 * 2018/3/19.
 */

public class AppApplication extends Application {

    private AppComponent mAppComponent;

    private View mCacheView;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule())
                .build();
        LogUtil.init(true);
       // Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static AppApplication get(Context context){
        return (AppApplication) context.getApplicationContext();
    }

    public View getCacheView() {
        return mCacheView;
    }

    public void setCacheView(View mCacheView) {
        this.mCacheView = mCacheView;
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
