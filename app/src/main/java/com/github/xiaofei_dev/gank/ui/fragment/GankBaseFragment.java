package com.github.xiaofei_dev.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.xiaofei_dev.gank.R;

/**
 * Created by Administrator on 2017/4/21.
 */

public abstract class GankBaseFragment extends Fragment {

    protected static final String ARGUMENT = "argument";
    protected String mCategory;

    protected RecyclerView recyclerView;
    private static final String TAG = "GankBaseFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategory = bundle.getString(ARGUMENT);
        }
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle_content,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.item_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setAdapter(mDayAdapter);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.smoothScrollToPosition(0);
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


}
