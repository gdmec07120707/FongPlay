package com.fong.play.presenter.constract;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.Category;
import com.fong.play.data.bean.LoginBean;
import com.fong.play.ui.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by FANGDINGJIE
 * 2018/3/30.
 */

public class CategoryContract {

    public interface ICategoryModel {
        Observable<BaseBean<List<Category>>> requestData();
    }

    public interface CategoryView extends BaseView {
        void showResult(List<Category> data);
    }
}
