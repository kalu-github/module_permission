package com.quasar.demo.module.comm_main;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Toast;

import com.quasar.demo.frame.BaseActivity;
import com.quasar.demo.R;
import com.quasar.demo.widget.TabMenuLayout;

/**
 * description: 入口
 * created by kalu on 2018/4/2 16:01
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    public static final String H5_URL = "url";
    private long currTime = Activity.DEFAULT_KEYS_DISABLE;

    @Override
    public int initView() {
        return R.layout.activity_comm_main;
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public void initDataNet() {

        final String stringExtra = getIntent().getStringExtra(H5_URL);
        if (!TextUtils.isEmpty(stringExtra)) {
            Toast.makeText(getApplicationContext(), stringExtra, Toast.LENGTH_SHORT).show();
        }

        MainPresenter presenter = getPresenter();
        if (null == presenter) return;
        presenter.initAdapter(this, getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {

        long secondTime = System.currentTimeMillis();
        if (secondTime - currTime > 2000) {
            currTime = secondTime;
            showToast("再次点击退出应用");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setPagerAdapter(PagerAdapter adapter) {
        ViewPager pager = findViewById(R.id.main_viewpager);
        if (null == pager) return;
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);
    }

    @Override
    public void setMenuPager() {

        TabMenuLayout menu = findViewById(R.id.main_tabmenu);
        if (null == menu) return;
        ViewPager pager = findViewById(R.id.main_viewpager);
        if (null == pager) return;

        menu.setViewPager(pager);
        menu.setBadgeMessage(2, 15);
        menu.setBadgeMessage(3);
        menu.setOnTabMenuChangedListener((isSwitch, isClick, isClickSelected, position) -> {

        });
    }
}