package com.fong.play.presenter;

import com.fong.play.common.rx.RxErrorHnadler;
import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ErrorHanderSubscriber;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.data.AppInfoModel;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.presenter.constract.AppInfoContract;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by FANGDINGJIE
 * 2018/3/17.
 */

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    public static final int TOP_LIST = 1;
    public static final int GAME = 2;

    @Inject
    public AppInfoPresenter(AppInfoContract.AppInfoView view, AppInfoModel model) {
        super(model, view);
    }

    public void requestData(int page, int type) {

        Subscriber subscriber = null;

        if (page == 0) {
            //显示第一页
            subscriber = new ProgressSubcriber<PageBean<AppInfo>>(mContext, mView) {
                @Override
                public void onNext(PageBean<AppInfo> appInfoPageBean) {
                    mView.showResult(appInfoPageBean);
                }
            };
        } else {
            //加载更多
            subscriber = new ErrorHanderSubscriber<PageBean<AppInfo>>(mContext) {
                @Override
                public void onCompleted() {
                    mView.onLoadMoreComplete();
                }

                @Override
                public void onNext(PageBean<AppInfo> appInfoPageBean) {
                    mView.showResult(appInfoPageBean);
                }
            };
        }

        getObservable(page, type)
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(subscriber);

    }


    private Observable<BaseBean<PageBean<AppInfo>>> getObservable(int page, int type) {
        switch (type) {
            case TOP_LIST:
                return mModel.getTopList(page);
            case GAME:
                return mModel.getGames(page);
            default:
                return Observable.empty();

        }
    }


}
