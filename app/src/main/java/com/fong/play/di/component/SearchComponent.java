package com.fong.play.di.component;

import com.fong.play.di.FragmentScope;
import com.fong.play.di.module.SearchModule;
import com.fong.play.ui.activity.SearchActivity;

import dagger.Component;

@FragmentScope
@Component(modules = SearchModule.class,dependencies = AppComponent.class)
public interface SearchComponent {

    void inject(SearchActivity activity);


}
