package com.lxp.widgetview.timer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

import com.lxp.utils.TimeUtils;

/**
 * 计时View
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class TimerView extends Chronometer {

    private static final String TAG = "TimerView";
    private int mode = MODE_ADD;//0:倒计时  1:计时
    private long startTime;

    private OnTimeCompleteListener mListener;

    public static final int MODE_ADD = 1;//计时
    public static final int MODE_REDUCE = 0;//倒计时

    public TimerView(Context context) {
        super(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnChronometerTickListener(new mChronometerTickListener());
    }

    /**
     * 初始化
     *
     * @param start_time 开始计时的时间，单位毫秒
     * @param mode       0:倒计时  1:计时
     */
    public void initTime(long start_time, int mode) {
        this.mode = mode;
        this.startTime = start_time;
        start();
    }


    public void onPause() {
        stop();
    }

    /**
     * 暂停后重新开始
     */
    public void reStart() {
        /**
         * 任务还是会执行一次，所以+1
         */
        initTime(startTime+1,mode);
        this.start();
    }



    class mChronometerTickListener implements OnChronometerTickListener {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (mode == MODE_REDUCE) {
                startTime--;
            } else if (mode == MODE_ADD) {
                startTime++;
            }

            updateTime();
        }
    }

    private void updateTime() {
        long currentTimeMillis = System.currentTimeMillis();
        String s = "";
        if (mode == MODE_REDUCE) {
            s = TimeUtils.dateCompare(currentTimeMillis, startTime);
            if (mListener != null) {
                mListener.onTime();
            }
            if (s.equals("00:00:00")) {//倒计时结束
                if (mListener != null) {
                    stop();
                    mListener.onTimeComplete();
                }
            }
        } else if (mode == MODE_ADD) {
            s = TimeUtils.dateCompare(startTime, currentTimeMillis);
            if (mListener != null) {
                mListener.onTime();
            }
        }


        this.setText(s);
    }

    public interface OnTimeCompleteListener {
        void onTimeComplete();

        void onTime();
    }

    public void setOnTimeCompleteListener(OnTimeCompleteListener listener) {
        mListener = listener;
    }
}
