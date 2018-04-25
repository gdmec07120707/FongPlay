package com.fong.play.di.component;

import com.fong.play.di.FragmentScope;
import com.fong.play.di.module.SubjectModule;
import com.fong.play.ui.fragment.base.BaseSubjectFragment;

import dagger.Component;

@FragmentScope
@Component(modules = SubjectModule.class,dependencies = AppComponent.class )
public interface SubjectComponent {
    void inject(BaseSubjectFragment fragment);
}
