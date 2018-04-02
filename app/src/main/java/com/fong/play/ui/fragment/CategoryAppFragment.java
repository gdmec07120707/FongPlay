package com.fong.play.ui.fragment;

import android.annotation.SuppressLint;

import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppInfoComponent;
import com.fong.play.di.module.AppInfoModule;
import com.fong.play.ui.adapter.AppInfoAdapter;

/**
 * Created by FANGDINGJIE
 * 2018/4/2.
 */

@SuppressLint("ValidFragment")
public class CategoryAppFragment extends BaseAppInfoFragment {

    private int categoryId;
    private int mFlagType;

    @SuppressLint("ValidFragment")
    public CategoryAppFragment(int categoryId, int flagType){
        this.categoryId = categoryId;
        this.mFlagType = flagType;
    }

    @Override
    public void init() {
        mPresenter.requestCategoryApps(categoryId,page,mFlagType);
        initRecyclerView();
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showBrief(false).showCategoryName(false).showPosition(false).build();
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder()
                .appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build().injectCategoryAppFragment(this);
    }
}
