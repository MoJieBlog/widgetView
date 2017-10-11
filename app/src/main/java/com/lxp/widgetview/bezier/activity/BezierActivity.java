package com.lxp.widgetview.bezier.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lxp.utils.ToastUtils;
import com.lxp.widgetview.R;

public class BezierActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_one, btn_two, btn_circle, btn_multiple;

    private static final String TAG = "BezierActivity";
    private Activity mActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);

        init();
    }

    private void init() {
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_circle = (Button) findViewById(R.id.btn_circle);
        btn_multiple = (Button) findViewById(R.id.btn_multiple);

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_circle.setOnClickListener(this);
        btn_multiple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_one:
                intent = new Intent(mActivity,OneBezierActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_two:
                intent = new Intent(mActivity,TwoBezierActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_circle:
                intent = new Intent(mActivity,Circle2HeartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_multiple:
                ToastUtils.setToast(mActivity,"敬请期待");
                break;

        }
    }
}
