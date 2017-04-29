package com.github.xiaofei_dev.gank.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankRandom;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.presenter.impl.GankRandomPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.MeiZhiAdapter;
import com.github.xiaofei_dev.gank.ui.view.GankRandomView;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.ToastUtils;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/21.
 */

public final class GankMeizhiRandomFragment extends GankBaseFragment implements GankRandomView ,GankBaseView{

    private int page = 1;
    private static final int PAGE_SIZE = 10;

    private final ArrayList<GankAPI> gankList = new ArrayList<>();
    private GankRandomPresenterImpl mGankRandomPresenterImpl;
    private MeiZhiAdapter mMeiZhiAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMeiZhiAdapter();
        mGankRandomPresenterImpl = new GankRandomPresenterImpl(new CompositeDisposable(),this);
        //mGankRandomPresenterImpl.subscribeRandom(mCategory,PAGE_SIZE);
        firstGetDate();
    }

    @Override
    public void firstGetDate() {
        mGankRandomPresenterImpl.subscribeRandom(mCategory,PAGE_SIZE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizhi_recycle_content,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.item_content);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMeiZhiAdapter);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(mMeiZhiAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGankRandomPresenterImpl.unsubscribe();
    }

    @Override
    public void setGankRandomInfo(GankRandom gankRandom) {
        mMeiZhiAdapter.setNewData(gankRandom.getDataList());
        Activity activity = getActivity();
        if(activity instanceof RefreshView){
            ((RefreshView) activity).hideRefresh();
        }
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

    private void initMeiZhiAdapter(){
        mMeiZhiAdapter = new MeiZhiAdapter(this, R.layout.item_meizhi,gankList);
        //mMeiZhiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mMeiZhiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final String url = mMeiZhiAdapter.getItem(position).url;
                //final Drawable drawable = ((ImageView)view.findViewById(R.id.meizhi)).getDrawable();//
                if(getActivity() instanceof MeiZhiItemClickListener){
                    ((MeiZhiItemClickListener)getActivity()).onMeiZhiItemClick(url,view);
                }
            }
        });
    }

    @Override
    public void loadMoreDateFail() {

    }

    public static GankMeizhiRandomFragment newInstance(String argument){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        GankMeizhiRandomFragment gankMeizhiRandomFragment = new GankMeizhiRandomFragment();
        gankMeizhiRandomFragment.setArguments(bundle);
        return gankMeizhiRandomFragment;
    }
}
