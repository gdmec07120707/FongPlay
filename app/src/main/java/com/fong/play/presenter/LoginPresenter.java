package com.fong.play.presenter;

import com.fong.play.common.Constant;
import com.fong.play.common.rx.RxBus;
import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ErrorHanderSubscriber;
import com.fong.play.common.utils.ACache;
import com.fong.play.common.utils.VerificationUtils;
import com.fong.play.data.bean.LoginBean;
import com.fong.play.presenter.constract.LoginContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by FANGDINGJIE
 * 2018/3/30.
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginModel, LoginContract.LoginView> {

    @Inject
    public LoginPresenter(LoginContract.ILoginModel mModel, LoginContract.LoginView mView) {
        super(mModel, mView);
    }

    public void login(String phone, String psw) {
        if(!VerificationUtils.matcherPhoneNum(phone)){
            mView.checkPhoneError();
            return;
        }else{
            mView.checkPhoneSuccess();
        }

        mModel.login(phone, psw)
                .compose(RxHttpResponseCompat.<LoginBean>compatResult())
                .subscribe(new ErrorHanderSubscriber<LoginBean>(mContext) {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.showResult(loginBean);
                        saveSuser(loginBean);

                        RxBus.getDefault().post(loginBean.getUser());
                    }
                });
    }

    private void saveSuser(LoginBean loginBean) {
        ACache mAcache =  ACache.get(mContext);
        mAcache.put(Constant.TOKEN,loginBean.getToken());
        mAcache.put(Constant.USER,loginBean.getUser());
    }
}
