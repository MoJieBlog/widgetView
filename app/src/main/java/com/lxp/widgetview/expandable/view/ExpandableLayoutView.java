package com.lxp.widgetview.expandable.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Li Xiaopeng on 17/9/4.
 */

public class ExpandableLayoutView extends LinearLayout {

    private boolean expanded = false;
    private Context mContext;

    private View titleView;
    private View contentView;

    public ExpandableLayoutView(Context context) {
        super(context);
        mContext = context;
    }

    public ExpandableLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ExpandableLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();

        if (childCount != 2) throw new IllegalArgumentException("childView count must be 2");
        titleView = getChildAt(0);
        contentView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isExpanded()) {
            int expandSpec =
                    View.MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setTitleView(int view) {
        titleView = LayoutInflater.from(mContext).inflate(view, this);
    }

    public void setContentView(int view) {
        contentView = LayoutInflater.from(mContext).inflate(view, this);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
