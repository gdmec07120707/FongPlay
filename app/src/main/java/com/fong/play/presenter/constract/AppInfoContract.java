package com.fong.play.presenter.constract;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.presenter.BasePresenter;
import com.fong.play.ui.BaseView;

import java.util.List;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public interface AppInfoContract {
    interface View extends BaseView {
        void showResult(IndexBean data);

        void showError(String msg);

    }

    interface AppInfoView extends BaseView {
        void showResult(PageBean<AppInfo> data);

        void onLoadMoreComplete();

    }

    /*interface Presenter extends BasePresenter {
        void requestData();
    }*/
}
