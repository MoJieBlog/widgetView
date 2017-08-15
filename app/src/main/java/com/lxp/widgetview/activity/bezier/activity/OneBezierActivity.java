package com.lxp.widgetview.activity.bezier.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxp.widgetview.R;
import com.lxp.widgetview.activity.bezier.view.OneBezierView;

import java.util.Timer;
import java.util.TimerTask;

public class OneBezierActivity extends AppCompatActivity {

    private OneBezierView bzone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_bezier);

        bzone = (OneBezierView) findViewById(R.id.bzone);



    }
}
