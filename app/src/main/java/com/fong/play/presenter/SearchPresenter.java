package com.fong.play.presenter;

import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.data.bean.SearchResult;
import com.fong.play.presenter.constract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchPresenter extends BasePresenter<SearchContract.ISearchModel,SearchContract.SearchView> {

    @Inject
    public SearchPresenter(SearchContract.ISearchModel mModel, SearchContract.SearchView mView) {
        super( mModel, mView );
    }

    public void showHistory(){
        // get search history from  database
        List<String> list = new ArrayList<>();
        list.add("地图");
        list.add("KK");
        list.add("爱奇艺");
        list.add("播放器");
        list.add("支付宝");
        list.add("微信");
        list.add("QQ");
        list.add("TV");
        list.add("直播");
        list.add("妹子");
        list.add("美女");
        mView.showSearchHistory(list);
    }

    /**
     * 获取提醒列表
     * @param keyword
     */
    public void getSuggestions(String keyword) {
        mModel.getSuggestions(keyword)
                .compose(RxHttpResponseCompat.<List<String>>compatResult())
                .subscribe(new ProgressSubcriber<List<String>>(mContext,mView) {
                    @Override
                    public void onNext(List<String> strings) {
                        mView.showSuggestions(strings);
                    }
                });
    }


    /**
     * 查找关键词
     * @param keyword
     */
    public void search(String keyword) {
        saveSearchHistory(keyword);
        mModel.getSearchResult(keyword)
                .compose(RxHttpResponseCompat.<SearchResult>compatResult())
                .subscribe(new ProgressSubcriber<SearchResult>(mContext,mView) {
                    @Override
                    public void onNext(SearchResult result) {
                        mView.showSearchResult(result);
                    }
                });
    }

    /**
     * 保存到数据库
     * @param keyword
     */
    private void saveSearchHistory(String keyword) {
        // save to database
    }
}
