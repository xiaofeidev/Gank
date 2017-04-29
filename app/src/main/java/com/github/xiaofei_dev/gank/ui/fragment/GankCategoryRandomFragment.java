package com.github.xiaofei_dev.gank.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankRandom;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.presenter.impl.GankRandomPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.CategoryAdapter;
import com.github.xiaofei_dev.gank.ui.view.GankRandomView;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/20.
 */

public final class GankCategoryRandomFragment extends GankBaseFragment implements GankRandomView,GankBaseView {

//    public static final String ARGUMENT = "argument";
//    private String mCategory;
    private static final int PAGE_SIZE = 10;

    private final ArrayList<GankAPI> gankList = new ArrayList<>();
    private GankRandomPresenterImpl mGankRandomPresenterImpl;
    private CategoryAdapter mCategoryAdapter;
//    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null)
//            mCategory = bundle.getString(ARGUMENT);
        initCategoryAdapter();
        mGankRandomPresenterImpl = new GankRandomPresenterImpl(new CompositeDisposable(),this);
        //mGankRandomPresenterImpl.subscribeRandom(mCategory,PAGE_SIZE);
        firstGetDate();
    }

    @Override
    public void firstGetDate() {
        mGankRandomPresenterImpl.subscribeRandom(mCategory,PAGE_SIZE);
    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_recycle_content,container,false);
//        recyclerView = (RecyclerView)view.findViewById(R.id.item_content);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(mCategoryAdapter);
//        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        fab.attachToRecyclerView(recyclerView);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerView.smoothScrollToPosition(0);
//            }
//        });
//        return view;
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGankRandomPresenterImpl.unsubscribe();
    }

    @Override
    public void setGankRandomInfo(GankRandom gankRandom) {
        mCategoryAdapter.setNewData(gankRandom.getDataList());
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
    private void initCategoryAdapter(){
        mCategoryAdapter = new CategoryAdapter(this, R.layout.item_gank_content,
                gankList);
        mCategoryAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final String url = mCategoryAdapter.getItem(position).url;
                final String title = mCategoryAdapter.getItem(position).desc;
                if(getActivity() instanceof ItemClickListener){
                    ((ItemClickListener) getActivity()).onItemClick(url,title);
                }else {
                    return;
                }
            }
        });
    }

    @Override
    public void loadMoreDateFail() {
    }

    public static GankCategoryRandomFragment newInstance(String argument){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        GankCategoryRandomFragment gankCategoryRandomFragment = new GankCategoryRandomFragment();
        gankCategoryRandomFragment.setArguments(bundle);
        return gankCategoryRandomFragment;
    }
}
