package com.lxp.widgetview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * 带阻尼的NestedScrollView
 * Created by Li Xiaopeng on 17/12/29.
 */

public class DampNestedScrollView extends NestedScrollView {

    Context mContext;
    private View mView;
    private float touchY;
    private int scrollY = 0;
    private boolean handleStop = false;
    private int eachStep = 0;

    private static final int MAX_SCROLL_HEIGHT = 200;// 最大滑动距离
    private static final float SCROLL_RATIO = 0.4f;// 阻尼系数,越小阻力就越大

    private IDampScrollViewListener mDampScrollViewListener;

    public DampNestedScrollView(Context context) {
        super(context);
        this.mContext = context;
    }

    public DampNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public DampNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            this.mView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchY = ev.getY();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (mView != null) {
            commonOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private int offsetY = 0;

    private void commonOnTouchEvent(@NonNull MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (mView.getScrollY() != 0) {
                    handleStop = true;
                    if (mDampScrollViewListener != null) {
                        if (isNeedMove()
                                && (Math.abs(offsetY) > getHeight() / 6)
                                && mDampScrollViewListener.canAnimat(scrollViewOrientaion)) {
                            moveOutAnimation();
                        } else {
                            dampAnimation();
                        }
                    } else {
                        dampAnimation();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                int deltaY = (int) (touchY - nowY);
                touchY = nowY;
                handleStop = false;
                if (isNeedMove()) {
                    int offset = mView.getScrollY();
                    //                    if (offset < MAX_SCROLL_HEIGHT && offset > -MAX_SCROLL_HEIGHT) {
                    mView.scrollBy(0, (int) (deltaY * SCROLL_RATIO));
                    handleStop = false;
                    offsetY += (int) (deltaY * SCROLL_RATIO);
                    //                    }
                }

                if (deltaY > 0) {
                    scrollViewOrientaion = IDampScrollViewListener.DampScrollViewOrientaion.UP;
                } else if (deltaY < 0) {
                    scrollViewOrientaion = IDampScrollViewListener.DampScrollViewOrientaion.DOWN;
                }

                break;
            default:
                break;
        }
    }

    private boolean isNeedMove() {
        int viewHight = mView.getMeasuredHeight();
        int srollHight = getHeight();
        int offset = viewHight - srollHight;
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    private void animation() {
        scrollY = mView.getScrollY();
        eachStep = scrollY / 10;
        resetPositionHandler.sendEmptyMessage(0);
    }

    @NonNull
    Handler resetPositionHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (scrollY != 0 && handleStop) {
                scrollY -= eachStep;
                if ((eachStep < 0 && scrollY > 0) || (eachStep > 0 && scrollY < 0)) {
                    scrollY = 0;
                }
                mView.scrollTo(0, scrollY);
                this.sendEmptyMessageDelayed(0, 5);
            }
        }

        ;
    };

    private void dampAnimation() {
        scrollY = mView.getScrollY();

        if (scrollY != 0 && handleStop) {
            ValueAnimator animator = ValueAnimator.ofInt(scrollY, 0);
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                    mView.scrollTo(0, (Integer) animation.getAnimatedValue());
                }
            });
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    offsetY = 0;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    private void moveOutAnimation() {

        scrollY = mView.getScrollY();
        if (scrollY != 0 && handleStop && !scrollViewOrientaion.equals(
                IDampScrollViewListener.DampScrollViewOrientaion.NO)) {

            int to = 0;

            if (scrollViewOrientaion.equals(IDampScrollViewListener.DampScrollViewOrientaion.DOWN)) {

                to = -getMeasuredHeight();
            } else if (scrollViewOrientaion.equals(IDampScrollViewListener.DampScrollViewOrientaion.UP)) {

                to = getMeasuredHeight();
            }

            ValueAnimator animator = ValueAnimator.ofInt(scrollY, to);
            animator.setDuration(300);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                    mView.scrollTo(0, (Integer) animation.getAnimatedValue());
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    offsetY = 0;
                    mView.scrollTo(0, 0);
                    mView.setVisibility(INVISIBLE);
                    if (mDampScrollViewListener != null) {
                        mDampScrollViewListener.onPageChange(scrollViewOrientaion);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    public void setChildVisiable(boolean visiable) {
        mView.setVisibility(visiable ? VISIBLE : INVISIBLE);
    }

    public void setmDampScrollViewListener(IDampScrollViewListener mDampScrollViewListener) {
        this.mDampScrollViewListener = mDampScrollViewListener;
    }

    @NonNull
    private IDampScrollViewListener.DampScrollViewOrientaion scrollViewOrientaion =IDampScrollViewListener.DampScrollViewOrientaion.NO;

    public interface IDampScrollViewListener {

        public enum DampScrollViewOrientaion {
            NO, UP, DOWN
        }

        public void onPageChange(DampScrollViewOrientaion orientaion);

        public boolean canAnimat(DampScrollViewOrientaion orientaion);
    }
}
