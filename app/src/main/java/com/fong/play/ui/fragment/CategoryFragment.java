package com.fong.play.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.data.bean.Category;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerCategoryComponent;
import com.fong.play.di.module.CategoryModule;
import com.fong.play.presenter.CategoryPresenter;
import com.fong.play.presenter.constract.CategoryContract;
import com.fong.play.ui.activity.CategoryAppActivity;
import com.fong.play.ui.adapter.CategoryAdapter;
import com.fong.play.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yechao on 2017/6/7.
 * Describe :
 */

public class CategoryFragment extends ProgressFragment<CategoryPresenter> implements CategoryContract.CategoryView {


    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private CategoryAdapter mAdapter;

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerCategoryComponent.builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build().inject(this);

    }

    @Override
    public void init() {
        initRecyclerView();
        mPresenter.requestData();
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }


    @Override
    public void showResult(List<Category> data) {
        mAdapter.addData(data);
    }

    private void initRecyclerView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()) );

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);

        mRecycleView.addItemDecoration(itemDecoration);
        mAdapter = new CategoryAdapter();

        mRecycleView.setAdapter(mAdapter);

        mRecycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CategoryAppActivity.class);
                intent.putExtra(Constant.CATEGORY,mAdapter.getData().get(position));
                startActivity(intent);
            }
        });

    }
}
