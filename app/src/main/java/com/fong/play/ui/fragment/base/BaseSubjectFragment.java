package com.fong.play.ui.fragment.base;

import com.fong.play.data.bean.PageBean;
import com.fong.play.data.bean.Subject;
import com.fong.play.data.bean.SubjectDetail;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerSubjectComponent;
import com.fong.play.di.module.SubjectModule;
import com.fong.play.presenter.SubjectPresenter;
import com.fong.play.presenter.constract.SubjectContract;
import com.fong.play.ui.fragment.ProgressFragment;

public abstract class BaseSubjectFragment extends ProgressFragment<SubjectPresenter> implements SubjectContract.SubjectView{

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerSubjectComponent.builder()
                .appComponent( appComponent )
                .subjectModule( new SubjectModule( this ) )
                .build().inject( this );
    }

    @Override
    public void showSubjects(PageBean<Subject> subject) {

    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {


    }
}
