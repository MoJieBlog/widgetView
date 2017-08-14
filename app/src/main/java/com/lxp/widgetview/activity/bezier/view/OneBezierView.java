package com.lxp.widgetview.activity.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lxp.utils.UiUtils;
import com.lxp.widgetview.activity.bezier.BezierUitls;

/**
 * 一阶贝塞尔曲线
 * Created by Li Xiaopeng on 17/8/14.
 */

public class OneBezierView extends View {
    private static final String TAG = "OneBezierView";

    public OneBezierView(Context context) {
        super(context);
    }

    public OneBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OneBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getResources().getDisplayMetrics().widthPixels;
        int dp_30 = UiUtils.dip2px(getContext(), 30);

        drawPoint(canvas, w, dp_30);
        drawLine(canvas, w, dp_30);

        drawBezierLine(canvas, w, dp_30);



    }

    private void drawBezierLine(Canvas canvas, int w, int dp_30) {
/*
        for (int i=0;i<1000;i++){
            Path path = new Path();
            path.moveTo(dp_30,dp_30);
            BezierUitls.getBezierPoiontX(dp_30,w-dp_30,i,1000);
            invalidate();
        }*/

    }

    private void drawLine(Canvas canvas, int w, int dp_30) {
        Log.e(TAG,"777");
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(30);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);


        canvas.drawLine(dp_30,dp_30,w-dp_30,w-dp_30,paint);

    }

    private void drawPoint(Canvas canvas, int w, int dp_30) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawPoint(dp_30,dp_30,paint);

        canvas.drawPoint(w-dp_30,w-dp_30,paint);
    }
}
