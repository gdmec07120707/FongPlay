package com.fong.play.presenter.constract;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.bean.SearchResult;
import com.fong.play.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class SearchContract {


    public interface SearchView extends BaseView {
        void showSearchHistory(List<String> list);
        void showSuggestions(List<String> list);
        void showSearchResult(SearchResult result);

    }

    public interface ISearchModel {
        Observable<BaseBean<List<String>>> getSuggestions(String keywork);

        Observable<BaseBean<SearchResult>> getSearchResult(String keyword);

    }
}
