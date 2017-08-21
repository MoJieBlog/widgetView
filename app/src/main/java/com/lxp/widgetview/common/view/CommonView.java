package com.lxp.widgetview.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lxp.widgetview.R;

/**
 * 示例用于展示简单的画笔划线，圆，矩形。
 * 构造方法的解释
 * attrs的运用
 * Created by Li Xiaopeng on 2017/6/29.
 */

public class CommonView extends View {

    private static final String TAG = "CommonView";

    private int type = TYPE_LINE;
    private int mode = MODE_STROKE;
    private int mColor = Color.parseColor("#000000");
    private int wide = 2;
    private int orientation = 0;//0:横向 1：竖向 2： 45度倾斜

    private static int TYPE_LINE = 0;//线,划线使用inColor
    private static int TYPE_CIRCLE = 1;//圆
    private static int TYPE_RECTANGLE = 2;//矩形

    private static int MODE_STROKE = 0;//描边
    private static int MODE_FILL = 1;//填充


    private Paint mPaint;

    /**
     * 代码中直接new时，调用这个构造方法
     *
     * @param context
     */
    public CommonView(Context context) {
        super(context);
    }

    /**
     * 这个用在xml文件中，并且该View含有自定义属性，
     *
     * @param context
     * @param attrs
     */
    public CommonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonView);
        type = typedArray.getInt(R.styleable.CommonView_type, 0);
        mode = typedArray.getInt(R.styleable.CommonView_mode, 0);
        wide = typedArray.getInt(R.styleable.CommonView_wide, 2);
        orientation = typedArray.getInt(R.styleable.CommonView_orientation, 0);
        mColor = typedArray.getColor(R.styleable.CommonView_color, 0xffffff);
        typedArray.recycle();

    }

    /**
     * 在xml中调用，并且该View含有自定义属性，一般不会用到
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CommonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int height_me;
    int wide_me;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height_me = MeasureSpec.getSize(heightMeasureSpec);
        wide_me = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height_me);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(wide);
        mPaint.setColor(mColor);

        if (mode == MODE_STROKE) {
            mPaint.setStyle(Paint.Style.STROKE);
        } else if (mode == MODE_FILL) {
            mPaint.setStyle(Paint.Style.FILL);
        }

        /**
         * 考虑到空心，所以起始坐标为wide
         */
        if (type == TYPE_LINE) {
            if (orientation == 0) {
                canvas.drawLine(wide, wide, wide_me, wide, mPaint);
            } else if (orientation == 1) {
                canvas.drawLine(wide, wide, wide, height_me, mPaint);
            } else if (orientation == 2) {
                canvas.drawLine(wide, wide, wide_me, height_me, mPaint);
            }

        } else if (type == TYPE_RECTANGLE) {
            canvas.drawRect(wide, wide, wide_me - 2 * wide, height_me - 2 * wide, mPaint);
        } else if (type == TYPE_CIRCLE) {

            canvas.drawCircle(wide_me / 2, height_me / 2, wide_me / 2 - 2 * wide, mPaint);
        }


    }
}
