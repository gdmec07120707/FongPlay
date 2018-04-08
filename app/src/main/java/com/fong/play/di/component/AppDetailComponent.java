package com.fong.play.di.component;

import com.fong.play.di.FragmentScope;
import com.fong.play.di.module.AppDetailModule;
import com.fong.play.di.module.AppInfoModule;
import com.fong.play.ui.activity.AppDetailActivity;
import com.fong.play.ui.fragment.AppDetailFragment;
import com.fong.play.ui.fragment.CategoryAppFragment;
import com.fong.play.ui.fragment.GamesFragment;
import com.fong.play.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */
@FragmentScope
@Component(modules = AppDetailModule.class,dependencies = AppComponent.class)
public interface AppDetailComponent {

    void inject(AppDetailFragment activity);

}
