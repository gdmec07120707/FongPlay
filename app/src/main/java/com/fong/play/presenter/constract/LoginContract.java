package com.fong.play.presenter.constract;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.LoginBean;
import com.fong.play.ui.BaseView;
import rx.Observable;

/**
 * Created by FANGDINGJIE
 * 2018/3/30.
 */

public class LoginContract {

    public interface ILoginModel {
        Observable<BaseBean<LoginBean>> login(String phone, String psw);
    }

    public interface LoginView extends BaseView {
        void showResult(LoginBean data);

        void checkPhoneError();

        void checkPhoneSuccess();

    }
}
