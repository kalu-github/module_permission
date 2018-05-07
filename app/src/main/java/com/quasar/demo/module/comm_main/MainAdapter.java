package com.quasar.demo.module.comm_main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.quasar.demo.frame.BaseFragment;
import com.quasar.demo.module.discover.DiscoverFragment;
import com.quasar.demo.module.doctor.DoctorFragment;
import com.quasar.demo.module.home.HomeFragment;
import com.quasar.demo.module.mine.MineFragment;

/**
 * description: 入口
 * created by kalu on 2018/4/2 16:01
 */
public class MainAdapter extends FragmentPagerAdapter {

    private final BaseFragment[] mBaseFragment = new BaseFragment[]{
            new HomeFragment(),
            new DoctorFragment(),
            new DiscoverFragment(),
            new MineFragment()
    };

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragment[position];
    }
}