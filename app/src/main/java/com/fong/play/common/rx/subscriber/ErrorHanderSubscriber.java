package com.fong.play.common.rx.subscriber;

import android.content.Context;
import android.content.Intent;

import com.fong.play.common.exception.BaseException;
import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.ui.activity.LoginActivity;
import com.orhanobut.logger.Logger;


/**
 * Created by FANGDINGJIE
 * 2018/3/23.'
 * 错误统一处理
 */

public abstract class ErrorHanderSubscriber<T> extends DefaultSubscriber<T> {
    protected RxErrorHnadler mErrorHandler = null;
    protected Context mContext;

    public ErrorHanderSubscriber(Context mContext) {
        this.mContext = mContext;
        mErrorHandler = new RxErrorHnadler(mContext);
        //this.mErrorHandler = mErrorHandler;
    }

    @Override
    public void onError(Throwable e) {
        BaseException baseException = mErrorHandler.handlerError(e);
        if (baseException == null) {
            e.printStackTrace();
            Logger.d(e.getMessage());
        } else {
            mErrorHandler.showErrorMessage(baseException);
            //判断Token失效
            if(baseException.getCode() == BaseException.ERROR_TOKEN){
                toLogin();
            }
        }
    }

    private void toLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }
}
