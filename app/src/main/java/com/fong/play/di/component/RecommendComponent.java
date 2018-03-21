package com.fong.play.di.component;

import com.fong.play.di.FragmentScope;
import com.fong.play.di.module.RecommendModule;
import com.fong.play.ui.fragment.RecommendFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by FANGDINGJIE
 * 2018/3/19.
 */
@FragmentScope
@Component(modules = RecommendModule.class,dependencies = AppComponent.class)
public interface RecommendComponent {

    void inject(RecommendFragment fragment);
}
