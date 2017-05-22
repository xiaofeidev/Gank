package com.github.xiaofei_dev.gank.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankSearchResult;
import com.github.xiaofei_dev.gank.model.bean.GankSearchResultBean;
import com.github.xiaofei_dev.gank.presenter.impl.GankSearchActivityPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.SearchResultAdapter;
import com.github.xiaofei_dev.gank.ui.view.GankSearchView;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.ToastUtils;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * author：xiaofei_dev
 * time：2017/4/28:20:28
 * e-mail：xiaofei.dev@gmail.com
 * desc：coding
 */
public final class SearchActivity extends AppCompatActivity implements GankSearchView,GankBaseView,RefreshView {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private int page = 1;
    private static final int PAGE_SIZE = 10;

    protected static final String ARGUMENT = "argument";
    protected String mCategory;

    private int mCurrentCounter = 0;//当前项目数
    private static final int TOTAL_COUNTER = 10000;//最多项目数
    private boolean isErr = true;

    private ArrayList<GankSearchResultBean> gankList = new ArrayList<>();
    private GankSearchActivityPresenterImpl mGankSearchActivityPresenter;
    private SearchResultAdapter mSearchResultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mCategory = getIntent().getStringExtra(ARGUMENT);
        initViews();

        mGankSearchActivityPresenter = new GankSearchActivityPresenterImpl(new CompositeDisposable(),this);
        //mGankSearchActivityPresenter.subscribeSearch(mCategory,PAGE_SIZE,page++);
        firstGetDate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void showRefresh() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        mRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mRefresh != null){
                    mRefresh.setRefreshing(false);
                }
            }
        },500);
    }

    @Override
    public void firstGetDate() {
        page = 1;
        mGankSearchActivityPresenter.subscribeSearch(mCategory,PAGE_SIZE,page++);
    }

    @Override
    public void setSearchInfo(GankSearchResult gankSearchResult) {
        mSearchResultAdapter.setNewData(gankSearchResult.dataList);
        hideRefresh();
    }

    @Override
    public void setLoadMoreErr(boolean err) {
        isErr = err;
    }
    @Override
    public void showNetworkError() {
        hideRefresh();
        ToastUtils.showShort(R.string.network_failure);
    }
    @Override
    public void loadMoreGankData(GankSearchResult gankSearchResult) {
        if(gankSearchResult == null){
            //获取更多数据失败
            loadMoreDateFail();
            return;
        }
        if(isErr){
            gankList = gankSearchResult.dataList;
            if(gankList == null){
                //获取更多数据失败
                loadMoreDateFail();
                return;
            }
            //到了数据尾页
            if(gankList.size()<PAGE_SIZE){
                mCurrentCounter = TOTAL_COUNTER;
                mSearchResultAdapter.loadMoreEnd();
            }
            //获取数据成功
            mSearchResultAdapter.addData(gankList);
            mCurrentCounter = mSearchResultAdapter.getData().size();
            mSearchResultAdapter.loadMoreComplete();
        }else {
            //获取更多数据失败
            loadMoreDateFail();
        }
    }

    /**
     * 获取更多数据失败
     */
    @Override
    public void loadMoreDateFail() {
        ToastUtils.showShort(R.string.failure);
        mSearchResultAdapter.loadMoreFail();
    }

    private void initViews(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(mCategory);
        showRefresh();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initSearchResultAdapter();
        mRecyclerView.setAdapter(mSearchResultAdapter);
        mFab.attachToRecyclerView(mRecyclerView);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstGetDate();
            }
        });
    }

    private  void initSearchResultAdapter(){
        mSearchResultAdapter = new SearchResultAdapter(R.layout.item_search,gankList);
        mSearchResultAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mSearchResultAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if(mCurrentCounter >= TOTAL_COUNTER){
                    //数据全部加载完毕
                    mSearchResultAdapter.loadMoreEnd();
                }else {
                    mGankSearchActivityPresenter.getMoreData(mCategory,PAGE_SIZE,page++);
                }
            }
        },mRecyclerView);
        mSearchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final String url = mSearchResultAdapter.getItem(position).url;
                final String title = mSearchResultAdapter.getItem(position).desc;
                Intent intent = new Intent(SearchActivity.this, SimpleWebActivity.class);
                intent.putExtra("URL", url);
                intent.putExtra("TITLE", title);
                startActivity(intent);
            }
        });
        mSearchResultAdapter.setAutoLoadMoreSize(1);
    }
}
