package com.lxp.widgetview.inflate.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lxp.widgetview.R;

/**
 * Created by Li Xiaopeng on 17/8/21.
 */

public class InflateTestView extends RelativeLayout {

    Button btn_one;
    Button btn_two;
    Button btn_three;
    Button btn_multiple;

    public InflateTestView(Context context) {
        super(context);
    }

    public InflateTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
    }


    public InflateTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
    }

    private void initInflate() {
        View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.activity_bezier, this);
        initView(inflateView);
    }

    private void initView(View inflateView) {
        btn_one = (Button) inflateView.findViewById(R.id.btn_one);
        btn_two = (Button) inflateView.findViewById(R.id.btn_two);
        btn_three = (Button) inflateView.findViewById(R.id.btn_three);
        btn_multiple = (Button) inflateView.findViewById(R.id.btn_multiple);
    }
}
