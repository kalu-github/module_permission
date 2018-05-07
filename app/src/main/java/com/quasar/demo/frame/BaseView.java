package com.quasar.demo.frame;

import android.content.Intent;

/**
 * description: MVP ==> V接口
 * created by kalu on 2018/3/22 9:16
 */
public interface BaseView<T> {

    T initPresenter();

    T getPresenter();

    int initView();

    void initDataNet();

    void initDataLocal();

    void go(Intent intent);

    void go(Class<?> clazz);

    void goThenKill(Class<?> clazz);

    void goThenKill(Intent intent);

    void showToast(int strId);

    void showToast(String strId);
}