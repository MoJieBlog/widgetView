package com.lxp.widgetview.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;

import com.lxp.widgetview.R;

/**
 * 自定义的刷新控件，仿照SwipeRefreshLayout改写
 * 分析：
 * 1.刷新控件
 *      统一添加在控件的上方
 * 2.加载控件
 *      对于recyclerView,listView等添加在adapter显示条目的下方，
 * <p>
 * Created by Li Xiaopeng on 18/1/5.
 */

public class LSwipeRefreshLayout extends ViewGroup implements NestedScrollingParent,
        NestedScrollingChild {

    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;//这个值越到，前期快的时间越久
    private static final int DEFAULT_TYPE = 0;//默认的模式
    private static final int INVALID_POINTER = -1;

    //在可滑动的控件中用于区别单击子控件和滑动操作的一个伐值。
    private int mTouchSlop;
    //先快后慢的动画插值器
    private final DecelerateInterpolator mDecelerateInterpolator;

    //父布局和子空间的嵌套滚动处理helper
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;

    private int headType = DEFAULT_TYPE;
    private int footType = DEFAULT_TYPE;

    private LoadingLayout headLoadView;
    private LoadingLayout footLoadView;

    private OnRefreshListener mRefreshListener;
    private OnLoadMoreListener mLoadMoreListener;

    private boolean freshing = false;
    private boolean loading = false;

    /**
     * the target of the gesture(官方解释) 个人理解为刷新的控件（listView，recyclerView...）
     */
    private View mTarget;

    protected boolean canRefresh, canLoadMore;//是否可刷新，可加载更多
    private boolean mNestedScrollInProgress;//是否嵌套滚动
    private boolean mIsBeingDragged;//是否开始拖拽

    private int mActivePointerId = INVALID_POINTER;//点击的Id

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    /**
     * 清空所有动画等
     */
    private void reset() {

    }

    public LSwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public LSwipeRefreshLayout(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        //可滑动控件中用于区别单击子空间和滑动操作的一个伐值
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //viewgroup出于性能考虑，默认会被设置成WILL_NOT_DROW，是不允许重写onDraw方法的，这个方法就是让其可以重写onDraw
        setWillNotDraw(false);
        //创建一个先快后慢的动画插值器
        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LSwipeRefreshLayout);
        headType = typedArray.getInt(R.styleable.LSwipeRefreshLayout_head_type, DEFAULT_TYPE);
        footType = typedArray.getInt(R.styleable.LSwipeRefreshLayout_foot_type, DEFAULT_TYPE);
        typedArray.recycle();

        createLoadingView();

        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }


    /**
     * 创建刷新和加载的View
     */
    private void createLoadingView() {
        if (headType == DEFAULT_TYPE) {
            headLoadView = new DefaultHeadLoadingView(getContext());
        }
        addView(headLoadView);
        if (footType == DEFAULT_TYPE) {
            footLoadView = new DefaultFootLoadingLayout(getContext());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() == 0) {//
            return;
        }
        if (mTarget == null) {//确定要刷新的控件
            ensureTarget();
        }
        if (mTarget == null) {//如果要刷新的控件不存在则直接return
            return;
        }

        View child = mTarget;
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = measuredWidth - getPaddingLeft() - getPaddingRight();
        int bottom = measuredHeight - getPaddingTop() - getPaddingBottom();
        child.layout(left, top, right, bottom);

        int headLoadViewMeasuredWidth = headLoadView.getMeasuredWidth();
        int headLoadViewMeasuredHeight = headLoadView.getMeasuredHeight();

        int footLoadViewMeasuredWidth = footLoadView.getMeasuredWidth();
        int footLoadViewMeasuredHeight = footLoadView.getMeasuredHeight();

        headLoadView.layout(0, -headLoadViewMeasuredHeight, headLoadViewMeasuredWidth, 0);
        footLoadView.layout(0, bottom, footLoadViewMeasuredWidth, bottom + footLoadViewMeasuredHeight);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            reset();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) return;
        mTarget.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                        MeasureSpec.EXACTLY));
        headLoadView.measure(
                MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(headLoadView.getMeasuredHeight(), MeasureSpec.EXACTLY));
        footLoadView.measure(
                MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(footLoadView.getMeasuredHeight(), MeasureSpec.EXACTLY));

    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        /**
         * Android用一个32位的整型值表示一次touchEvent，低8位表示事件的具体动作，比如按下，抬起，滑动，还有多点触控
         * getAction ：触摸动作的原始32位信息，包括事件的动作，触控点信息
         * getActionMask：触摸动作（按下，抬起，滑动，多点按下，多点抬起）
         * getActionIndex：触控点的信息
         */
        int action = MotionEventCompat.getActionMasked(ev);
        if (canChildScrollUp() || !canRefresh || mNestedScrollInProgress) {
            //如果可以继续向上滑动，或者不可以刷新，或者可以嵌套滚动，不拦截
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mIsBeingDragged = false;

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return mIsBeingDragged;
    }

    /**
     * 确定要刷新的布局
     */
    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                //不是刷新的布局，所以确定为要实现刷新的控件
                if (!child.equals(headLoadView)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }


    public boolean isFreshing() {
        return freshing;
    }

    public void setFreshing(boolean freshing) {
        this.freshing = freshing;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    /**
     * 设置刷新
     *
     * @param listener
     */
    public void setRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    /**
     * 设置加载更多
     *
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    public interface OnRefreshListener {

    }

    public interface OnLoadMoreListener {

    }
}
