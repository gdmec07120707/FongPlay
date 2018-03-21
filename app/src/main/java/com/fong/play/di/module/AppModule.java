package com.fong.play.di.module;

import android.app.Application;

import com.fong.play.AppApplication;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FANGDINGJIE
 * 2018/3/19.
 */
@Module
public class AppModule {
    private Application mApp;

    public AppModule(Application mApp){
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    public Application provideApplication(){
        return mApp;
    }


    @Provides
    @Singleton
    public Gson provideGson(){
        return new Gson();
    }
}
