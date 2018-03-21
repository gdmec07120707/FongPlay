package com.fong.play.di.module;

import android.app.ProgressDialog;

import com.fong.play.data.RecommendModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.RecommendPresenter;
import com.fong.play.presenter.constract.RecommendContract;
import com.fong.play.ui.fragment.RecommendFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FANGDINGJIE
 * 2018/3/19.
 */
@Module
public class RecommendModule {

    private RecommendContract.View view;

    public RecommendModule(RecommendContract.View view){
        this.view = view;
    }

    @Provides
    public RecommendContract.View provideView(){
        return view;
    }

    @Provides
    public ProgressDialog provideProgressDialog(RecommendContract.View view){
        return new ProgressDialog(((RecommendFragment)view).getActivity());
    }

    @Provides
    public RecommendModel provideModel(ApiService mApiService){
        return new RecommendModel(mApiService);
    }
}
