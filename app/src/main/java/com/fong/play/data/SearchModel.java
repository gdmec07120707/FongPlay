package com.fong.play.data;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.SearchResult;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.SearchContract;

import java.util.List;

import io.reactivex.Observable;

public class SearchModel implements SearchContract.ISearchModel {

    private ApiService mApiService;

    public SearchModel(ApiService apiService){
        this.mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<String>>> getSuggestions(String keywork) {
        return mApiService.searchSuggest( keywork );
    }

    @Override
    public Observable<BaseBean<SearchResult>> getSearchResult(String keyword) {
        return mApiService.search( keyword );
    }
}
