package com.fong.play.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fong.play.R;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.PageBean;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppInfoComponent;
import com.fong.play.di.module.AppInfoModule;
import com.fong.play.presenter.AppInfoPresenter;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.ui.adapter.AppInfoAdapter;
import com.fong.play.ui.widget.DividerItemDecoration;

import butterknife.BindView;


/**
 * Created by yechao on 2017/6/7.
 * Describe :
 */

public class TopListFragment extends BaseAppInfoFragment {

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build().injectTopListFragment(this);
    }


    @Override
    public int type() {
        return AppInfoPresenter.TOP_LIST;
    }

    @Override
    public AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showBrief(false).showCategoryName(true).showPosition(true).build();
    }



}
