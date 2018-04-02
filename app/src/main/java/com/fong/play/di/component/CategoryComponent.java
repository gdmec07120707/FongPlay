package com.fong.play.di.component;

import com.fong.play.di.FragmentScope;
import com.fong.play.di.module.AppInfoModule;
import com.fong.play.di.module.CategoryModule;
import com.fong.play.ui.fragment.CategoryFragment;
import com.fong.play.ui.fragment.GamesFragment;
import com.fong.play.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */
@FragmentScope
@Component(modules = CategoryModule.class,dependencies = AppComponent.class)
public interface CategoryComponent {

    void inject(CategoryFragment fragment);

}
