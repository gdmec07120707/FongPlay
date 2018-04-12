package com.fong.play.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fong.play.R;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppInfoComponent;
import com.fong.play.di.module.AppInfoModule;
import com.fong.play.presenter.AppInfoPresenter;
import com.fong.play.ui.adapter.AppInfoAdapter;

/**
 * Created by yechao on 2017/6/7.
 * Describe :
 */

public class GamesFragment extends BaseAppInfoFragment {


    @Override
    public int type() {
        return AppInfoPresenter.GAME;
    }

    @Override
    public AppInfoAdapter buildAdapter() {
        return AppInfoAdapter.builder().showBrief(false).showCategoryName(true).showPosition(true).rxDownload(mRxDownload).build();
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder()
                .appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build()
                .injectGamesFragment(this);
    }
}
