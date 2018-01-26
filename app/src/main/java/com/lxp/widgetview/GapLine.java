package com.lxp.widgetview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Li Xiaopeng on 18/1/20.
 */

public class GapLine extends View {

    private static final String TAG = "GapLine";
    public static final int DEFAULT_DASH_LENGTH = 30;
    public static final int DEFAULT_HORIZONTAL_LENGTH = 50;
    public static final int DEFAULT_VERTICAL_LENGTH = 50;
    public static final int DEFAULT_LINE_COLOR = Color.RED;

    /**
     * 虚线的方向
     */
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    /**
     * 默认为水平方向
     */
    public static final int DEFAULT_DASH_ORIENTATION = ORIENTATION_HORIZONTAL;
    /**
     * 间距宽度
     */
    private float dashLength;
    /**
     * 竖直高度
     */
    private float verticalLength;
    /**
     * 水平宽度
     */
    private float horizontalLength;
    /**
     * 线段颜色
     */
    private int lineColor;
    private int dashOrientation;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int widthSize;
    private int heightSize;


    public GapLine(Context context){
        this(context,null);
    }

    public GapLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        dashLength = DEFAULT_DASH_LENGTH;
        verticalLength = DEFAULT_VERTICAL_LENGTH;
        horizontalLength = DEFAULT_HORIZONTAL_LENGTH;
        lineColor = DEFAULT_LINE_COLOR;
        dashOrientation = DEFAULT_DASH_ORIENTATION;
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(verticalLength);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        heightSize = MeasureSpec.getSize(heightMeasureSpec - getPaddingTop() - getPaddingBottom());
        if (dashOrientation == ORIENTATION_HORIZONTAL) {
            //不管在布局文件中虚线高度设置为多少，虚线的高度统一设置为实体线段的高度
            setMeasuredDimension(widthSize, (int) verticalLength);
        } else {
            setMeasuredDimension((int) horizontalLength, heightSize);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (dashOrientation) {
            case ORIENTATION_VERTICAL:
                drawVerticalLine(canvas);
                break;
            default:
                drawHorizontalLine(canvas);
        }
    }

    /**
     * 画水平方向虚线
     *
     * @param canvas
     */
    public void drawHorizontalLine(Canvas canvas) {
        float totalWidth = 0;
        canvas.save();
        float[] pts = {0, 0, horizontalLength, 0};
        //在画线之前需要先把画布向下平移半个线段高度的位置，目的就是为了防止线段只画出一半的高度
        //因为画线段的起点位置在线段左下角
        canvas.translate(0, verticalLength / 2);
        while (totalWidth <= widthSize) {
            canvas.drawLines(pts, mPaint);
            canvas.translate(horizontalLength + dashLength, 0);
            totalWidth += horizontalLength + dashLength;
        }
        canvas.restore();
    }

    /**
     * 画竖直方向虚线
     *
     * @param canvas
     */
    public void drawVerticalLine(Canvas canvas) {
        float totalHeight = 0;
        canvas.save();
        float[] pts = {0, 0, horizontalLength, 0};
        //在画线之前需要先把画布向右平移半个线段高度的位置，目的就是为了防止线段只画出一半的高度
        //因为画线段的起点位置在线段左下角
        canvas.translate(horizontalLength/2, 0);
        while (totalHeight <= heightSize) {
            canvas.drawLines(pts, mPaint);
            canvas.translate(0, verticalLength + dashLength);
            totalHeight += verticalLength + dashLength;
        }
        canvas.restore();
    }

    public void setDashLength(float dashLength) {
        this.dashLength = dashLength;
    }

    public void setVerticalLength(float verticalLength) {
        if (mPaint!=null){
            mPaint.setStrokeWidth(verticalLength);
        }
        this.verticalLength = verticalLength;
    }


    public void setHorizontalLength(float horizontalLength) {
        this.horizontalLength = horizontalLength;
    }


    public void setLineColor(int lineColor) {
        if (mPaint!=null){
            mPaint.setColor(lineColor);
        }
        this.lineColor = lineColor;
    }

    public void setDashOrientation(int dashOrientation) {
        this.dashOrientation = dashOrientation;
    }
}
