package com.lxp.widgetview.loading.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lxp.utils.UiUtils;

/**
 * 签到动画
 * Created by Li Xiaopeng on 17/11/2.
 */

public class SignView extends View {

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

    private final int STATUS_SIGN = 1;//已签到
    private final int STATUS_UNSIGN = 0;//未签到
    private final int STATUS_SIGN_FAIL = -1;//签到失败

    private int mStatus = STATUS_UNSIGN;

    public SignView(Context context) {
        super(context);
        init(context);
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    public void setStatus(int status) {
        mStatus = status;
    }

    public void setContentPaintColor(Color color) {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wideMode == MeasureSpec.EXACTLY) {
            wide = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            wide = minWide;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMode);
        } else {
            height = minHeight;
        }

        setMeasuredDimension(wide, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mStatus) {
            case STATUS_SIGN:
                drawSignView(canvas);
                break;
            case STATUS_UNSIGN:
                break;
            case STATUS_SIGN_FAIL:
                break;
            default:
                break;
        }
    }

    /**
     * 未签到的View
     * @param canvas
     */
    private void drawSignView(Canvas canvas) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
    }
}
