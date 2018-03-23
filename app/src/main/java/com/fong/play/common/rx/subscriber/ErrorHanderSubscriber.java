package com.fong.play.common.rx.subscriber;

import android.content.Context;

import com.fong.play.common.exception.BaseException;
import com.fong.play.common.rx.RxErrorHnadler;
import com.orhanobut.logger.Logger;


/**
 * Created by FANGDINGJIE
 * 2018/3/23.'
 * 错误统一处理
 */

public abstract class ErrorHanderSubscriber<T> extends DefaultSubscriber<T> {
    protected RxErrorHnadler mErrorHandler = null;
    //protected Context mContext;

    public ErrorHanderSubscriber(RxErrorHnadler mErrorHandler) {
        //this.mContext = mContext;
        //mErrorHandler = new RxErrorHnadler(mContext);
        this.mErrorHandler = mErrorHandler;
    }

    @Override
    public void onError(Throwable e) {
        BaseException baseException = mErrorHandler.handlerError(e);
        if (baseException == null) {
            e.printStackTrace();
            Logger.d(e.getMessage());
        } else {
            mErrorHandler.showErrorMessage(baseException);
        }
    }
}
