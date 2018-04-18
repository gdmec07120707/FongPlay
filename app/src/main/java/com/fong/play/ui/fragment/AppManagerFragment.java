package com.fong.play.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fong.play.R;
import com.fong.play.presenter.AppManagerPresenter;
import com.fong.play.presenter.constract.AppManagerContract;
import com.fong.play.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import zlc.season.rxdownload2.entity.DownloadRecord;

public abstract class AppManagerFragment extends ProgressFragment<AppManagerPresenter> implements AppManagerContract.AppManagerView {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;


    @Override
    public void init() {
        initRecyclerView();
    }

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }


    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(setAdapter());
    }


    @Override
    public void showDownloading(List<DownloadRecord> downloadRecords) {

    }

    protected abstract RecyclerView.Adapter setAdapter();
}
