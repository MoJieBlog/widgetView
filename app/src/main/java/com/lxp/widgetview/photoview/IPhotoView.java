package com.lxp.widgetview.photoview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.lxp.utils.LogUtils;

/**
 * 图片查看器
 * Created by Li Xiaopeng on 17/12/18.
 */

public class IPhotoView extends ImageView {
    private static final String TAG = "IPhotoView";
    //最大缩放倍数
    private static float SCALE_MAX;
    //最小缩放倍数
    private static float SCALE_MIN;

    private boolean isEnable = false;
    /**
     * 当前放大倍数
     */
    private float mScale;
    /**
     * 是否可放大
     */
    private boolean canScale = true;
    /**
     * 是否可以旋转
     */
    private boolean canRotate = true;
    /**
     * 是否可以拖拽关闭图片
     */
    private boolean canDragFinish = true;
    /**
     * 图片的矩阵
     */
    Matrix matrix = new Matrix();

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    /**
     * 控件宽度
     */
    private int mWidth;
    /**
     * 控件高度
     */
    private int mHeight;
    /**
     * 拿到src的图片
     */
    private Drawable mDrawable;
    /**
     * 图片宽度（使用前判断mDrawable是否null）
     */
    private int mDrawableWidth;
    /**
     * 图片高度（使用前判断mDrawable是否null）
     */
    private int mDrawableHeight;

    private boolean once = true;//首次进入时测量量图片和布局的宽高
    private boolean isScaling = false;//是否正在缩放
    private Activity activity;

    //拖拽的手势监听
    private DragGestureDetector dragGestureDetector;
    //旋转的手势监听
    private RotateGestureDetector rotateGestureDetector;
    //手势监听
    private GestureDetector gestureDetector;
    //缩放的手势监听
    private ScaleGestureDetector scaleGestureDetector;

    public IPhotoView(Context context) {
        super(context);
        init();
    }

