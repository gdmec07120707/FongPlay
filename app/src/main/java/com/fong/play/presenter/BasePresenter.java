package com.fong.play.presenter;

import com.fong.play.ui.BaseView;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public class BasePresenter<M,V extends BaseView> {
    protected M mModel;
    protected V mView;

    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
    }
}
