package com.lxp.widgetview.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lxp.utils.UiUtils;
import com.lxp.widgetview.bezier.BezierUitls;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 一阶贝塞尔曲线
 * Created by Li Xiaopeng on 17/8/14.
 */

public class OneBezierView extends View {
    private static final String TAG = "OneBezierView";


    private Paint pointPaint;
    private Paint linePaint;
    private Paint pathPaint;

    int w = getResources().getDisplayMetrics().widthPixels;
    int h = getResources().getDisplayMetrics().heightPixels;
    int dp_30 = UiUtils.dip2px(getContext(), 30);

    Timer timer;

    private int pro = 1;

    private Path path;

    public OneBezierView(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public OneBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init();
    }

    public OneBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private void init() {
        initPointPaint();
        initLinePaint();
        initPathPaint();


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start(pro);
                pro++;
                if (pro > 300) {
                    timer.cancel();
                }
            }
        }, 0, 30);
    }

    private void initPathPaint() {
        pathPaint = new Paint();
        pathPaint.setColor(Color.parseColor("#00aa00"));
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);
        pathPaint.setStrokeWidth(15);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void initLinePaint() {
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#bb0000"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAlpha(50);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(15);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void initPointPaint() {
        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(30);
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeJoin(Paint.Join.ROUND);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoint(canvas);
        drawLine(canvas);


        path = new Path();
        path.moveTo(dp_30, dp_30);
        drawLinePath(canvas);
    }


    /*设置进度条进度, 外部调用*/
    public void start(int progress) {
        if (progress > 300) {
            this.pro = 300;
        } else if (progress < 0) {
            this.pro = 0;
        } else {
            this.pro = progress;
        }
        postInvalidate();
    }

    private void drawLinePath(Canvas canvas) {
        int bezierPoiontX = BezierUitls.getBezierPoiontX(dp_30, w - dp_30, pro, 300);
        int bezierPoiontY = BezierUitls.getBezierPoiontX(dp_30, w - dp_30, pro, 300);

        path.lineTo(bezierPoiontX, bezierPoiontY);
        canvas.drawPath(path, pathPaint);
        //考虑到后期要写二阶所以这边直接用path
        //canvas.drawLine(dp_30, dp_30, bezierPoiontX, bezierPoiontY, pathPaint);

    }


    private void drawLine(Canvas canvas) {
        canvas.drawLine(dp_30, dp_30, w - dp_30, w - dp_30, linePaint);
    }

    private void drawPoint(Canvas canvas) {
        canvas.drawPoint(dp_30, dp_30, pointPaint);
        canvas.drawPoint(w - dp_30, w - dp_30, pointPaint);
    }
}
