package com.fong.play.presenter;

import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.data.AppInfoModel;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.presenter.constract.AppInfoContract;

import javax.inject.Inject;

/**
 * Created by FANGDINGJIE
 * 2018/4/3.
 */

public class AppDetailPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppDetailView> {

    @Inject
    public AppDetailPresenter(AppInfoModel mModel, AppInfoContract.AppDetailView mView) {
        super(mModel, mView);
    }

    public void requestAppInfo(int id) {
        mModel.getAppDetail(id)
                .compose(RxHttpResponseCompat.<AppInfo>compatResult())
                .subscribe(new ProgressSubcriber<AppInfo>(mContext, mView) {
                    @Override
                    public void onNext(AppInfo appInfo) {
                        mView.showResult(appInfo);
                    }
                });
    }
}
