package com.fong.play.presenter;

import com.fong.play.common.rx.RxHttpResponseCompat;
import com.fong.play.common.rx.subscriber.ErrorHanderSubscriber;
import com.fong.play.common.rx.subscriber.ProgressDialogSubcriber;
import com.fong.play.common.rx.subscriber.ProgressSubcriber;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.bean.Subject;
import com.fong.play.data.bean.SubjectDetail;
import com.fong.play.presenter.constract.SubjectContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class SubjectPresenter extends BasePresenter<SubjectContract.ISubjectModel,SubjectContract.SubjectView> {

    @Inject
    public SubjectPresenter(SubjectContract.ISubjectModel mModel, SubjectContract.SubjectView mView) {
        super( mModel, mView );
    }

    public void getSujects(int page){
        Observer subscriber = null;

        if(page==0){
            subscriber = new ProgressDialogSubcriber<PageBean<Subject>>(mContext) {

                @Override
                public void onNext(PageBean<Subject> subjectPageBean) {
                    mView.showSubjects( subjectPageBean );
                }
            };
        }else{
            subscriber = new ErrorHanderSubscriber<PageBean<Subject>>(mContext) {


                @Override
                public void onNext(PageBean<Subject> subjectPageBean) {
                    mView.showSubjects( subjectPageBean );
                }

                @Override
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }
            };
        }

        mModel.getSubjects(page)
                .compose(RxHttpResponseCompat.<PageBean<Subject>>compatResult())
                .subscribe(subscriber);
    }


    public void getSubjectDetail(int id){
        mModel.getSubjectDetail(id).compose(RxHttpResponseCompat.<SubjectDetail>compatResult())
                .subscribe( new ProgressSubcriber<SubjectDetail>(mContext,mView) {
                    @Override
                    public void onNext(SubjectDetail detail) {
                        mView.showSubjectDetail(detail);
                    }
                } );
    }
}
