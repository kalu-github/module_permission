package com.quasar.demo.module.home;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.quasar.demo.BaseConstant;
import com.quasar.demo.R;
import com.quasar.demo.frame.BaseFragment;

import lib.kalu.permission.core.PermissionManager;
import lib.kalu.permission.core.intent.IntentType;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;

/**
 * description: 首页
 * created by kalu on 2018/3/19 16:14
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public int initView() {
        return R.layout.fragment_home;
    }


    @Override
    public void initDataLocal() {

        getView().findViewById(R.id.fragment_home_qrcode).setOnClickListener(v -> {

            PermissionManager.get(HomeFragment.this)
                    .setForce(true)
                    .setPermission(Manifest.permission.CAMERA)
                    .setCode(BaseConstant.PERMISSION_CAMERA)
                    .setPageType(IntentType.PLATFRRM_SETTING)
                    .setOnPermissionChangeListener(new OnPermissionChangeListener() {
                        @Override
                        public void onSucc(int code) {
                            Log.e("dsds", "cameraSucc ==> code = " + code);
                            Toast.makeText(getContext(), "获取相机权限成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(int code) {
                            Log.e("dsds", "cameraFail ==> code = " + code);
                            Toast.makeText(getContext(), "获取相机权限失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAgain(int code) {
                            Log.e("dsds", "onAgain ==> code = " + code);
                            Toast.makeText(getContext(), "获取相机权限, 弹窗", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDenied(int code, Intent intent) {
                            Log.e("dsds", "onDenied ==> code = " + code);
                            Toast.makeText(getContext(), "获取相机权限, 拒绝", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .request();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.onRequestPermissionsResult(getActivity(), requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void initDataNet() {
    }
}