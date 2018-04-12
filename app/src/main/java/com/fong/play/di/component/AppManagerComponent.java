package com.fong.play.di.component;

import com.fong.play.di.FragmentScope;
import com.fong.play.di.module.AppInfoModule;
import com.fong.play.di.module.AppManagerModule;
import com.fong.play.ui.fragment.CategoryAppFragment;
import com.fong.play.ui.fragment.DownloadingFragment;
import com.fong.play.ui.fragment.GamesFragment;
import com.fong.play.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */
@FragmentScope
@Component(modules = AppManagerModule.class,dependencies = AppComponent.class)
public interface AppManagerComponent {

    void injectDownloading(DownloadingFragment fragment);

}
