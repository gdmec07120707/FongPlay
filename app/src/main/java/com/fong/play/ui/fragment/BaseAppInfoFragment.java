package com.fong.play.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fong.play.R;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.PageBean;
import com.fong.play.presenter.AppInfoPresenter;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.ui.adapter.AppInfoAdapter;
import com.fong.play.ui.widget.DividerItemDecoration;

import butterknife.BindView;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */

public  abstract  class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenter>
        implements BaseQuickAdapter.RequestLoadMoreListener,AppInfoContract.AppInfoView {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private AppInfoAdapter mAdapter;

    private int page =0;

    @Override
    public void init() {
        initRecyclerView();
        mPresenter.requestData(page,type());
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = buildAdapter();//

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showResult(PageBean<AppInfo> data) {
        mAdapter.addData(data.getDatas());
        if(data.isHasMore()){
            page++;
        }
        mAdapter.setEnableLoadMore(data.isHasMore());

    }

    @Override
    public void onLoadMoreComplete() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(page,type());
    }

    public abstract int type();

    public abstract AppInfoAdapter buildAdapter();
}
