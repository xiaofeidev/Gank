package com.github.xiaofei_dev.gank.ui.fragment.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.xiaofei_dev.gank.R;

/**
 * Created by Administrator on 2017/4/23.
 */

public class MeizhiFragment extends Fragment {

    private static final String URL= "argument1";
    private String mURL;
    private ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mURL = bundle.getString(URL);
        }
        setSharedElementEnterTransition(
                TransitionInflater.from(getContext())
                        .inflateTransition(android.R.transition.move));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_simple_meizhi,container,false);///false?
        mImageView = (ImageView)view.findViewById(R.id.meizhi);
        Glide.with(this)
                .load(mURL)
                .asBitmap()
                .fitCenter()
                .skipMemoryCache(true)
                .dontAnimate()
                //.thumbnail( 0.1f )
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                //.placeholder(R.mipmap.ic_launcher)
                //.centerCrop()
                .into(mImageView);
//                .getSize(new SizeReadyCallback() {
//                    @Override
//                    public void onSizeReady(int width, int height) {
//                        startPostponedEnterTransition();
//                    }
//                });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static MeizhiFragment newInstance(String url){
        Bundle bundle = new Bundle();
        bundle.putString(URL,url);
        MeizhiFragment meizhiFragment = new MeizhiFragment();
        meizhiFragment.setArguments(bundle);
        return meizhiFragment;
    }
}
