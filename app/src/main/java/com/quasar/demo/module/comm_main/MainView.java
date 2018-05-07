package com.quasar.demo.module.comm_main;

import android.support.v4.view.PagerAdapter;

/**
 * description: 入口
 * created by kalu on 2018/4/2 16:01
 */
public interface MainView {

    void setPagerAdapter(PagerAdapter adapter);

    void setMenuPager();
}
