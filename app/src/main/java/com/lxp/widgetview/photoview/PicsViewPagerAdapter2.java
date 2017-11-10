package com.lxp.widgetview.photoview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Li Xiaopeng on 17/11/7.
 */

public class PicsViewPagerAdapter2 extends FragmentStatePagerAdapter {


    @NonNull
    private int position;

    public PicsViewPagerAdapter2(FragmentManager fm) {
        super(fm);
    }


    @Nullable
    @Override
    public Fragment getItem(int position) {
        PicViewFragment2 fragment = PicViewFragment2.getFragment();
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 5;
    }

    private boolean isValidPosition(int position) {
        return (position >= 0 && position < getCount());
    }

    private Fragment mCurrentFragment;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);

    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
