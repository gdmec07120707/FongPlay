package com.fong.play.presenter.constract;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.bean.Subject;
import com.fong.play.data.bean.SubjectDetail;
import com.fong.play.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public class SubjectContract  {

    public interface SubjectView extends BaseView{

        void showSubjects(PageBean<Subject> subject);

        void onLoadMoreComplete();

        void showSubjectDetail(SubjectDetail detail);

    }

    public interface ISubjectModel{

         Observable<BaseBean<PageBean<Subject>>> getSubjects(int page);
         Observable<BaseBean<SubjectDetail>> getSubjectDetail(int id);

    }

}
