package com.lxp.widgetview.loading.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lxp.widgetview.R;

public class LoadingActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext = LoadingActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        init();
    }

    private void init() {
    }

    @Override
    public void onClick(View v) {

    }
}
