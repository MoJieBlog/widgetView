package com.lxp.widgetview.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxp.widgetview.R;
import com.lxp.widgetview.photoview.IPhotoView;
import com.lxp.widgetview.photoview.PicsViewPagerAdapter2;

public class PhotoViewActivity extends AppCompatActivity {

    ViewPager viewpager;
    PicsViewPagerAdapter2 mPicsViewPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        viewpager = (ViewPager) findViewById(R.id.pics_viewpager);
        mPicsViewPageAdapter = new PicsViewPagerAdapter2(getSupportFragmentManager());
        viewpager.setAdapter(mPicsViewPageAdapter);

        IPhotoView photoView = (IPhotoView) findViewById(R.id.iv_photo);
        photoView.setIsEnable(this,true);

    }
}
