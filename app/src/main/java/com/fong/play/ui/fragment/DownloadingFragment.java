package com.fong.play.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fong.play.R;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppManagerComponent;
import com.fong.play.di.module.AppManagerModule;
import com.fong.play.presenter.AppManagerPresenter;
import com.fong.play.presenter.constract.AppManagerContract;
import com.fong.play.ui.adapter.AppManagerAdapter;
import com.fong.play.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import zlc.season.rxdownload2.entity.DownloadRecord;


public class DownloadingFragment extends ProgressFragment<AppManagerPresenter> implements AppManagerContract.AppManagerView{


    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private AppManagerAdapter mAdapter;



    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerAppManagerComponent.builder()
                .appComponent(appComponent)
                .appManagerModule(new AppManagerModule(this))
                .build().injectDownloading(this);

    }

    @Override
    public void init() {

        mPresenter.getDownloadingApps();


    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = new AppManagerAdapter(mPresenter.getRxDownlad());

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }


    @Override
    public void showDownloading(List<DownloadRecord> downloadRecords) {
        initRecyclerView();
        mAdapter.addData(downloadRecords);
    }
}
