package com.quasar.demo.frame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * description: 基类Fragment
 * created by kalu on 17-10-16 上午10:28
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView<T> {

    private View rootView;

    private boolean isLoad;
    private boolean isActivityCreated;

    private T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

        if (rootView == null) rootView = inflater.inflate(initView(), null);

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) parent.removeView(rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter = initPresenter();
        initDataLocal();

        isActivityCreated = true;

        // 第一个fragment会调用
        if (!getUserVisibleHint()) return;
        initDataNet();
        isLoad = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isLoad && isActivityCreated) {
            initDataNet();
            isLoad = true;
        }
    }

    @Override
    public void initDataLocal() {
    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }

    /**********************************************************************************************/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {

        // 销毁数据
        if (null != mPresenter) {
            mPresenter.recycler();
        }

        super.onDetach();
    }

    @Override
    public void showToast(int strId) {

        Toast.makeText(getContext().getApplicationContext(), strId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String strId) {

        Toast.makeText(getContext().getApplicationContext(), strId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void go(Class<?> clazz) {

        startActivity(new Intent(getContext().getApplicationContext(), clazz));
    }

    @Override
    public void go(Intent intent) {

        startActivity(intent);
    }

    @Override
    public void goThenKill(Class<?> clazz) {

        startActivity(new Intent(getContext().getApplicationContext(), clazz));
        FragmentActivity activity = getActivity();
        if (null != activity) {
            activity.onBackPressed();
        }
    }

    @Override
    public void goThenKill(Intent intent) {

        startActivity(intent);
        FragmentActivity activity = getActivity();
        if (null != activity) {
            activity.onBackPressed();
        }
    }

    public View getView() {
        return rootView;
    }
}