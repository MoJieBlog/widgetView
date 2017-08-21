package com.lxp.widgetview.drag.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.lxp.utils.UiUtils;
import com.lxp.widgetview.R;

public class DragActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = "DragActivity";
    private Activity mActivity = DragActivity.this;

    private ImageView iv_drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        init();
    }

    private void init() {
        iv_drag = (ImageView) findViewById(R.id.iv_drag);

        iv_drag.setOnTouchListener(this);
    }

    private int lastX = 0;
    private int lasty = 0;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //屏幕宽高
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = UiUtils.getDisHeight(mActivity);


        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) motionEvent.getRawX();
                lasty = (int) motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (motionEvent.getRawX() - lastX);
                int dy = (int) (motionEvent.getRawY() - lasty);

                int left = view.getLeft() + dx;
                int right = view.getRight() + dx;
                int top = view.getTop() + dy;
                int bottom = view.getBottom() + dy;

                if (left < 0) {
                    left = 0;
                    right = left + view.getWidth();
                }


                if (right > widthPixels) {
                    right = widthPixels;
                    left = right - view.getWidth();
                }

                if (top < 0) {
                    top = 0;
                    bottom = top + view.getHeight();
                }
                if (bottom > heightPixels) {
                    bottom = heightPixels;
                    top = bottom - view.getHeight();
                }

                view.layout(left, top, right, bottom);
                lastX = (int) motionEvent.getRawX();
                lasty = (int) motionEvent.getRawY();
                view.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
}
