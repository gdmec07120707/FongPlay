package com.fong.play.presenter.constract;

import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.AppsUpdateBean;
import com.fong.play.data.bean.BaseBean;
import com.fong.play.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public class MainContract {

    public interface MainView extends BaseView{
        void requestPermissonSuccess();
        void requestPermissonFail();
        void changneAppNeedUpdateCount(int count);
    }

    public interface IMainModel{
        Observable<BaseBean<List<AppInfo>>> getUpdateApps(AppsUpdateBean updateBean);
    }


}
