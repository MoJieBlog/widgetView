package com.lxp.widgetview.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxp.widgetview.R;

public class CommonActivity extends AppCompatActivity {

    private static final String TAG = "CommonActivity";
    private Activity mActivity = CommonActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_1);
    }
}
