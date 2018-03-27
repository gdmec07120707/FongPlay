package com.fong.play.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fong.play.AppApplication;
import com.fong.play.R;
import com.fong.play.di.component.AppComponent;
import com.fong.play.presenter.BasePresenter;
import com.fong.play.ui.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by FANGDINGJIE
 * 2018/3/27.
 */

public abstract class ProgressFragment<T extends BasePresenter> extends Fragment implements BaseView {

    private FrameLayout mRootView;

    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
    private Unbinder mUnbinder;
    private TextView mTextError;
    private AppApplication mApplication;


    @Inject
    T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (FrameLayout) inflater.inflate(R.layout.fragment_progress, container, false);

        mViewProgress = mRootView.findViewById(R.id.view_progress);
        mViewContent = (FrameLayout) mRootView.findViewById(R.id.view_content);
        mViewEmpty = mRootView.findViewById(R.id.view_empty);

        mTextError = (TextView) mRootView.findViewById(R.id.text_tip);

        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClick();
            }
        });
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mApplication = (AppApplication) getActivity().getApplication();
        setupAcitivtyComponent(mApplication.getAppComponent());
        setRealContentView();

        init();
    }

    public void onEmptyViewClick() {

    }

    private void setRealContentView() {
        View realContentView = LayoutInflater.from(getActivity()).inflate(setLayout(), mViewContent, true);
        mUnbinder = ButterKnife.bind(this, realContentView);
    }

    public abstract void setupAcitivtyComponent(AppComponent appComponent);

    public abstract void init();

    public abstract int setLayout();

    /**
     * 显示进度
     */
    public void showProgressView() {
        showView(R.id.view_progress);
    }

    /**
     * 显示内容
     */
    public void showContentView() {
        showView(R.id.view_content);
    }

    /**
     * 显示空布局
     */
    public void showEmptyView() {
        showView(R.id.view_empty);
    }

    /**
     * 显示空布局，自定义文字
     */
    public void showEmptyView(int resId) {
        showEmptyView();
        mTextError.setText(resId);
    }

    /**
     * 显示空布局，自定义文字
     */
    public void showEmptyView(String msg) {
        showEmptyView();
        mTextError.setText(msg);
    }

    /**
     * 遍历布局，判断显示的内容
     * @param viewId
     */
    public void showView(int viewId) {
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            if (mRootView.getChildAt(i).getId() == viewId) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void showError(String msg) {

        showEmptyView(msg);
    }

    @Override
    public void dismissLoading() {
        showContentView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
