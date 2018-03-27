package com.fong.play.ui;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public interface BaseView {
    void showLoading();

    void showError(String msg);

    void dismissLoading();
}
