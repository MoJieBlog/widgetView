package com.lxp.widgetview.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.lxp.utils.LogUtils;
import com.lxp.widgetview.R;
import com.lxp.widgetview.bezier.activity.BezierActivity;
import com.lxp.widgetview.common.activity.CommonActivity;
import com.lxp.widgetview.drag.activity.DragActivity;
import com.lxp.widgetview.draw.activity.DrawActivity;
import com.lxp.widgetview.inflate.activity.InflateTestActivity;
import com.lxp.widgetview.loading.activity.LoadingActivity;
import com.lxp.widgetview.shape.actvity.ShapeActivity;
import com.lxp.widgetview.zoom.activity.ImageTypeActivity;
import com.lxp.widgetview.zoom.activity.ZoomImageActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Activity mActivity = MainActivity.this;

    private Button btn_line, btn_view, btn_drag, btn_draw, btn_bezier, btn_inflate, btn_zoom;
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_line = (Button) findViewById(R.id.btn_line);
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_drag = (Button) findViewById(R.id.btn_drag);
        btn_draw = (Button) findViewById(R.id.btn_draw);
        btn_bezier = (Button) findViewById(R.id.btn_bezier);
        btn_inflate = (Button) findViewById(R.id.btn_inflate);
        btn_zoom = (Button) findViewById(R.id.btn_zoom);
        btn_line.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_drag.setOnClickListener(this);
        btn_draw.setOnClickListener(this);
        btn_bezier.setOnClickListener(this);
        btn_inflate.setOnClickListener(this);
        btn_zoom.setOnClickListener(this);

        LogUtils.logE(TAG, "init: "+Integer.parseInt("111111",2));//二进制转十进制
        LogUtils.logE(TAG, "init: "+Integer.toBinaryString(63));//十进制转二进制
        LogUtils.logE(TAG, "init: "+Integer.toOctalString(63));//十进制转八进制
        LogUtils.logE(TAG, "init: "+Integer.toHexString(63));//十进制转十六进制

        findViewById(R.id.btn_loading).setOnClickListener(this);
        findViewById(R.id.btn_shape).setOnClickListener(this);
        findViewById(R.id.btn_photoView).setOnClickListener(this);
        findViewById(R.id.checkView).setOnClickListener(this);

        doAnimation(tv_text);
    }

    /**
     * 做呼吸灯动画
     *
     * @param view
     */
    private void doAnimation(View view) {
        tv_text.setText("6666666666666666666");
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator fade = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f,1.0f);
        fade.setDuration(3000);
        /*fade.setRepeatMode(ValueAnimator.REVERSE);
        fade.setRepeatCount(1);*/
        fade.setInterpolator(new AccelerateDecelerateInterpolator());
        fade.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                tv_text.setText("7777777777777777");
            }
        });

        set.playSequentially(fade);
        set.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int measuredHeight = tv_text.getMeasuredHeight();
            int measuredWidth = tv_text.getMeasuredWidth();
            Shader shader = new LinearGradient(0, 0, measuredWidth / 5, measuredHeight / 5, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);
            tv_text.getPaint().setShader(shader);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_line:
                intent.setClass(mActivity, CommonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view:
                intent.setClass(mActivity, ViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_drag:
                intent.setClass(mActivity, DragActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_draw:
                intent.setClass(mActivity, DrawActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_bezier:
                intent.setClass(mActivity, BezierActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_inflate:
                intent.setClass(mActivity, InflateTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_zoom:
                intent.setClass(mActivity, ImageTypeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_loading:
                intent.setClass(mActivity, LoadingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_shape:
                intent.setClass(mActivity, ShapeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_photoView:
                intent.setClass(mActivity, PhotoViewActivity.class);
                startActivity(intent);
                break;
            case R.id.checkView:
                intent.setClass(mActivity, CheckViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
