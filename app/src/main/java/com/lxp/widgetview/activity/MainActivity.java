package com.lxp.widgetview.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lxp.widgetview.R;
import com.lxp.widgetview.activity.bezier.activity.BezierActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Activity mActivity = MainActivity.this;

    private Button btn_line,btn_view,btn_drag,btn_draw,btn_bezier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btn_line = (Button) findViewById(R.id.btn_line);
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_drag = (Button) findViewById(R.id.btn_drag);
        btn_draw = (Button) findViewById(R.id.btn_draw);
        btn_bezier = (Button) findViewById(R.id.btn_bezier);
        btn_line.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_drag.setOnClickListener(this);
        btn_draw.setOnClickListener(this);
        btn_bezier.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_line:
                intent.setClass(mActivity,CommonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view:
                intent.setClass(mActivity,ViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_drag:
                intent.setClass(mActivity,DragActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_draw:
                intent.setClass(mActivity,DrawActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_bezier:
                intent.setClass(mActivity,BezierActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
