package com.fong.play.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fong.play.AppApplication;
import com.fong.play.R;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerRecommendComponent;
import com.fong.play.di.module.RecommendModule;
import com.fong.play.presenter.RecommendPresenter;
import com.fong.play.presenter.constract.RecommendContract;
import com.fong.play.ui.adapter.RecomendAppAdatper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by yechao on 2017/6/7.
 * Describe :
 */

public class RecommendFragment extends ProgressFragment<RecommendPresenter> implements RecommendContract.View {

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    private RecomendAppAdatper mAdatper;
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
    public void showResult(List<AppInfo> data) {
        showContentView();
        initRecycleView( data);
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void showError(String msg) {
       showError(msg);
    }

    @Override
    public void showEmpty() {
        showEmptyView("暂无数据");
    }

    private void initRecycleView(List<AppInfo> datas){

        //为RecyclerView设置布局管理器
        rvRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));

        //为RecyclerView设置分割线(这个可以对DividerItemDecoration进行修改，自定义)
        //rvRecommend.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        //动画
        rvRecommend.setItemAnimator(new DefaultItemAnimator());

        mAdatper = new RecomendAppAdatper(getActivity(),datas);
        rvRecommend.setAdapter(mAdatper);



    }


}
