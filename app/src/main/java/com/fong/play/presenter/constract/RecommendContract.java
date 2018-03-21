package com.fong.play.presenter.constract;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.presenter.BasePresenter;
import com.fong.play.ui.BaseView;

import java.util.List;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public interface RecommendContract {
    interface View extends BaseView {
        void showResult(List<AppInfo> data);

        void showError(String msg);

        void showEmpty();
    }

    /*interface Presenter extends BasePresenter {
        void requestData();
    }*/
}
