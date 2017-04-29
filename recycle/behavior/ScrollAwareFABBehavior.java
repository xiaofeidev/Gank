package com.github.xiaofei_dev.gank.ui.behavior;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by Administrator on 2017/4/17.
 */

public class ScrollAwareFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private static final android.view.animation.Interpolator INTERPOLATOR=new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut=false;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs){
        super();
    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        //处理垂直方向上的滚动事件  
        return nestedScrollAxes== ViewCompat.SCROLL_AXIS_VERTICAL|| super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        //向上滚动进入，向下滚动隐藏  
        if (dyConsumed>0&&!this.mIsAnimatingOut && child.getVisibility()==View.VISIBLE){
            //如果是展开的话就先收回去  
           /* if (child.isExpanded()){
                child.collapse();
            }*/
            //animateOut()和animateIn()都是私有方法，需要重新实现  
            animateOut(child);
        } else if (dyConsumed<0 && child.getVisibility()!=View.VISIBLE){
            animateIn(child);
        }
    }

    private void animateOut(final FloatingActionButton button){
        ViewCompat.animate(button).translationY(500)
                .setInterpolator(INTERPOLATOR).withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut=true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut=false;
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut=false;

                    }
                }).start();
    }

    private void animateIn(FloatingActionButton button){
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button).translationY(0)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start();
    }
}  
