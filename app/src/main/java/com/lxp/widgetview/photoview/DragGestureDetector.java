package com.lxp.widgetview.photoview;

import android.view.MotionEvent;

/**
 * 拖拽关闭当前页面的一个手势监听
 * Created by Li Xiaopeng on 17/11/8.
 */

public class DragGestureDetector {

    private float nowY = 0;//拖拽的Y轴坐标
    private float nowX = 0;//拖拽的X轴坐标

    float mDownY = 0;
    float mDownX = 0;

    private DragCallback callback;

    public DragGestureDetector(DragCallback callback) {
        this.callback = callback;
    }

    public void onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event);
                callback.drag(nowX,nowY,mDownX,mDownY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                callback.dragUp(event.getX(),event.getY(),mDownX,mDownY);
                onActionUp(event);
                break;
        }
    }

    private void onActionUp(MotionEvent event) {
        mDownX = 0;
        mDownY = 0;
    }

    private void onActionMove(MotionEvent event) {
         nowY = event.getY();
         nowX = event.getX();
    }

    private void onActionDown(MotionEvent event) {
        mDownX = event.getX();
        mDownY = event.getY();
    }

    interface DragCallback {
        /**
         * 拖拽
         * @param nowX
         * @param nowY
         * @param downX
         * @param downY
         */
        void drag(float nowX, float nowY, float downX, float downY);

        /**
         * 松手
         */
        void dragUp(float upX, float upY, float downX, float downY);
    }
}
