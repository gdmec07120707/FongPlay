package com.fong.play.di.module;

import com.fong.play.data.AppInfoModel;
import com.fong.play.data.CategoryModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.presenter.constract.CategoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */
@Module
public class CategoryModule {

    private CategoryContract.CategoryView mView;

    public CategoryModule(CategoryContract.CategoryView mView) {
        this.mView = mView;
    }

    @Provides
    public CategoryContract.CategoryView provideView(){
        return mView;
    }

    @Provides
    public CategoryContract.ICategoryModel provideModel(ApiService apiService){
        return new CategoryModel(apiService);
    }
}
