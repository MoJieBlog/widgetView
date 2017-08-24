package com.lxp.widgetview.zoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lxp.utils.ToastUtils;
import com.lxp.widgetview.R;
import com.lxp.widgetview.zoom.view.ZoomImageView;

public class WideImageActivity extends AppCompatActivity {

    ZoomImageView iv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wide_image);

        iv_test = (ZoomImageView) findViewById(R.id.iv_test);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_test.setScaleMax(2.0f);
                iv_test.setScaleMin(0.3f);
            }
        });

        iv_test.setZoomOnClickLister(new ZoomImageView.OnClickListener() {
            @Override
            public void onClick() {
                ToastUtils.setToast(WideImageActivity.this,"单击了");
            }
        });

        iv_test.setZoomOnLongClickLister(new ZoomImageView.OnLongClickListener() {
            @Override
            public boolean onLongClick() {
                ToastUtils.setToast(WideImageActivity.this,"长按了");
                return false;
            }
        });


    }
}
