package com.lxp.widgetview.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lxp.widgetview.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Activity mActivity = MainActivity.this;

    private Button btn_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btn_line = (Button) findViewById(R.id.btn_line);
        btn_line.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_line:
                Intent intent = new Intent(mActivity,CommonActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
