package com.lxp.widgetview.draw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 画板
 * Created by Li Xiaopeng on 2017/7/10.
 */

public class DrawView extends View {

    private Paint mPaint;
    private int preX;
    private int preY;
    Canvas pathCanvas;

    private Path mPath;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Bitmap cacheBitmap;

    private void init() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //初始化画板
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheBitmap.eraseColor(Color.parseColor("#999999"));
        //初始化画笔
        pathCanvas = new Canvas();//画笔画板
        mPath = new Path();//画笔路径
        pathCanvas.setBitmap(cacheBitmap);
        mPaint = new Paint();//初始化画笔
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                preX = (int) x;
                preY = (int) y;
                break;
            case MotionEvent.ACTION_MOVE:

                mPath.quadTo(preX,preY,x,y);//这个比较平滑，但是偶尔会有断点
                //mPath.lineTo(x,y);//这个不够平滑
                preX = (int) x;
                preY = (int) y;

                break;
            case MotionEvent.ACTION_UP:
                pathCanvas.drawPath(mPath,mPaint);
                break;

        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画一个画布(颜色#999999)
         */
        Paint bmpPaint = new Paint();
        canvas.drawBitmap(cacheBitmap, 0, 0, bmpPaint);
        //初始化时划线
        canvas.drawPath(mPath, mPaint);//画笔
    }
}
