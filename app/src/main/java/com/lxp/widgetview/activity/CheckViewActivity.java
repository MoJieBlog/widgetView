package com.lxp.widgetview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lxp.widgetview.CheckView;
import com.lxp.widgetview.R;

public class CheckViewActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckView checkView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_view);

        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.repeat).setOnClickListener(this);
        checkView = (CheckView) findViewById(R.id.checkView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                checkView.start();
                break;
            case R.id.stop:
                checkView.stop();
                break;
            case R.id.repeat:
                checkView.repeat();
                break;
        }
    }
}
