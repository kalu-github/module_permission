package com.quasar.demo.module.mine;

import com.quasar.demo.frame.BaseFragment;
import com.quasar.demo.R;

/**
 * description: 我
 * created by kalu on 2018/4/2 11:03
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineView {

    public final static int REQUEST_CODE = 1111;
    public final static int RESULT_CODE = 1112;

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    public int initView() {
        // getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.fragment_mine;
    }

    @Override
    public void initDataLocal() {
    }

    @Override
    public void initDataNet() {
    }
}