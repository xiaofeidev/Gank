package com.github.xiaofei_dev.gank.ui.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.greenDAO.VisitDao;
import com.github.xiaofei_dev.gank.greenDAO.bean.Collect;
import com.github.xiaofei_dev.gank.ui.adapter.CollectionAdapter;
import com.github.xiaofei_dev.gank.util.ToastUtils;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class CollectionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private List<Collect> mCollectionArrayList = new ArrayList<>();
    private CollectionAdapter mCollectionAdapter;

    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        mCollectionArrayList = VisitDao.queryAll();
        Collections.reverse(mCollectionArrayList);
        mCollectionAdapter.setNewData(mCollectionArrayList);
        super.onResume();
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

    private void initView(){
        mRefresh.setRefreshing(true);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initCollectionAdapter();
        mRecyclerView.setAdapter(mCollectionAdapter);
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
                mRefresh.setRefreshing(false);
            }
        });
        mRefresh.setRefreshing(false);
    }

    private void initCollectionAdapter(){
//        mCollectionArrayList = VisitDao.queryAll();
//        Collections.reverse(mCollectionArrayList);
        mCollectionAdapter = new CollectionAdapter(R.layout.item_search,mCollectionArrayList);
        mCollectionAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        mCollectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final String url = mCollectionAdapter.getItem(position).getUrl();
                final String title = mCollectionAdapter.getItem(position).getTitle();
                final String desc = mCollectionAdapter.getItem(position).getDesc();
                Intent intent = SimpleWebActivity.newIntent(CollectionActivity.this,url,title,desc);
                startActivity(intent);
            }
        });

        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mCollectionAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        mCollectionAdapter.enableSwipeItem();
        mCollectionAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Collect collect = mCollectionArrayList.get(pos);
                VisitDao.deleteCollection(collect);
                ToastUtils.showShort(R.string.delete_done);

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        mCollectionAdapter.setEmptyView(R.layout.collection_empty,(ViewGroup) mRecyclerView.getParent());

    }
}
