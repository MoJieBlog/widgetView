package com.lxp.widgetview.activity.bezier;

/**
 * 获取贝塞尔点
 * 举例
 * 假设第一个点是 x1 第二个点是x2 获取总长度2%的点
 * 则贝塞尔点 = x2+(x1-x2)*2/100
 * 即（1-2/100）*x2 +x1*2/100
 * Created by Li Xiaopeng on 17/8/14.
 */

public class BezierUitls {

    /**
     * 得到某一时刻贝塞尔点
     * @param x1 点1 的X坐标
     * @param x2 点2的X坐标
     * @param copies 当前是总份数的多少
     * @param total 总的份数
     * @return 返回的贝塞尔曲线的X坐标
     *
     */
    public static int getBezierPoiontX(int x1,int x2,int copies,int total){
        int bezierX = (x2-x1)*copies/total+x1;
        return bezierX;
    }
}
