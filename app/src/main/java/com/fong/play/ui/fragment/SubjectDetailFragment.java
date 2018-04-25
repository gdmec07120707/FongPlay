package com.fong.play.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.imageloader.ImageLoader;
import com.fong.play.data.bean.Subject;
import com.fong.play.data.bean.SubjectDetail;
import com.fong.play.ui.adapter.AppInfoAdapter;
import com.fong.play.ui.fragment.base.BaseSubjectFragment;
import com.fong.play.ui.widget.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import zlc.season.rxdownload2.RxDownload;

@SuppressLint("ValidFragment")
public class SubjectDetailFragment extends BaseSubjectFragment {
    @BindView(R.id.imageview)
    ImageView mImageView;
    @BindView(R.id.txt_desc)
    TextView mtxtDesc;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Subject mSubject;

    private AppInfoAdapter mAdapter;

    @Inject
    RxDownload mRxDownload;

    public SubjectDetailFragment(Subject subject){
        this.mSubject = subject;
    }

    @Override
    public void init() {

        initRecycleView();
        mPresenter.getSubjectDetail(mSubject.getRelatedId());

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_subject_detail;
    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {
        ImageLoader.load( Constant.BASE_IMG_URL + detail.getPhoneBigIcon(),mImageView);

        mtxtDesc.setText(detail.getDescription());

        mAdapter.addData(detail.getListApp());
    }

    private void initRecycleView() {

        mAdapter = AppInfoAdapter.builder().showBrief(false).showCategoryName(true)
                .rxDownload(mRxDownload).build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setAdapter(mAdapter);

    }
}
