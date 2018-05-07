package com.quasar.demo.module.comm_main;

import android.support.v4.app.FragmentManager;

import com.quasar.demo.frame.BasePresenter;

/**
 * description: 入口
 * created by kalu on 2018/4/2 16:01
 */
public class MainPresenter implements BasePresenter {

    void initAdapter(MainView view, FragmentManager manager) {

        if (null == view) return;

        MainAdapter mainAdapter = new MainAdapter(manager);
        view.setPagerAdapter(mainAdapter);
        view.setMenuPager();
    }

    @Override
    public void recycler() {
    }
}
