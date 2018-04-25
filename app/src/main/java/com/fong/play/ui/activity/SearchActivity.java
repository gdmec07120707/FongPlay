package com.fong.play.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fong.play.AppApplication;
import com.fong.play.R;
import com.fong.play.common.rx.RxSchedulers;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.data.bean.SearchResult;
import com.fong.play.di.component.AppComponent;
import com.fong.play.di.component.DaggerSearchComponent;
import com.fong.play.di.module.SearchModule;
import com.fong.play.presenter.SearchPresenter;
import com.fong.play.presenter.constract.SearchContract;
import com.fong.play.ui.adapter.AppInfoAdapter;
import com.fong.play.ui.adapter.SearchHistoryAdatper;
import com.fong.play.ui.adapter.SuggestionAdapter;
import com.fong.play.ui.widget.DividerItemDecoration;
import com.fong.play.ui.widget.SpaceItemDecoration2;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import zlc.season.rxdownload2.RxDownload;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.SearchView {

    @BindView(R.id.searchTextView)
    EditText mSearchTextView;
    @BindView(R.id.action_clear_btn)
    ImageView mActionClearBtn;

    @BindView(R.id.search_bar)
    RelativeLayout mSearchBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.btn_clear)
    ImageView mBtnClear;

    @BindView(R.id.recycler_view_history)
    RecyclerView mRecyclerViewHistory;

    @BindView(R.id.layout_history)
    LinearLayout mLayoutHistory;

    @BindView(R.id.recycler_view_suggestion)
    RecyclerView mRecyclerViewSuggestion;

    @BindView(R.id.recycler_view_result)
    RecyclerView mRecyclerViewResult;

    @BindView(R.id.activity_search)
    LinearLayout mActivitySearch;


    private SearchHistoryAdatper mHistoryAdapter;
    private SuggestionAdapter mSuggestionAdapter;
    private AppInfoAdapter mAppInfoAdapter;

    private Disposable disposable;

    @Inject
    RxDownload rxDownload;

    protected AppApplication mApplication;

    @Override
    protected int setLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchComponent.builder( )
                .appComponent( appComponent )
                .searchModule( new SearchModule( this ) )
                .build( ).inject( this );

    }

    @Override
    protected void init() {
        this.mApplication = (AppApplication)getApplication();

        mPresenter.showHistory( );

        initView( );

        setupSearchView( );

        setupSuggestionRecyclerView();

        initSearchResultRecycleView();

    }


    private void initView() {
        mToolbar.setNavigationIcon(
                new IconicsDrawable( this )
                        .icon( Ionicons.Icon.ion_ios_arrow_back )
                        .color( ContextCompat.getColor( this,R.color.white ) )
                        .sizeDp( 16 )
        );

        mToolbar.setNavigationOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish( );
            }
        } );

        mActionClearBtn.setImageDrawable( new IconicsDrawable( this )
                .icon( Ionicons.Icon.ion_ios_close_empty )
                .color( ContextCompat.getColor( this,R.color.white ) )
                .sizeDp( 16 )
        );

        mBtnClear.setImageDrawable( new IconicsDrawable( this )
                .icon( Ionicons.Icon.ion_ios_trash_outline )
                .color( ContextCompat.getColor( this,R.color.md_grey_600 ) )
                .sizeDp( 16 )
        );
    }


    @SuppressLint("CheckResult")
    private void setupSearchView() {
        RxView.clicks(mActionClearBtn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                mSearchTextView.setText("");
                mLayoutHistory.setVisibility(View.VISIBLE);
                mRecyclerViewSuggestion.setVisibility(View.GONE);
                mRecyclerViewResult.setVisibility(View.GONE);
            }
        });

        RxTextView.editorActions(mSearchTextView).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                search(mSearchTextView.getText().toString().trim());
            }
        });

        disposable = RxTextView.textChanges(mSearchTextView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.<CharSequence>io_main())
                .filter(new Predicate<CharSequence>(){
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return charSequence.toString().trim().length()>0;
                    }
                })
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if(charSequence.length()>0){
                            mActionClearBtn.setVisibility(View.VISIBLE);
                        }else{
                            mActionClearBtn.setVisibility(View.GONE);
                        }
                        mPresenter.getSuggestions(charSequence.toString().trim());
                    }
                });
    }


    @Override
    public void showSearchHistory(List<String> list) {
        initHistoryRecyclerView( list );
        mRecyclerViewSuggestion.setVisibility(View.GONE);
        mLayoutHistory.setVisibility(View.VISIBLE);
        mRecyclerViewResult.setVisibility(View.GONE);
    }

    @Override
    public void showSuggestions(List<String> list) {
        mSuggestionAdapter.setNewData(list);
        mRecyclerViewSuggestion.setVisibility(View.VISIBLE);
        mLayoutHistory.setVisibility(View.GONE);
        mRecyclerViewResult.setVisibility(View.GONE);

    }

    @Override
    public void showSearchResult(SearchResult result) {
        mAppInfoAdapter.setNewData(result.getListApp());
        mRecyclerViewSuggestion.setVisibility(View.GONE);
        mLayoutHistory.setVisibility(View.GONE);
        mRecyclerViewResult.setVisibility(View.VISIBLE);
    }


    /**
     * 初始化历史纪录列表
     * @param list
     */
    private void initHistoryRecyclerView(List<String> list) {
        mHistoryAdapter = new SearchHistoryAdatper( );
        mHistoryAdapter.addData( list );
        RecyclerView.LayoutManager lm = new GridLayoutManager( this,5 );
        SpaceItemDecoration2 itemd = new SpaceItemDecoration2( 10 );
        mRecyclerViewHistory.addItemDecoration( itemd );

        mRecyclerViewHistory.setLayoutManager( lm );
        mRecyclerViewHistory.setAdapter( mHistoryAdapter );

        mRecyclerViewHistory.addOnItemTouchListener( new OnItemChildClickListener( ) {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter,View view,int position) {

                search(mHistoryAdapter.getItem(position));
            }
        } );
    }


    /**
     *初始化提示列表
     */
    private void setupSuggestionRecyclerView() {
        mSuggestionAdapter = new SuggestionAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewSuggestion.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerViewSuggestion.addItemDecoration(itemDecoration);
        mRecyclerViewSuggestion.setAdapter(mSuggestionAdapter);

        mRecyclerViewSuggestion.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                search(mSuggestionAdapter.getItem(position));
            }
        });

    }

    /**
     * 初始化搜索结果
     */
    private void initSearchResultRecycleView(){
        mAppInfoAdapter = AppInfoAdapter.builder().showBrief(false).showCategoryName(true).rxDownload(rxDownload).build();

        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        mRecyclerViewResult.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
        mRecyclerViewResult.addItemDecoration(itemDecoration);

        mRecyclerViewResult.setAdapter(mAppInfoAdapter);

        mRecyclerViewResult.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                AppInfo appInfo = mAppInfoAdapter.getItem(position);
                mApplication.setCacheView(view);
                Intent intent  = new Intent(SearchActivity.this, AppDetailActivity.class);
                intent.putExtra("appinfo",appInfo);
                startActivity(intent);
            }
        });
    }


    private void search(String keyword){
        mPresenter.search(keyword);
    }
}
