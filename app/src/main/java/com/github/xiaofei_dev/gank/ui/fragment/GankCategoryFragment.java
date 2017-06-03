package com.github.xiaofei_dev.gank.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankCategory;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.presenter.impl.GankCategoryPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.CategoryAdapter;
import com.github.xiaofei_dev.gank.ui.fragment.call_back_listener.ItemClickListener;
import com.github.xiaofei_dev.gank.ui.view.GankCategoryView;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/10.
 */

public final class GankCategoryFragment extends GankBaseFragment implements GankCategoryView,GankBaseView {

//    public static final String ARGUMENT = "argument";
//    private String mCategory;
    private int page = 1;
    private static final int PAGE_SIZE = 10;

    private int mCurrentCounter = 0;//当前项目数
    private static final int TOTAL_COUNTER = 10000;//最多项目数
    private boolean isErr = true;

    private ArrayList<GankAPI> gankList = new ArrayList<>();
    private GankCategoryPresenterImpl mGankCategoryPresenterImpl;
    private CategoryAdapter mCategoryAdapter;
//    RecyclerView recyclerView;

    private static final String TAG = "GankCategoryFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null)
//            mCategory = bundle.getString(ARGUMENT);\
        initCategoryAdapter();
        mGankCategoryPresenterImpl = new GankCategoryPresenterImpl(new CompositeDisposable(),this);
        //mGankCategoryPresenterImpl.subscribeCategory(mCategory,PAGE_SIZE,page++);//??
        firstGetDate();
        //Log.d(TAG, "onCreate: "+mCategory);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_content,container,false);
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
    public void firstGetDate() {
        page = 1;
        mGankCategoryPresenterImpl.subscribeCategory(mCategory,PAGE_SIZE,page++);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGankCategoryPresenterImpl.unsubscribe();
    }

    @Override
    public void setGankDataInfo(GankCategory gankCategory) {
        //gankList = gankCategory.getDataList();
        mCategoryAdapter.setNewData(gankCategory.getDataList());
        //mCategoryAdapter.notifyDataSetChanged();//??
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
            ToastUtils.showShort(R.string.network_failure);
        }
    }

    @Override
    public void loadMoreGankData(GankCategory gankCategory) {
        if(gankCategory == null){
            //获取更多数据失败
            loadMoreDateFail();
            return;
        }
        if (isErr) {
            gankList = gankCategory.getDataList();
            if(gankList == null){
                //获取更多数据失败
                loadMoreDateFail();
                return;
            }
            //成功获取更多数据
            mCategoryAdapter.addData(gankList);
            mCurrentCounter = mCategoryAdapter.getData().size();
            mCategoryAdapter.loadMoreComplete();
        } else {
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
        mCategoryAdapter.loadMoreFail();
    }

    private void initCategoryAdapter(){
        mCategoryAdapter = new CategoryAdapter(this, R.layout.item_gank_content,
                gankList);
        mCategoryAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mCategoryAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mCurrentCounter >= TOTAL_COUNTER) {
//                            //数据全部加载完毕
//                            mCategoryAdapter.loadMoreEnd();
//                        } else {
//                            mGankCategoryPresenterImpl.getMoreData(mCategory,PAGE_SIZE,page++);
//                            if (isErr) {
//                                //成功获取更多数据
//                                mCategoryAdapter.addData(gankList);
//                                mCurrentCounter = mCategoryAdapter.getData().size();
//                                mCategoryAdapter.loadMoreComplete();
//                            } else {
//                                //获取更多数据失败
//                                Toast.makeText(getActivity(), R.string.failure,
//                                        Toast.LENGTH_LONG).show();
//                                mCategoryAdapter.loadMoreFail();
//
//                            }
//                        }
//                    }
//
//                }, 1000);
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    //数据全部加载完毕
                    mCategoryAdapter.loadMoreEnd();
                } else {
                    mGankCategoryPresenterImpl.getMoreData(mCategory,PAGE_SIZE,page++);
                }
            }
        }, recyclerView);
        mCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final String url = mCategoryAdapter.getItem(position).url;
                final String title = mCategoryAdapter.getItem(position).desc;
                final String desc = ((TextView)(view.findViewById(R.id.person))).getText().toString();
                if(getActivity() instanceof ItemClickListener){
                    ((ItemClickListener) getActivity()).onItemClick(url,title,desc);
                }else {
                    return;//没必要
                }
            }
        });
        mCategoryAdapter.setAutoLoadMoreSize(1);
    }

    public static GankCategoryFragment newInstance(String argument)
    {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        GankCategoryFragment contentFragment = new GankCategoryFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
}
