package com.fong.play.data;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.LoginBean;
import com.fong.play.data.bean.requestbean.LoginRequestBean;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.LoginContract;

import io.reactivex.Observable;


/**
 * Created by FANGDINGJIE
 * 2018/3/30.
 * 登陆model
 */

public class LoginModel implements LoginContract.ILoginModel {

    private ApiService mApiService;

    public LoginModel(ApiService mApiService){
        this.mApiService = mApiService;
    }


    @Override
    public Observable<BaseBean<LoginBean>> login(String phone, String psw) {
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setEmail(phone);
        loginRequestBean.setPassword(psw);
        return mApiService.login(loginRequestBean);
    }
}
