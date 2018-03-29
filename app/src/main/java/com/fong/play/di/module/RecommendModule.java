package com.fong.play.di.module;

import android.app.ProgressDialog;

import com.fong.play.data.AppInfoModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FANGDINGJIE
 * 2018/3/19.
 */
@Module
public class RecommendModule {

    private AppInfoContract.View view;

    public RecommendModule(AppInfoContract.View view){
        this.view = view;
    }

    @Provides
    public AppInfoContract.View provideView(){
        return view;
    }

    @Provides
    public ProgressDialog provideProgressDialog(AppInfoContract.View view){
        return new ProgressDialog(((RecommendFragment)view).getActivity());
    }

    @Provides
    public AppInfoModel provideModel(ApiService mApiService){
        return new AppInfoModel(mApiService);
    }
}
