package com.lxp.widgetview.nested.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.lxp.utils.LogUtils;

/**
 * 弹性ScrollView
 * Created by Li Xiaopeng on 18/1/12.
 */

public class ElasticScrollView extends NestedScrollView {

    private static final String TAG = "ElasticScrollView";

    private float damp = 0.4f;//阻尼系数，越小阻力越大

    private View mView;

    private Context context;
    private float touchY;


    public ElasticScrollView(Context context) {
        super(context);
        init(context);
    }

    public ElasticScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ElasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount()>0){
            mView = getChildAt(0);
        }else{
            LogUtils.logE(TAG, "onFinishInflate: have no view.");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_DOWN){
            touchY = ev.getY();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView!=null){
            dealMove(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void dealMove(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                float deltY = touchY - nowY;
                touchY = nowY;
                mView.scrollBy(0, (int) (deltY*damp));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                doDamp();
                break;
        }
    }

    private void doDamp() {
        int scrollY = mView.getScrollY();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(scrollY,0);
        valueAnimator.setDuration(400);
        valueAnimator.setInterpolator(new DecelerateInterpolator(2));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mView.scrollTo(0, (Integer) animation.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.start();
    }
}
