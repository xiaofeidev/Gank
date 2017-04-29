package com.github.xiaofei_dev.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankDay;
import com.github.xiaofei_dev.gank.model.bean.MySection;
import com.github.xiaofei_dev.gank.presenter.impl.GankDayPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.SectionAdapter;
import com.github.xiaofei_dev.gank.ui.view.GankDayView;
import com.github.xiaofei_dev.gank.util.DateUtils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by Administrator on 2017/4/3.
 */

public final class MainFragment extends Fragment implements GankDayView {

    private int mCurrentCounter = 0;//当前项目数
    private static final int TOTAL_COUNTER = 100;//最多项目数
    private boolean isErr = true;

    private ArrayList<MySection> gankList = new ArrayList<>();
    private GankDayPresenterImpl mGankDayPresenterImpl;
    SectionAdapter mGankItemAdapter;
    RecyclerView recyclerView;
    private int count = 1;
    private static final String TAG = "MainFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGankItemAdapter();
        mGankDayPresenterImpl = new GankDayPresenterImpl(new CompositeDisposable(),this);
        //mGankDayPresenterImpl.subscribeDay(DateUtils.toWorkDate(DateUtils.getCurrentDate(),-- count));
        getData();
    }

    private void initGankItemAdapter(){
        mGankItemAdapter = new SectionAdapter(R.layout.item_gank_content,R.layout.item_gank_category,
                gankList,this);
        mGankItemAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mGankItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                //Log.d(TAG, "onLoadMoreRequested: ");
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    //数据全部加载完毕
                    mGankItemAdapter.loadMoreEnd();
                } else {
                    getMoreData();
                }
            }
        }, recyclerView);
    }

    @Override
    public void getData() {
        mGankDayPresenterImpl.subscribeDay(DateUtils.toWorkDate(DateUtils.getCurrentDate(),-- count));
    }

    @Override
    public void getMoreData() {
        mGankDayPresenterImpl.getMoreData(DateUtils.toWorkDate(DateUtils.getCurrentDate(),-- count));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.item_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mGankItemAdapter);
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGankDayPresenterImpl.unsubscribe();
        //mGankItemAdapter.mDa
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void setGankDayInfo(GankDay gankDay) {
        gankList = GankDay.getSctionData(gankDay.getResults());
        mGankItemAdapter.setData(gankList);
        mGankItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreGankData(GankDay gankDay) {
        if(isErr){
            //成功获取更多数据
            gankList = GankDay.getSctionData(gankDay.getResults());
            mGankItemAdapter.addData(gankList);
            mCurrentCounter = mGankItemAdapter.getData().size();
            mGankItemAdapter.loadMoreComplete();
        }else {
            //获取更多数据失败
            Toast.makeText(getActivity(), R.string.failure,
                    Toast.LENGTH_LONG).show();
            mGankItemAdapter.loadMoreFail();
        }

    }

    @Override
    public void setLoadMoreErr(boolean err) {
        isErr = err;
    }

    @Override
    public void showNetworkError() {

    }
}
