package com.lxp.widgetview.photoview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dreamtrip on 15/5/4.
 * fix  Issue 18990
 */
public class ViewPagerFixed extends ViewPager {

  private boolean enabledScroll;

  public ViewPagerFixed(Context context) {
    super(context);
    enabledScroll = true;
  }

  public ViewPagerFixed(Context context, AttributeSet attrs) {
    super(context, attrs);
    enabledScroll = true;
  }

  public boolean isEnabledScroll() {
    return enabledScroll;
  }

  public void setEnabledScroll(boolean enabledScroll) {
    this.enabledScroll = enabledScroll;
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {

    if (enabledScroll) {
      try {
        return super.onTouchEvent(ev);
      } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
      }
    }
    return false;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (enabledScroll) {
      try {
        return super.onInterceptTouchEvent(ev);
      } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
      }
    }

    return false;
  }
}
