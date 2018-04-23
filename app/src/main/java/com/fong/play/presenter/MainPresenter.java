package com.fong.play.presenter;

import android.annotation.SuppressLint;

import com.fong.play.common.Constant;
import com.fong.play.common.apkparest.AndroidApk;
import com.fong.play.common.http.JsonUtil;
import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.RxSchedulers;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.common.utils.ACache;
import com.fong.play.common.utils.AppUtils;
import com.fong.play.common.utils.PermissionUtil;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.AppsUpdateBean;
import com.fong.play.presenter.constract.MainContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static android.Manifest.permission.READ_PHONE_STATE;

public class MainPresenter extends BasePresenter<MainContract.IMainModel, MainContract.MainView> {

    @Inject
    public MainPresenter(MainContract.IMainModel mModel, MainContract.MainView mView) {
        super( mModel, mView );
    }

    @SuppressLint("CheckResult")
    public void requestPermisson() {
        PermissionUtil.requestPermisson( mContext, READ_PHONE_STATE ).doOnNext( new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    mView.requestPermissonFail();
                }
            }
        } ).subscribe( new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mView.requestPermissonSuccess();
            }
        } );
    }


    @SuppressLint("CheckResult")
    public void getAppUpdateInfo() {
        getInstalledApps()
                .flatMap( new Function<AppsUpdateBean, ObservableSource<List<AppInfo>>>() {
                    @Override
                    public ObservableSource<List<AppInfo>> apply(AppsUpdateBean appsUpdateBean) throws Exception {
                        return mModel.getUpdateApps(appsUpdateBean).compose( RxHttpResponseCompat.<List<AppInfo>>compatResult());
                    }
                } )
                .subscribe( new ProgressSubcriber<List<AppInfo>>( mContext, mView ) {
                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        if(appInfos!=null){
                            ACache.get(mContext).put(Constant.APP_UPDATE_LIST, JsonUtil.toJson(appInfos));
                        }
                        mView.changneAppNeedUpdateCount( appInfos == null ? 0 : appInfos.size() );
                    }
                } );

    }

    /**
     * 获取已安装的app，并进行封装
     *
     * @return
     */
    private Observable<AppsUpdateBean> getInstalledApps() {
        return Observable.create( new ObservableOnSubscribe<AppsUpdateBean>() {
            @Override
            public void subscribe(ObservableEmitter<AppsUpdateBean> e) throws Exception {
                e.onNext( buildParams( AppUtils.getInstalledApps( mContext ) ) );
                e.onComplete();
            }
        } );
    }

    /**
     * 构建参数
     *
     * @param apks
     * @return
     */
    private AppsUpdateBean buildParams(List<AndroidApk> apks) {
        StringBuilder packageNameBuilder = new StringBuilder();
        StringBuilder versionCodeBuilder = new StringBuilder();

        for (AndroidApk apk : apks) {
            if (!apk.isSystem()) {
                packageNameBuilder.append( apk.getPackageName() ).append( "," );
                versionCodeBuilder.append( apk.getAppVersionCode() ).append( "," );
            }
        }

        AppsUpdateBean param = new AppsUpdateBean();
        param.setPackageName( packageNameBuilder.toString() );
        param.setVersionCode( versionCodeBuilder.toString() );
        return param;
    }


}
