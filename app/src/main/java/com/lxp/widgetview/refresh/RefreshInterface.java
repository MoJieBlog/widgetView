package com.lxp.widgetview.refresh;

/**
 * Created by Li Xiaopeng on 18/1/25.
 */

public interface RefreshInterface {
    /**
     * 移动时
     * @param moveOffset
     * @param isRefreshing
     */
    void onMoving(float moveOffset,boolean isRefreshing);

    /**
     * 刷新时
     */
    void onRefreshing();

    /**
     * 刷新完成
     */
    void onRefreshFinish();

    /**
     * 获取loadingView的高度
     *
     * 无论头部和底部都需要知道当控件被显示多少时松开时开始刷新
     * @return
     */
    int getLoadingViewHeight();
}
