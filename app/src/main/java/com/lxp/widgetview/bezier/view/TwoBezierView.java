package com.lxp.widgetview.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lxp.utils.UiUtils;
import com.lxp.widgetview.bezier.BezierUitls;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Li Xiaopeng on 17/8/14.
 */

public class TwoBezierView extends View {

    private static final String TAG = "TwoBezierView";


    private Paint pointPaint;
    private Paint darkPointPaint;
    private Paint linePaint;
    private Paint pathPaint;

    int w = getResources().getDisplayMetrics().widthPixels;
    int h = getResources().getDisplayMetrics().heightPixels;
    int dp_30 = UiUtils.dip2px(getContext(), 30);

    Timer timer;

    private int pro = 1;

    public TwoBezierView(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public TwoBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init();
    }

    public TwoBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private void init() {
        data = new ArrayList<>();
        initPointPaint();
        initLinePaint();
        initPathPaint();
        initDarkPathPaint();


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start(pro);
                pro++;
                if (pro > 1000) {
                    timer.cancel();
                }
            }
        }, 0, 30);
    }

    private void initDarkPathPaint() {
        darkPointPaint = new Paint();
        darkPointPaint.setColor(Color.BLACK);
        darkPointPaint.setStyle(Paint.Style.STROKE);
        darkPointPaint.setStrokeWidth(10);
        darkPointPaint.setAntiAlias(true);
        darkPointPaint.setStrokeJoin(Paint.Join.ROUND);
        darkPointPaint.setStrokeCap(Paint.Cap.ROUND);
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

        drawDarkLine(canvas);//画移动的线

        drawLinePath(canvas);
    }

    private int startX = dp_30;
    int startY = w - dp_30;
    int endX = w / 2;
    int endY = dp_30;

    private void drawDarkLine(Canvas canvas) {
        canvas.drawLine(startX, startY, endX, endY, darkPointPaint);
    }


    /*设置进度条进度, 外部调用*/
    public void start(int progress) {
        if (progress > 1000) {
            this.pro = 1000;
        } else if (progress < 0) {
            this.pro = 0;
        } else {
            this.pro = progress;
            startX = BezierUitls.getBezierPoiontX(dp_30, w / 2, pro, 1000);
            startY = BezierUitls.getBezierPoiontX(w - dp_30, dp_30, pro, 1000);
            endX = BezierUitls.getBezierPoiontX(w / 2, w - dp_30, pro, 1000);
            endY = BezierUitls.getBezierPoiontX(dp_30, w - dp_30, pro, 1000);
            int bezierPoiontX_3 = BezierUitls.getBezierPoiontX(startX, endX, pro, 1000);
            int bezierPoiontY_3 = BezierUitls.getBezierPoiontX(startY, endY, pro, 1000);
            data.add(new Point(bezierPoiontX_3,bezierPoiontY_3));
        }
        postInvalidate();
    }

    List<Point> data;
    private void drawLinePath(Canvas canvas) {
        for (int i = 0; i < data.size(); i++) {
            Point point = data.get(i);
            canvas.drawPoint(point.x, point.y, pathPaint);
        }
    }


    private void drawLine(Canvas canvas) {
        canvas.drawLine(w / 2, dp_30, dp_30, w - dp_30, linePaint);
        canvas.drawLine(w / 2, dp_30, w - dp_30, w - dp_30, linePaint);
    }

    private void drawPoint(Canvas canvas) {
        canvas.drawPoint(w / 2, dp_30, pointPaint);
        canvas.drawPoint(dp_30, w - dp_30, pointPaint);
        canvas.drawPoint(w - dp_30, w - dp_30, pointPaint);
    }
}
