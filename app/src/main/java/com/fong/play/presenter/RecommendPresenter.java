package com.fong.play.presenter;

import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ErrorHanderSubscriber;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.RecommendModel;
import com.fong.play.presenter.constract.RecommendContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public class RecommendPresenter extends BasePresenter<RecommendModel, RecommendContract.View> {
    protected RxErrorHnadler mErrorHandler = null;

    @Inject
    public RecommendPresenter(RecommendContract.View view, RecommendModel model,RxErrorHnadler mErrorHandler) {
        super(model, view);
        //通过注入的方式，全局调用
        this.mErrorHandler = mErrorHandler;
    }

    public void requestData() {
        mModel.getApps()
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(new ErrorHanderSubscriber<PageBean<AppInfo>>(mErrorHandler) {

                    @Override
                    public void onStart() {
                        mView.dismissionProgress();
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissionProgress();
                    }

                    @Override
                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
                        if (appInfoPageBean != null) {
                            mView.showResult(appInfoPageBean.getDatas());
                        } else {
                            mView.showEmpty();
                        }
                    }
                });
    }
}