    public IPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        init();
    }


    private void init() {
        gestureDetector = new GestureDetector(getContext(), gestureListener);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), ScaleGestureListener);
        rotateGestureDetector = new RotateGestureDetector(rotateCallBack);
        dragGestureDetector = new DragGestureDetector(dragCallBack);
    }


    private void move() {
        final float dx = mWidth / 2 - mDrawableWidth / 2;
        final float dy = mHeight / 2 - mDrawableHeight / 2;
        // 平移至中心
        matrix.postTranslate(dx, dy);
        // 以控件中心作为缩放
        matrix.postScale(mScale, mScale, mWidth / 2, mHeight / 2);
        setImageMatrix(matrix);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (once) {
            Drawable d = getDrawable();
            if (d == null)
                return;
            int width = getWidth();
            int height = getHeight();
            // 拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            //获取控件的大小
            mWidth = getWidth();
            mHeight = getHeight();

            //获取图片的尺寸
            mDrawable = getDrawable();
            if (mDrawable == null) return;
            mDrawableWidth = mDrawable.getIntrinsicWidth();
            mDrawableHeight = mDrawable.getIntrinsicHeight();


            mScale = (float) (width * 1.0 / dw);
            SCALE_MAX = 2 * mScale;
            SCALE_MIN = (float) (0.5 * mScale);

            move();
            once = false;
        }
    }

    /**
     * 手势监听
     */
    GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {//点击关闭
            doFinish();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {//双击缩放
            doScale(e);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {//长按选项框
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            LogUtils.logE(TAG, "onScroll: ");
            if (Math.abs(distanceX)>25){
                matrix.postTranslate(-distanceX, -distanceY);
                setImageMatrix(matrix);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }
    };


    /**
     * 移动时，进行边界判断，主要判断宽或高大于屏幕的
     */
    private void checkMatrixBounds() {
        LogUtils.logE(TAG, "checkMatrixBounds: ");
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;

        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.height() > viewHeight || rect.width() > viewWidth) {//图片宽或者高大于控件时
            if (rect.top > 0 && rect.bottom > viewHeight) {//上边界不是0，并且下边界大于控件高度
                if (rect.height() > viewHeight) {//图片高度大于控件高度
                    deltaY = -rect.top;
                } else {//图片高度小于空间高度，Y方向移动到中间
                    deltaY = -(rect.centerY() - viewHeight / 2);
                }
            }
            if (rect.bottom < viewHeight && rect.top < 0) {//下边界不是底部，并且上边界超过顶部
                if (rect.height() > viewHeight) {//高度超过控件高度
                    deltaY = viewHeight - rect.bottom;
                } else {
                    deltaY = viewHeight / 2 - rect.centerY();
                }

            }
            if (rect.left > 0 && rect.right > viewWidth) {//左边界
                if (rect.width() > viewWidth) {
                    deltaX = -rect.left;
                } else {
                    deltaX = -(rect.centerX() - viewWidth / 2);
                }
            }
            if (rect.right < viewWidth && rect.left < 0) {//右边界
                if (rect.width() > viewWidth) {
                    deltaX = viewWidth - rect.right;
                } else {
                    deltaX = -(rect.centerX() - viewWidth / 2);
                }
            }
            matrix.postTranslate(deltaX, deltaY);
            setImageMatrix(matrix);
        } else {//直接移动到中心
            deltaX = -(rect.centerX() - viewWidth / 2);
            deltaY = viewHeight / 2 - rect.centerY();
            matrix.postTranslate(deltaX, deltaY);
            setImageMatrix(matrix);
        }
    }

    private void doScale(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        float currentScale = getCurrentScale();
        if (currentScale == mScale) {
            IPhotoView.this.postDelayed(
                    new IPhotoView.AutoScaleRunnable(SCALE_MAX, x, y), 10);
        } else {
            IPhotoView.this.postDelayed(
                    new IPhotoView.AutoScaleRunnable(mScale, x, y), 10);
        }

    }

    public void setIsEnable(@NonNull Activity activity, boolean isEnable) {
        this.isEnable = isEnable;
        this.activity = activity;
    }

    private void doFinish() {
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 缩放的手势监听回调
     */
    ScaleGestureDetector.OnScaleGestureListener ScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            LogUtils.logE(TAG, "onScale: = " + detector.getScaleFactor());
            if (getDrawable() == null)
                return true;
            float scale = getCurrentScale();//当前的缩放比例
            float scaleFactor = detector.getScaleFactor();//获取相对于上次的缩放比例
            if ((scale > SCALE_MAX && scaleFactor > 1.0f) || (scale < SCALE_MIN && scaleFactor < 1.0f)) {
                if (scaleFactor * scale < SCALE_MIN) {//缩小到最小
                    scaleFactor = SCALE_MIN / scale;
                    return true;
                }
                if (scaleFactor * scale > SCALE_MAX) {//放大到最大
                    scaleFactor = SCALE_MAX / scale;
                    return true;
                }
            }
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();
            executeTranslate(scaleFactor, focusX, focusY);
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            LogUtils.logE(TAG, "onScaleBegin: ");
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            LogUtils.logE(TAG, "onScaleEnd: ");

        }
    };

    /**
     * 进行缩放
     *
     * @param scale
     * @param focusX
     * @param focusY
     */
    private void executeTranslate(float scale, float focusX, float focusY) {
        if (!isScaling) {
            isScaling = true;
            matrix.postScale(scale, scale, focusX, focusY);
            checkBorderAndCenterWhenScale();
            setImageMatrix(matrix);
            if (getCurrentScale() == mScale) {
                move();
            }
            isScaling = false;
        }
    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();//控件的宽度
        int height = getHeight();//控件的高度

        // 如果宽大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        // 如果高大于屏幕，则控制范围
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        // 如果高小于屏幕，则让其居中
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        matrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 自动缩放的任务
     */
    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.1f;
        static final float SMALLER = 0.9f;
        private float mTargetScale;
        private float tmpScale;

        /**
         * 缩放的中心
         */
        private float x;
        private float y;

        /**
         * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
         *
         * @param targetScale
         */
        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getCurrentScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }

        }

        @Override
        public void run() {
            // 进行缩放
            matrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(matrix);

            final float currentScale = getCurrentScale();
            // 如果值在合法范围内，继续缩放
            if (((tmpScale > 1f) && (currentScale < mTargetScale))
                    || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                IPhotoView.this.postDelayed(this, 10);
            } else
            // 设置为目标的缩放比例
            {
                final float deltaScale = mTargetScale / currentScale;
                matrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(matrix);
                isScaling = false;
            }

        }
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix mmatrix = matrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mmatrix.mapRect(rect);
        }
        return rect;
    }

    /**
     * 旋转的手势监听回调
     */
    RotateGestureDetector.OnRotateListener rotateCallBack = new RotateGestureDetector.OnRotateListener() {
        @Override
        public void onRotate(float degrees, float focusX, float focusY) {
            LogUtils.logE(TAG, "onRotate: ");
        }
    };

    /**
     * 拖拽的手势监听回调
     */
    DragGestureDetector.DragCallback dragCallBack = new DragGestureDetector.DragCallback() {
        @Override
        public void drag(float nowX, float nowY, float downX, float downY) {
            LogUtils.logE(TAG, "drag: ");
        }

        @Override
        public void dragUp(float upX, float upY, float downX, float downY) {
            float currentScale = getCurrentScale();
            if (Math.abs(upX - downX) > 5 || Math.abs(upY - downY) > 5) {//水平或者竖直方向位移大于5，视为拖拽
                if (currentScale == mScale) {//不是缩放状态 拖拽的抬起是回到最初的状态，或者关闭页面
                    IPhotoView.this.postDelayed(
                            new IPhotoView.AutoScaleRunnable(currentScale, mWidth / 2, mHeight / 2), 10);
                } else {//缩放状态 单指拖拽时 检查边界，显示
                    checkMatrixBounds();
                }
            } else {//双击 双击的抬起进行缩放，暂时不做其他处理

            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (isEnable) {
            int pointerCount = event.getPointerCount();
            if (pointerCount == 2) {
                if (canScale) scaleGestureDetector.onTouchEvent(event);
                if (canRotate) rotateGestureDetector.onTouchEvent(event);
            }
            if (canDragFinish && pointerCount == 1) {//可以拖拽关闭，按压一个点，不是放大状态
                dragGestureDetector.onTouchEvent(event);
                gestureDetector.onTouchEvent(event);
            }
            return true;
        } else {
            return super.dispatchTouchEvent(event);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 获取放大的倍数
     *
     * @return
     */
    public float getCurrentScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 是否处于放大或者缩小状态
     *
     * @return
     */
    public boolean isZooming() {
        return mScale == getCurrentScale();
    }
}
