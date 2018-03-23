package com.fong.play.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.fong.play.ui.BaseView;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public class BasePresenter<M,V extends BaseView> {
    protected M mModel;
    protected V mView;

    protected Context mContext;

    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
        initContext();
    }


    private void initContext(){
        if(mView instanceof Fragment){
            mContext = ((Fragment)mView).getActivity();
        }else{
            mContext = (Activity)mContext;
        }
    }
}
