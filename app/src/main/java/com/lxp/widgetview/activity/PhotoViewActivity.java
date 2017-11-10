package com.lxp.widgetview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.lxp.widgetview.R;
import com.lxp.widgetview.photoview.PhotoView;
import com.lxp.widgetview.photoview.PicsViewPagerAdapter2;
import com.lxp.widgetview.photoview.ViewPagerFixed;

public class PhotoViewActivity extends AppCompatActivity {

    ViewPagerFixed viewpager;
    PicsViewPagerAdapter2 mPicsViewPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        viewpager = (ViewPagerFixed) findViewById(R.id.pics_viewpager);
        mPicsViewPageAdapter = new PicsViewPagerAdapter2(getSupportFragmentManager());
        viewpager.setAdapter(mPicsViewPageAdapter);

    }
}
