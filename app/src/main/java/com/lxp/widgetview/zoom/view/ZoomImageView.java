package com.lxp.widgetview.zoom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.lxp.utils.LogUtils;
import com.lxp.utils.ToastUtils;


/**
 * Created by Li Xiaopeng on 17/8/23.
 * 支持缩放的ImageView
 * <p>
 * 默认宽度为屏幕宽度，高度等比例缩放
 */
public class ZoomImageView extends ImageView implements OnScaleGestureListener,
        OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = ZoomImageView.class.getSimpleName();

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


    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private static float initScale;//设置为控件的宽度
    public static float SCALE_MAX;//设置为2*initScale
    private static float SCALE_MIN;//设置为0.5*initScale
    private boolean once = true;//首次进入时测量量图片和布局的宽高

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;
    private final Matrix mScaleMatrix = new Matrix();

    /**
     * 用于双击检测
     */
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;

    private int mTouchSlop;

    private float mLastX;
    private float mLastY;

    private boolean isCanDrag;
    private int lastPointerCount;

    private boolean isCheckTopAndBottom = true;
    private boolean isCheckLeftAndRight = true;

    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);//强制设置模式
        mGestureDetector = new GestureDetector(context,
                new SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (isAutoScale == true)
                            return true;

                        float x = e.getX();
                        float y = e.getY();
                        Log.e("DoubleTap", getScale() + " , " + initScale);
                        if (getScale() == initScale) {//当前倍数是默认，变大
                            ZoomImageView.this.postDelayed(
                                    new AutoScaleRunnable(SCALE_MAX, x, y), 16);
                            isAutoScale = true;
                        } else {//如果有缩放，变回刚好全屏
                            ZoomImageView.this.postDelayed(
                                    new AutoScaleRunnable(initScale, x, y), 16);
                            isAutoScale = true;
                        }
                        return true;
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        clickListener.onClick();
                        return super.onSingleTapConfirmed(e);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        longClickListener.onLongClick();
                    }
                });
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }

    /**
     * 自动缩放的任务
     */
    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
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
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }

        }

        @Override
        public void run() {
            // 进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            final float currentScale = getScale();
            // 如果值在合法范围内，继续缩放
            if (((tmpScale > 1f) && (currentScale < mTargetScale))
                    || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                ZoomImageView.this.postDelayed(this, 16);
            } else
            // 设置为目标的缩放比例
            {
                final float deltaScale = mTargetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }

        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null)
            return true;

        /**
         * 缩放的范围控制
         */
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > SCALE_MIN && scaleFactor < 1.0f)) {
            /**
             * 最大值最小值判断
             */
            if (scaleFactor * scale < SCALE_MIN) {
                scaleFactor = SCALE_MIN / scale;
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            /**
             * 设置缩放比例
             */
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;

    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);

        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event))
            return true;
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;

        /**
         * 每当触摸点发生变化时，重置mLasX , mLastY
         */
        if (pointerCount != lastPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        lastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                Log.e(TAG, "ACTION_MOVE");
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag) {

                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 如果宽度小于屏幕宽度，则禁止左右移动
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        // 如果高度小雨屏幕高度，则禁止上下移动
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }


                        mScaleMatrix.postTranslate(dx, dy);
                        checkMatrixBounds();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "ACTION_UP");
                lastPointerCount = 0;
                break;
        }

        return true;
    }

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
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


            initScale = (float) (width * 1.0 / dw);
            SCALE_MAX = 2 * initScale;
            SCALE_MIN = (float) (0.5 * initScale);

            Log.e(TAG, width + " , " + height);
            Log.e(TAG, dw + " , " + dh);
            Log.e(TAG, "initScale = " + initScale);
            move();
            once = false;
        }

    }

    private void move() {
        float rate = mDrawableWidth / mDrawableHeight;//缩放前的比例
        float drawable_height = mWidth / rate;//缩放后的高度
        if (drawable_height < mHeight) {//图片高度小于控件高度时显示在中心
            LogUtils.logE(TAG, "move: 中心");
            moveToCenter();
        } else {//
            LogUtils.logE(TAG, "move: 上端");
            moveToTop();
        }
    }

    /**
     * 移动到顶部
     */
    private void moveToTop() {
        final float dx = mWidth / 2 - mDrawableWidth / 2;
        // 平移至顶部
        mScaleMatrix.postTranslate(dx, 0);
        // 以控件宽度的中心和高度的顶部作为缩放
        mScaleMatrix.postScale(initScale, initScale, mWidth / 2, 0);
        setImageMatrix(mScaleMatrix);
    }

    /**
     * 移动控件中间位置
     */
    private void moveToCenter() {
        final float dx = mWidth / 2 - mDrawableWidth / 2;
        final float dy = mHeight / 2 - mDrawableHeight / 2;
        // 平移至中心
        mScaleMatrix.postTranslate(dx, dy);
        // 以控件中心作为缩放
        mScaleMatrix.postScale(initScale, initScale, mWidth / 2, mHeight / 2);
        setImageMatrix(mScaleMatrix);
    }


    /**
     * 移动时，进行边界判断，主要判断宽或高大于屏幕的
     */
    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rect.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 是否是推动行为
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    /**
     * 设置最大放大倍数，单位当前控件的宽度
     * 不能小于1单位
     */
    public static void setScaleMax(float fold) {
        if (fold < 1) {
            return;
        }
        SCALE_MAX = fold * initScale;
    }

    /**
     * 设置最小放大倍数，单位当前控件的宽度
     * 不能大于1单位
     */
    public static void setScaleMin(float fold) {
        if (fold > 1) {
            return;
        }
        SCALE_MIN = fold * initScale;
    }

    public interface OnClickListener {
        void onClick();
    }

    public interface OnLongClickListener {
        boolean onLongClick();
    }


    public void setZoomOnClickLister(OnClickListener lister) {
        this.clickListener = lister;
    }

    public void setZoomOnLongClickLister(OnLongClickListener lister) {
        this.longClickListener = lister;
    }
}
