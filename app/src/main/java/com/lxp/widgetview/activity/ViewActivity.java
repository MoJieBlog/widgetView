package com.lxp.widgetview.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lxp.widgetview.R;
import com.lxp.widgetview.timer.activity.TimeViewActivity;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewActivity";
    private Activity mActivity = ViewActivity.this;

    Button btn_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        btn_time = (Button) findViewById(R.id.btn_time);
        btn_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_time:
                Intent intent = new Intent(mActivity,TimeViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
