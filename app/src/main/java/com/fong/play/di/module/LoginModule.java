package com.fong.play.di.module;

import com.fong.play.data.AppInfoModel;
import com.fong.play.data.LoginModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.presenter.constract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */
@Module
public class LoginModule {

    private LoginContract.LoginView mView;

    public LoginModule(LoginContract.LoginView mView) {
        this.mView = mView;
    }

    @Provides
    public LoginContract.LoginView provideView(){
        return mView;
    }

    @Provides
    public LoginContract.ILoginModel provideModel(ApiService apiService){
        return new LoginModel(apiService);
    }
}
