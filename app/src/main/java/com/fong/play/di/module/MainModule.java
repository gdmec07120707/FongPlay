package com.fong.play.di.module;

import com.fong.play.data.MainModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.di.FragmentScope;
import com.fong.play.presenter.constract.MainContract;
import dagger.Module;
import dagger.Provides;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.di.module
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

@Module
public class MainModule {


    private MainContract.MainView mView;


    public MainModule(MainContract.MainView view){

        this.mView = view;
    }



    @FragmentScope
    @Provides
    public MainContract.MainView provideView(){


        return  mView;
    }


    @FragmentScope
    @Provides
    public MainContract.IMainModel provideModel(ApiService apiService){

        return  new MainModel(apiService);
    }


}
