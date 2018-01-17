package com.lxp.widgetview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Li Xiaopeng on 18/1/17.
 */

public class CheckView extends View {

    private static final int STATUS_NULL = 0;//动画开始前
    private static final int STATUS_START = 1;//动画开始

    private static final int MAX_PAGER = 13;//图片的个数

    private static final int MESSAGE_START = 0;
    private static final int MESSAGE_STOP = 1;
    private static final int MESSAGE_REPEAT = 2;

    private Context context;
    private Bitmap checkBitmap;//图片
    private Paint mPaint;

    private int status = STATUS_NULL;
    private int currentPage = 0;

    private int mWide,mHeight;//控件的宽高

    public CheckView(Context context) {
        super(context);
        init(context);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initPaint();
        checkBitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.checkmark);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffff5317);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWide = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布移动到中间
        canvas.translate(mWide/2,mHeight/2);
        //画背景
        canvas.drawCircle(0,0,250,mPaint);
        //图像的大小
        int height = checkBitmap.getHeight();
        int width = checkBitmap.getWidth();
        //指定绘制图片的区域,这里绘制长宽都是图片高度的矩形
        Rect src = new Rect(height*currentPage,0,height*(currentPage+1),height);
        //指定图片在屏幕上显示的区域,即坐标
        Rect dst = new Rect(-200,-200,200,200);
        //绘制到画布
        canvas.drawBitmap(checkBitmap,src,dst,null);
    }



    public void start(){
        if (status==STATUS_NULL){
            status = STATUS_START;
            mHandel.sendEmptyMessage(MESSAGE_START);
        }

    }

    public void stop(){
        if (status==STATUS_START){
            status = STATUS_NULL;
            mHandel.sendEmptyMessage(MESSAGE_STOP);
        }
    }

    public void repeat(){
        if (status==STATUS_NULL){
            status = STATUS_START;
            mHandel.sendEmptyMessage(MESSAGE_REPEAT);
        }
    }

    private Handler mHandel = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_STOP:
                    this.removeMessages(MESSAGE_STOP);
                    this.removeMessages(MESSAGE_REPEAT);
                    break;
                case MESSAGE_REPEAT:
                    invalidate();
                    if (currentPage<=MAX_PAGER){
                        currentPage++;
                        this.sendEmptyMessageDelayed(MESSAGE_REPEAT,30);
                    }else{
                        currentPage = 0;
                        this.sendEmptyMessageDelayed(MESSAGE_REPEAT,30);
                    }
                    break;
                case MESSAGE_START:
                    if (currentPage<MAX_PAGER){
                        invalidate();
                        currentPage++;
                        this.sendEmptyMessageDelayed(MESSAGE_START,30);
                    }else{
                        currentPage = 0;
                        status = STATUS_NULL;
                    }
                    break;
            }
        }
    };
}
