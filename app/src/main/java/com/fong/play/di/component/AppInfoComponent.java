package com.fong.play.di.component;

import com.fong.play.di.FragmentScope;
import com.fong.play.di.module.AppInfoModule;
import com.fong.play.ui.fragment.CategoryAppFragment;
import com.fong.play.ui.fragment.GamesFragment;
import com.fong.play.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */
@FragmentScope
@Component(modules = AppInfoModule.class,dependencies = AppComponent.class)
public interface AppInfoComponent {

    void injectTopListFragment(TopListFragment fragment);

    void injectGamesFragment(GamesFragment fragment);

    void injectCategoryAppFragment(CategoryAppFragment fragment);
}
