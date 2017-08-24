package com.lxp.widgetview.zoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lxp.widgetview.R;
import com.lxp.widgetview.activity.MainActivity;
import com.lxp.widgetview.drag.activity.DragActivity;

public class ImageTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_wide;
    private Button btn_long;

    private Activity mActivity = ImageTypeActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_type);

        btn_wide = (Button) findViewById(R.id.btn_wide);
        btn_long = (Button) findViewById(R.id.btn_long);

        btn_long.setOnClickListener(this);
        btn_wide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_long:
                intent.setClass(mActivity,ZoomImageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_wide:
                intent.setClass(mActivity,WideImageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
