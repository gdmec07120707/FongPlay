package com.fong.play.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fong.play.R;
import com.fong.play.common.rx.RxBus;
import com.fong.play.data.bean.PageBean;
import com.fong.play.data.bean.Subject;
import com.fong.play.ui.adapter.SubjectAdapter;
import com.fong.play.ui.fragment.base.BaseSubjectFragment;
import com.fong.play.ui.widget.SpaceItemDecoration2;

import butterknife.BindView;

public class SubjectFragment extends BaseSubjectFragment implements  BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    int page =0;

    private SubjectAdapter mAdapter;

    @Override
    public void init() {
        initRecyclerView();

        mPresenter.getSujects( page );
    }



    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(layoutManager);

        SpaceItemDecoration2 dividerDecoration = new SpaceItemDecoration2(5);
        mRecyclerView.addItemDecoration(dividerDecoration);


        mAdapter = new SubjectAdapter();

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener( new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Subject subject = mAdapter.getItem(position);
                RxBus.getDefault().post(subject);
            }
        } );

    }

    @Override
    public void showSubjects(PageBean<Subject> subject) {
        super.showSubjects( subject );
        mAdapter.addData(subject.getDatas());

        if(subject.isHasMore()){
            page++;
        }
        mAdapter.setEnableLoadMore(subject.isHasMore());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getSujects(page);
    }
}
