package com.fong.play.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.imageloader.ImageLoader;
import com.fong.play.common.utils.DateUtils;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerAppDetailComponent;
import com.fong.play.di.module.AppDetailModule;
import com.fong.play.presenter.AppDetailPresenter;
import com.fong.play.presenter.constract.AppInfoContract;
import com.fong.play.ui.adapter.AppInfoAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;

/**
 * Created by FANGDINGJIE
 * 2018/4/3.
 */

@SuppressLint("ValidFragment")
public class AppDetailFragment extends ProgressFragment<AppDetailPresenter> implements AppInfoContract.AppDetailView {

    @BindView(R.id.view_gallery)
    LinearLayout viewGallery;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.view_introduction)
    ExpandableTextView viewIntroduction;
    @BindView(R.id.txt_update_time)
    TextView txtUpdateTime;
    @BindView(R.id.txt_version)
    TextView txtVersion;
    @BindView(R.id.txt_apk_size)
    TextView txtApkSize;
    @BindView(R.id.txt_publisher)
    TextView txtPublisher;
    @BindView(R.id.txt_publisher2)
    TextView txtPublisher2;
    @BindView(R.id.recycler_view_same_dev)
    RecyclerView recyclerViewSameDev;
    @BindView(R.id.recycler_view_relate)
    RecyclerView recyclerViewRelate;

    private int mAppId;

    private LayoutInflater mInflater;

    private AppInfoAdapter mAdapter;


    @SuppressLint("ValidFragment")
    public AppDetailFragment(int id) {
        this.mAppId = id;
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerAppDetailComponent.builder()
                .appComponent(appComponent)
                .appDetailModule(new AppDetailModule(this))
                .build().inject(this);

    }

    @Override
    public void init() {

        mInflater = LayoutInflater.from(getActivity());
        mPresenter.requestAppInfo(mAppId);

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_app_detail;
    }

    @Override
    public void showResult(AppInfo appInfo) {
        showScreenshot(appInfo.getScreenshot());

        viewIntroduction.setText(appInfo.getIntroduction());

        txtUpdateTime.setText(DateUtils.formatDate(appInfo.getUpdateTime()));
        txtApkSize.setText((appInfo.getApkSize() / 1014 / 1024) + " Mb");
        txtVersion.setText(appInfo.getVersionName());
        txtPublisher.setText(appInfo.getPublisherName());
        txtPublisher2.setText(appInfo.getPublisherName());

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo2)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewSameDev.setLayoutManager(layoutManager);


        mAdapter.addData(appInfo.getSameDevAppInfoList());
        recyclerViewSameDev.setAdapter(mAdapter);

        /////////////////////////////////////////////

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo2)
                .build();

        recyclerViewRelate.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mAdapter.addData(appInfo.getRelateAppInfoList());
        recyclerViewRelate.setAdapter(mAdapter);
    }

    private void showScreenshot(String screentShot) {


        List<String> urls = Arrays.asList(screentShot.split(","));


        for (String url : urls) {

            ImageView imageView = (ImageView) mInflater.inflate(R.layout.template_imageview, viewGallery, false);

            ImageLoader.load(Constant.BASE_IMG_URL + url, imageView);

            viewGallery.addView(imageView);

        }


    }


}
