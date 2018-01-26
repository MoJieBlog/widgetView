package com.lxp.widgetview.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.lxp.widgetview.R;

/**
 * 底部刷新控件
 * Created by Li Xiaopeng on 18/1/17.
 */

public class DefaultFootLoadingLayout extends LoadingLayout {

    private View rootView;

    public DefaultFootLoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public DefaultFootLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.fresh_view, this, false);
        addView(rootView);
    }


    @Override
    public void onMoving(float moveOffset, boolean isRefreshing) {
        setVisibility(VISIBLE);
    }

    @Override
    public void onRefreshing() {
        setVisibility(VISIBLE);
    }

    @Override
    public void onRefreshFinish() {
        setVisibility(GONE);
    }

    @Override
    public int getLoadingViewHeight() {
        return rootView.getHeight();
    }
}
