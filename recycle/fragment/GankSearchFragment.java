package com.github.xiaofei_dev.gank.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankSearchResult;
import com.github.xiaofei_dev.gank.model.bean.GankSearchResultBean;
import com.github.xiaofei_dev.gank.presenter.impl.GankSearchPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.SearchResultAdapter;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.GankSearchView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/22.
 */

public final class GankSearchFragment extends GankBaseFragment implements GankSearchView,GankBaseView{

    private int page = 1;
    private static final int PAGE_SIZE = 10;

    private int mCurrentCounter = 0;//当前项目数
    private static final int TOTAL_COUNTER = 10000;//最多项目数
    private boolean isErr = true;

    private ArrayList<GankSearchResultBean> gankList = new ArrayList<>();
    private GankSearchPresenterImpl mGankSearchPresenterImpl;
    private SearchResultAdapter mSearchResultAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSearchResultAdapter();
        mGankSearchPresenterImpl = new GankSearchPresenterImpl(new CompositeDisposable(),this);
        mGankSearchPresenterImpl.subscribeSearch(mCategory,PAGE_SIZE,page++);
    }

    @Override
    public void firstGetDate() {
        page = 1;
        mGankSearchPresenterImpl.subscribeSearch(mCategory,PAGE_SIZE,page++);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(mSearchResultAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGankSearchPresenterImpl.unsubscribe();
    }

    @Override
    public void setSearchInfo(GankSearchResult gankSearchResult) {
        mSearchResultAdapter.setNewData(gankSearchResult.dataList);
        Activity activity = getActivity();
        if(activity instanceof RefreshView){
            ((RefreshView) activity).hideRefresh();
        }
    }

    @Override
    public void setLoadMoreErr(boolean err) {
        isErr = err;

    }

    @Override
    public void showNetworkError() {
        Activity activity = getActivity();
        if(activity instanceof RefreshView){
            ((RefreshView) activity).hideRefresh();
        }
        if (getUserVisibleHint()) {
            ToastUtils.showShort(R.string.networkfailure);
        }
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

    private  void initSearchResultAdapter(){
        mSearchResultAdapter = new SearchResultAdapter(R.layout.item_search,gankList);
        mSearchResultAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mSearchResultAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if(mCurrentCounter >= TOTAL_COUNTER){
                    //数据全部加载完毕
                    mSearchResultAdapter.loadMoreEnd();
                }else {
                    mGankSearchPresenterImpl.getMoreData(mCategory,PAGE_SIZE,page++);
                }
            }
        },recyclerView);
        mSearchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final String url = mSearchResultAdapter.getItem(position).url;
                final String title = mSearchResultAdapter.getItem(position).desc;
                if(getActivity() instanceof ItemClickListener){
                    ((ItemClickListener) getActivity()).onItemClick(url,title);
                }else {
                    return;
                }
            }
        });
    }

    public static GankSearchFragment newInstance(String argument){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        GankSearchFragment gankSearchFragment = new GankSearchFragment();
        gankSearchFragment.setArguments(bundle);
        return  gankSearchFragment;
    }
}
