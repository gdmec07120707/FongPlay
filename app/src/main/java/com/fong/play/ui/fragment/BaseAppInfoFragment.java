package com.fong.play.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fong.play.R;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.PageBean;
import com.fong.play.presenter.AppInfoPresenter;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.ui.activity.AppDetailActivity;
import com.fong.play.ui.adapter.AppInfoAdapter;
import com.fong.play.ui.widget.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import zlc.season.rxdownload2.RxDownload;

import static com.fong.play.common.Constant.APPINFO;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */

public  abstract  class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenter>
        implements BaseQuickAdapter.RequestLoadMoreListener,AppInfoContract.AppInfoView {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    @Inject
    RxDownload mRxDownload;

    private AppInfoAdapter mAdapter;

    int page =0;

    @Override
    public void init() {
        initRecyclerView();
        mPresenter.requestData(page,type());
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }

    public void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = buildAdapter();//

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mApplication.setCacheView(view);
                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                intent.putExtra(APPINFO,mAdapter.getItem(position));
                startActivity(intent);
            }
        });

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
