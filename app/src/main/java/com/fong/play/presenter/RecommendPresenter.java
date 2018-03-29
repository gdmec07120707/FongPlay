package com.fong.play.presenter;

import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.data.AppInfoModel;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.presenter.constract.AppInfoContract;

import javax.inject.Inject;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public class RecommendPresenter extends BasePresenter<AppInfoModel, AppInfoContract.View> {
    protected RxErrorHnadler mErrorHandler = null;

    @Inject
    public RecommendPresenter(AppInfoContract.View view, AppInfoModel model, RxErrorHnadler mErrorHandler) {
        super(model, view);
        //通过注入的方式，全局调用
        this.mErrorHandler = mErrorHandler;
    }

    public void requestData() {
        mModel.getIndex().compose(RxHttpResponseCompat.<IndexBean>compatResult())
                .subscribe(new ProgressSubcriber<IndexBean>(mContext,mView) {
                    @Override
                    public void onNext(IndexBean indexBean) {
                        mView.showResult(indexBean);
                    }
                });

        /*RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .flatMap(new Func1<Boolean, Observable<PageBean<AppInfo>>>() {
                    @Override
                    public Observable<PageBean<AppInfo>> call(Boolean aBoolean) {
                        if (aBoolean) {
                            return mModel.getApps().compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult());
                        } else {
                            return Observable.empty();
                        }
                    }
                })
                .subscribe(new ProgressSubcriber<PageBean<AppInfo>>(mContext, mView) {
                    @Override
                    public void onNext(PageBean<AppInfo> appInfoPageBean) {
                        if (appInfoPageBean != null) {
                            mView.showResult(appInfoPageBean.getDatas());
                            mModel.getIndex();
                        } else {
                            mView.showEmpty();
                        }
                    }

                });*/
    }
}
