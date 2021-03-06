package com.lxp.widgetview.refresh;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 头部和底部控件的父类
 * Created by Li Xiaopeng on 18/1/17.
 */

public abstract class LoadingLayout extends FrameLayout implements RefreshInterface{

    protected Context mContext;
    protected Resources mResources;

    public LoadingLayout(@NonNull Context context) {
        this(context,null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mResources  = context.getResources();
        init();
        //createViewPlaceHolder();
    }

    protected abstract void init();

   /* private void createViewPlaceHolder() {
        View viewPlaceHolder = new View(mContext);
        FrameLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(viewPlaceHolder,lp);
    }*/

}
