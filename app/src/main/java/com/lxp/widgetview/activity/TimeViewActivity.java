package com.lxp.widgetview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lxp.widgetview.R;
import com.lxp.widgetview.view.TimerView;

public class TimeViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TimeViewActivity";
    private Activity mActivity = TimeViewActivity.this;

    TimerView tv_1;
    TimerView tv_2;

    Button btn_start,btn_pause,btn_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_view);

        tv_1 = (TimerView) findViewById(R.id.tv_1);
        tv_2 = (TimerView) findViewById(R.id.tv_2);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_restart = (Button) findViewById(R.id.btn_restart);

        btn_pause.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_restart.setOnClickListener(this);

        tv_1.setOnTimeCompleteListener(new TimerView.OnTimeCompleteListener() {
            @Override
            public void onTimeComplete() {

            }

            @Override
            public void onTime() {

            }
        });
        tv_2.setOnTimeCompleteListener(new TimerView.OnTimeCompleteListener() {
            @Override
            public void onTimeComplete() {

            }

            @Override
            public void onTime() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                long currentTimeMillis = System.currentTimeMillis();
                long currentTimeMillis_next = System.currentTimeMillis()+61*1000;
                tv_1.initTime(currentTimeMillis_next,0);
                tv_2.initTime(currentTimeMillis,1);
                /*tv_2.start();
                tv_1.start();*/

                break;
            case R.id.btn_pause:
                tv_1.onPause();
                tv_2.onPause();
                break;
            case R.id.btn_restart:
                tv_1.reStart();
                tv_2.reStart();
                break;
        }
    }
}
