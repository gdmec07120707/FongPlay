package com.fong.play.ui.fragment;

import android.app.ProgressDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fong.play.R;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerRecommendComponent;
import com.fong.play.di.module.RecommendModule;
import com.fong.play.presenter.RecommendPresenter;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.ui.adapter.IndexMultipleAdapter;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by yechao on 2017/6/7.
 * Describe :
 */

public class RecommendFragment extends ProgressFragment<RecommendPresenter> implements AppInfoContract.View {

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    private IndexMultipleAdapter mAdatper;
    @Inject
    ProgressDialog progressDialog;

    @Override
    public int setLayout() {
        return R.layout.fragment_recommend;
    }


    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerRecommendComponent.builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build().inject(this);
    }

    @Override
    public void init() {
        mPresenter.requestData();
    }

    @Override
    public void showResult(IndexBean data) {
        initRecycleView(data);
        showContentView();
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void showError(String msg) {
      showEmptyView(msg);
    }


    private void initRecycleView(IndexBean data){
        //为RecyclerView设置布局管理器
        rvRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        //为RecyclerView设置分割线(这个可以对DividerItemDecoration进行修改，自定义)
        //rvRecommend.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        //动画
        rvRecommend.setItemAnimator(new DefaultItemAnimator());
        mAdatper = new IndexMultipleAdapter(getActivity());
        mAdatper.setData(data);
        rvRecommend.setAdapter(mAdatper);



    }


}
