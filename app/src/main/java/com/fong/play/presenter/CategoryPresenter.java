package com.fong.play.presenter;

import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.data.bean.Category;
import com.fong.play.presenter.constract.CategoryContract;
import com.fong.play.ui.BaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by FANGDINGJIE
 * 2018/4/2.
 */

public class CategoryPresenter extends BasePresenter<CategoryContract.ICategoryModel,CategoryContract.CategoryView> {

    @Inject
    public CategoryPresenter(CategoryContract.ICategoryModel mModel, CategoryContract.CategoryView mView) {
        super(mModel, mView);
    }

    public void requestData(){
        mModel.requestData()
                .compose(RxHttpResponseCompat.<List<Category>>compatResult())
                .subscribe(new ProgressSubcriber<List<Category>>(mContext,mView) {
                    @Override
                    public void onNext(List<Category> categories) {
                        mView.showResult(categories);
                    }
                });
    }
}
