package com.fong.play.common.rx.subscriber;

import android.content.Context;

import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.common.utils.ProgressDialogHandler;

import io.reactivex.disposables.Disposable;

/**
 * Created by FANGDINGJIE
 * 2018/3/23.
 */

public abstract class ProgressDialogSubcriber<T> extends ErrorHanderSubscriber<T> implements ProgressDialogHandler.OnProgressCancelListener{

    private ProgressDialogHandler mProgressDialogHandler;
    private Disposable mDisponsable;

    public ProgressDialogSubcriber(Context context) {
        super(context);
        mProgressDialogHandler = new ProgressDialogHandler(context);
    }

    protected boolean isShowProgressDialog() {
        return true;
    }

    @Override
    public void onCancelProgress() {
        mDisponsable.dispose();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisponsable = d;
        if(isShowProgressDialog()){
            this.mProgressDialogHandler.showProgressDialog();
        }

    }

    @Override
    public void onComplete() {



        if(isShowProgressDialog()){
            this.mProgressDialogHandler.dismissProgressDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);

        if(isShowProgressDialog()){
            this.mProgressDialogHandler.dismissProgressDialog();
        }

    }
}
