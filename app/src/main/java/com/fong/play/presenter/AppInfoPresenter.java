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
    public static final int  CATEGORY=3;

    public static final int FEATURED=0;
    public static final int TOPLIST=1;
    public static final int NEWLIST=2;

    @Inject
    public AppInfoPresenter(AppInfoContract.AppInfoView view, AppInfoModel model) {
        super(model, view);
    }



    public void request(int type,int page,int categoryId,int flagType){
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

        getObservable(page, type,categoryId,flagType)
                .compose(RxHttpResponseCompat.<PageBean<AppInfo>>compatResult())
                .subscribe(subscriber);
    }

    /**
     * 获取制定类别下的数据
     * @param categoryId
     * @param page
     * @param flagType
     */
    public void requestCategoryApps(int categoryId,int page,int flagType){
        request(CATEGORY,page,categoryId,flagType);
    }

    /**
     * 获取排行和类别的数据
     * @param page
     * @param type
     */
    public void requestData(int page, int type) {
        request(type,page,0,0);
    }


    private Observable<BaseBean<PageBean<AppInfo>>> getObservable(int page, int type,int categoryId,int flagType) {
        switch (type) {
            case TOP_LIST:
                return mModel.getTopList(page);
            case GAME:
                return mModel.getGames(page);
            case CATEGORY:
                if(flagType==FEATURED){
                    return mModel.getFeaturedAppsByCategory(categoryId,page);
                }else if(flagType == TOPLIST){
                    return mModel.getTopListAppsByCategory(categoryId,page);
                }else if(flagType == NEWLIST){
                    return mModel.getNewListAppsByCategory(categoryId,page);
                }
            default:
                return Observable.empty();

        }
    }


}
