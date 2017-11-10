package com.lxp.widgetview.loading;

/**
 * 加载的监听
 * Created by Li Xiaopeng on 17/11/1.
 */

public interface LoadingListener {

    /**
     * 开始加载
     */
    void startLoading();

    /**
     * 结束加载
     * @param status 用于区分是否成功
     */
    void endLoading(int status);
}
