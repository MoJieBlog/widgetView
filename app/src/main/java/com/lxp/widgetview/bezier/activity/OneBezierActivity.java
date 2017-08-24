package com.lxp.widgetview.bezier.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxp.widgetview.R;
import com.lxp.widgetview.bezier.view.OneBezierView;

public class OneBezierActivity extends AppCompatActivity {

    private OneBezierView bzone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_bezier);

        bzone = (OneBezierView) findViewById(R.id.bzone);



    }
}
