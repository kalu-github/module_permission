package com.quasar.demo.module.doctor;

import com.quasar.demo.frame.BaseFragment;
import com.quasar.demo.R;

/**
 * description: 我的医生
 * created by kalu on 2018/3/19 16:14
 */
public class DoctorFragment extends BaseFragment<DoctorPresenter> implements DoctorView {

    @Override
    public int initView() {
        return R.layout.fragment_doctor;
    }

    @Override
    public DoctorPresenter initPresenter() {
        return new DoctorPresenter();
    }

    @Override
    public void initDataLocal() {
    }

    @Override
    public void initDataNet() {
    }
}