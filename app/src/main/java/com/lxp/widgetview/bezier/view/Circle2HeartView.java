package com.lxp.widgetview.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.lxp.widgetview.R;

import java.util.List;

/**
 * 圆变矩形
 * Created by Li Xiaopeng on 17/10/10.
 */

public class Circle2HeartView extends View {

    private Paint pointPaint;
    private Paint circlePaint;

    public Circle2HeartView(Context context) {
        super(context);
    }

    public Circle2HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
    }

    public Circle2HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
    }

    private void initInflate() {
        initView();
    }

    private void initView() {
        initPointPaint();
        initCirclePaint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPoint(canvas);
        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        Path path1 = new Path();
        path1.moveTo(x[0], y[0]);
        path1.cubicTo(x[1],y[1],x[2],y[2],x[3],y[3]);
        canvas.drawPath(path1, circlePaint);

        Path path2 = new Path();
        path2.moveTo(x[3], y[3]);
        path2.cubicTo(x[4],y[4],x[5],y[5],x[6],y[6]);
        canvas.drawPath(path2, circlePaint);

        Path path3 = new Path();
        path3.moveTo(x[6], y[6]);
        path3.cubicTo(x[7],y[7],x[8],y[8],x[9],y[9]);
        canvas.drawPath(path3, circlePaint);

        Path path4 = new Path();
        path4.moveTo(x[9], y[9]);
        path4.cubicTo(x[10],y[10],x[11],y[11],x[0],y[0]);
        canvas.drawPath(path4, circlePaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int winW = getResources().getDisplayMetrics().widthPixels;
    int winH = getResources().getDisplayMetrics().heightPixels;

    private int[] x = {
            winW / 2 + 300,
            winW / 2 + 300,
            winW / 2 + 150,
            winW / 2,
            winW / 2 - 150,
            winW / 2 - 300,
            winW / 2 - 300,
            winW / 2 - 200,
            winW / 2 - 150,
            winW / 2,
            winW / 2 + 150,
            winW / 2 + 200};
    private int[] y = {
            winH / 2,
            winH / 2 - 150,
            winH / 2 - 300,
            winH / 2 - 150,
            winH / 2 - 300,
            winH / 2 - 150,
            winH / 2,
            winH / 2 + 150,
            winH / 2 + 150,
            winH / 2 + 300,
            winH / 2 + 150,
            winH / 2 + 150
    };

    private void drawPoint(Canvas canvas) {
        canvas.drawPoint(x[0], y[0], pointPaint);
        canvas.drawPoint(x[1], y[1], pointPaint);
        canvas.drawPoint(x[2], y[2], pointPaint);
        canvas.drawPoint(x[3], y[3], pointPaint);
        canvas.drawPoint(x[4], y[4], pointPaint);
        canvas.drawPoint(x[5], y[5], pointPaint);
        canvas.drawPoint(x[6], y[6], pointPaint);
        canvas.drawPoint(x[7], y[7], pointPaint);
        canvas.drawPoint(x[8], y[8], pointPaint);
        canvas.drawPoint(x[9], y[9], pointPaint);
        canvas.drawPoint(x[10], y[10], pointPaint);
        canvas.drawPoint(x[11], y[11], pointPaint);
    }


    /**
     * 初始化点的画笔
     */
    private void initCirclePaint() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(15);
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.ROUND);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 初始化点的画笔
     */
    private void initPointPaint() {
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeWidth(30);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeJoin(Paint.Join.ROUND);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
    }


}
