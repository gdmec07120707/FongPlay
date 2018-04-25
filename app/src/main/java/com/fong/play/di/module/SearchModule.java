package com.fong.play.di.module;

import com.fong.play.data.SearchModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.di.FragmentScope;
import com.fong.play.presenter.constract.SearchContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    private SearchContract.SearchView mView;

    public SearchModule(SearchContract.SearchView mView){
        this.mView = mView;
    }

    @FragmentScope
    @Provides
    public SearchContract.SearchView provideView(){
        return mView;
    }

    @FragmentScope
    @Provides
    public SearchContract.ISearchModel provideModel(ApiService apiService){
        return new SearchModel(apiService);
    }
}
