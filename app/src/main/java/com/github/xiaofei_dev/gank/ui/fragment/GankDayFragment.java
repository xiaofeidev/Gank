package com.github.xiaofei_dev.gank.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankDay;
import com.github.xiaofei_dev.gank.model.bean.GankDayBean;
import com.github.xiaofei_dev.gank.presenter.impl.GankDayPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.DayAdapter;
import com.github.xiaofei_dev.gank.ui.fragment.call_back_listener.ItemClickListener;
import com.github.xiaofei_dev.gank.ui.view.GankDayView;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.DateUtils;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/19.
 */

public final class GankDayFragment extends GankBaseFragment implements GankDayView,GankBaseView {

    private int mCurrentCounter = 0;//当前项目数
    private static final int TOTAL_COUNTER = 10000;//最多项目数
    private boolean isErr = true;

    private ArrayList<GankDayBean> gankList = new ArrayList<>();
    private GankDayPresenterImpl mGankDayPresenterImpl;
    private DayAdapter mDayAdapter;
//    RecyclerView recyclerView;
    private int count = 0;
    private static final String TAG = "GankDayFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGankItemAdapter();
        mGankDayPresenterImpl = new GankDayPresenterImpl(new CompositeDisposable(),this);
        //mGankDayPresenterImpl.subscribeDay(DateUtils.toWorkDate(DateUtils.getCurrentDate(),-- count));
        firstGetDate();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_content,container,false);
//        recyclerView = (RecyclerView)view.findViewById(R.id.item_content);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(mDayAdapter);
//        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        fab.attachToRecyclerView(recyclerView);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerView.smoothScrollToPosition(0);
//                //(MaterialSearchView)(getActivity().findViewById(R.id.search_view))
//            }
//        });
//        return view;
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(mDayAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGankDayPresenterImpl.unsubscribe();
        //mDayAdapter.mDa
        Log.d(TAG, "onDestroy: ");
    }

    private void initGankItemAdapter(){
        /*mDayAdapter = new SectionAdapter(R.layout.item_gank_content,R.layout.item_gank_category,
                gankList,this);*/
        mDayAdapter = new DayAdapter(this,gankList);
        mDayAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mDayAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                //Log.d(TAG, "onLoadMoreRequested: ");
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    //数据全部加载完毕
                    mDayAdapter.loadMoreEnd();
                } else {
                    getMoreData();
                }
            }
        }, recyclerView);
        mDayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getTag() != null){
                    final String url = mDayAdapter.getItem(position).mGankAPI.url;
                    final String title = mDayAdapter.getItem(position).mGankAPI.desc;
                    final String desc = ((TextView)(view.findViewById(R.id.person))).getText().toString();
                    if(getActivity() instanceof ItemClickListener){
                        ((ItemClickListener) getActivity()).onItemClick(url,title,desc);
                    }else {
                        return;
                    }

                }
            }
        });
        mDayAdapter.setAutoLoadMoreSize(1);
    }

    public int getCount() {
        return count;
    }

    public void setCount() {
        count -= 1;
    }

    @Override
    public void reGetData() {
        mGankDayPresenterImpl.subscribeDay(DateUtils.toWorkDate(DateUtils.getCurrentDate(),this));
    }

    @Override
    public void firstGetDate() {
        count = 0;//??
        mGankDayPresenterImpl.subscribeDay(DateUtils.toWorkDate(DateUtils.getCurrentDate(),this));
    }

    @Override
    public void getMoreData() {
        mGankDayPresenterImpl.getMoreData(DateUtils.toWorkDate(DateUtils.getCurrentDate(),this));

    }



    @Override
    public void setGankDayInfo(GankDay gankDay) {
        gankList = gankDay.getMultiGankData();
        mDayAdapter.setNewData(gankList);
        Activity activity = getActivity();
        if(activity instanceof RefreshView){
            ((RefreshView) activity).hideRefresh();
        }
    }

    @Override
    public void loadMoreGankData(GankDay gankDay) {
        if(gankDay == null){
            loadMoreDateFail();
            return;
        }
        if(isErr){
            gankList = gankDay.getMultiGankData();
            if(gankList == null){
                loadMoreDateFail();
                return;
            }
            //成功获取更多数据
            mDayAdapter.addData(gankList);
            mCurrentCounter = mDayAdapter.getData().size();
            mDayAdapter.loadMoreComplete();
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
        Toast.makeText(getActivity(), R.string.failure,
                Toast.LENGTH_LONG).show();
        mDayAdapter.loadMoreFail();
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
            ToastUtils.showShort(R.string.network_failure);
        }
    }
}