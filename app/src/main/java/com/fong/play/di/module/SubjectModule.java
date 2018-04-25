package com.fong.play.di.module;

import com.fong.play.data.SubjectModel;
import com.fong.play.data.http.ApiService;
import com.fong.play.di.FragmentScope;
import com.fong.play.presenter.constract.SubjectContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SubjectModule {

    private SubjectContract.SubjectView mView;

    public SubjectModule(SubjectContract.SubjectView view){
        this.mView = view;
    }

    @FragmentScope
    @Provides
    public SubjectContract.ISubjectModel provideModel(ApiService apiService){
        return new SubjectModel(apiService);
    }

    @FragmentScope
    @Provides
    public SubjectContract.SubjectView provideView(){
        return mView;
    }
}
