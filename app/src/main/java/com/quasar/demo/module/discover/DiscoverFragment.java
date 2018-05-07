package com.quasar.demo.module.discover;

import com.quasar.demo.frame.BaseFragment;
import com.quasar.demo.R;

/**
 * description: 发现
 * created by kalu on 2018/4/2 16:12
 */
public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverView {

    @Override
    public int initView() {
        return R.layout.fragment_discover;
    }

    @Override
    public DiscoverPresenter initPresenter() {
        return new DiscoverPresenter();
    }

    @Override
    public void initDataLocal() {
    }

    @Override
    public void initDataNet() {
    }
}