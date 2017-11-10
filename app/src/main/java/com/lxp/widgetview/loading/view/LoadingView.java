package com.lxp.widgetview.loading.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lxp.utils.UiUtils;

/**
 * 加载动画
 * Created by Li Xiaopeng on 17/11/1.
 */

public class LoadingView extends View {

    public static int minWide;
    public static int minHeight;

    private int wide;
    private int height;

    private Paint strokePaint;//边框画笔
    private Paint contentPaint;//内部画笔
    private Paint textPaint;//文字的画笔

    private int contentColor = Color.parseColor("#ff6666");
    private int strokeColor = Color.parseColor("#e60012");
    private int textColor = Color.parseColor("#ffffff");

    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        minWide = UiUtils.dip2px(context, 100);
        minHeight = UiUtils.dip2px(context, 65);

        initPaint();
    }

    private void initPaint() {
        contentPaint = new Paint();
        contentPaint.setAntiAlias(true);
        contentPaint.setColor(contentColor);
        contentPaint.setStrokeWidth(30);
        contentPaint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(30);
        strokePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(30);
        textPaint.setStyle(Paint.Style.FILL);



    }

    public void setContentPaintColor(Color color){

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wide = w;
        height = h;
    }
}
