package com.github.xiaofei_dev.gank.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.GankCategory;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.presenter.impl.GankCategoryPresenterImpl;
import com.github.xiaofei_dev.gank.ui.adapter.MeiZhiAdapter;
import com.github.xiaofei_dev.gank.ui.fragment.call_back_listener.MeiZhiItemClickListener;
import com.github.xiaofei_dev.gank.ui.view.GankCategoryView;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/4/17.
 */

public final class GankMeiZhiFragment extends GankBaseFragment implements GankCategoryView,GankBaseView {
//    public static final String ARGUMENT = "argument";
//    private String mCategory;
    private int page = 1;
    private static final int PAGE_SIZE = 10;

    private int mCurrentCounter = 0;//当前项目数
    private static final int TOTAL_COUNTER = 10000;//最多项目数
    private boolean isErr = true;

    private ArrayList<GankAPI> gankList = new ArrayList<>();
    private GankCategoryPresenterImpl mGankCategoryPresenter;
    private MeiZhiAdapter mMeiZhiAdapter;

    private static final String TAG = "GankMeiZhiFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null)
//            mCategory = bundle.getString(ARGUMENT);

        mGankCategoryPresenter = new GankCategoryPresenterImpl(new CompositeDisposable(),this);
        //mGankCategoryPresenter.subscribeCategory(mCategory,PAGE_SIZE,page++);//??
        firstGetDate();
        //Log.d(TAG, "onCreate: "+mCategory);
    }
    @Override
    public void firstGetDate() {
        page = 1;
        mGankCategoryPresenter.subscribeCategory(mCategory,PAGE_SIZE,page++);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizhi_recycle_content,container,false);
        //view.setPadding(15,15,15,15);//??
        recyclerView = (RecyclerView)view.findViewById(R.id.item_content);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        initMeiZhiAdapter();
        recyclerView.setAdapter(mMeiZhiAdapter);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        fab.attachToRecyclerView(recyclerView);
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
        //mImageView = (ImageView)getView().findViewById(R.id.meizhi);//??
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGankCategoryPresenter.unsubscribe();

        mMeiZhiAdapter.clearData();
        mMeiZhiAdapter = null;
        gankList.clear();
        System.gc();

        //Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void setLoadMoreErr(boolean err) {
        isErr = err;
    }

    @Override
    public void setGankDataInfo(GankCategory gankCategory) {
        //gankList = gankCategory.getDataList();
        mMeiZhiAdapter.setNewData(gankCategory.getDataList());
        Activity activity = getActivity();
        if(activity instanceof RefreshView){
            ((RefreshView) activity).hideRefresh();
        }
        //mMeiZhiAdapter.notifyDataSetChanged();//??
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

    private void initMeiZhiAdapter(){
        mMeiZhiAdapter = new MeiZhiAdapter(this,R.layout.item_meizhi,gankList);
        //mMeiZhiAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mMeiZhiAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    //数据全部加载完毕
                    mMeiZhiAdapter.loadMoreEnd();
                } else {
                    mGankCategoryPresenter.getMoreData(mCategory,PAGE_SIZE,page++);
                }
            }
        }, recyclerView);
        mMeiZhiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //mImageView = (ImageView)getView().findViewById(R.id.meizhi);//??
                final String url = mMeiZhiAdapter.getItem(position).url;
                //final Drawable drawable = ((ImageView)view.findViewById(R.id.meizhi)).getDrawable();//
//                MeizhiFragment meizhiFragment =  (MeizhiFragment) getFragmentManager().findFragmentByTag(TAG);
//                if(meizhiFragment == null) meizhiFragment = MeizhiFragment.newInstance(str);
//                getFragmentManager()
//                        .beginTransaction()
//                        .addSharedElement(mImageView, ViewCompat.getTransitionName(mImageView))
//                        .replace(R.id.container, meizhiFragment)
//                        .addToBackStack(TAG)
//                        .commit();
                if(getActivity() instanceof MeiZhiItemClickListener){
                    ((MeiZhiItemClickListener)getActivity()).onMeiZhiItemClick(url,view);
                }
            }
        });
    }

//    //回调接口，由托管Activity实现
//    public interface MeiZhiItemClickListener {
//        void onMeiZhiItemClick(String url, View view);
//    }

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
                loadMoreDateFail();
                return;
            }
            //成功获取更多数据
            mMeiZhiAdapter.addData(gankList);
            //mMeiZhiAdapter.setNewData(gankList);//每次都用新数据更换原始数据而不是添加，这样可节省内存
            mCurrentCounter = mMeiZhiAdapter.getData().size();
            mMeiZhiAdapter.loadMoreComplete();
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
        mMeiZhiAdapter.loadMoreFail();
    }

    public static GankMeiZhiFragment newInstance(String argument)
    {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        GankMeiZhiFragment contentFragment = new GankMeiZhiFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

}
