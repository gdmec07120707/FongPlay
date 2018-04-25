package com.fong.play.data;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.bean.Subject;
import com.fong.play.data.bean.SubjectDetail;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.SubjectContract;

import io.reactivex.Observable;

public class SubjectModel implements SubjectContract.ISubjectModel {

    private ApiService mApiService;

    public SubjectModel(ApiService mApiService){
        this.mApiService = mApiService;
    }


    @Override
    public Observable<BaseBean<PageBean<Subject>>> getSubjects(int page) {
        return mApiService.getSubjects(page);
    }

    @Override
    public Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id) {
        return mApiService.getSubjectDetail(id);
    }
}
