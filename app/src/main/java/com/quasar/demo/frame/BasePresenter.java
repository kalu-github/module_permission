package com.quasar.demo.frame;

/**
 * description: 解除订阅回调
 * created by kalu on 17-10-16 下午5:07
 */
public interface BasePresenter {

    /**
     * 销毁数据, 例如取消当前页面的网络请求, 清除临时的数据集合
     */
    void recycler();
}