package com.quasar.demo.module.home;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.quasar.demo.BaseConstant;
import com.quasar.demo.R;
import com.quasar.demo.frame.BaseFragment;

import java.util.List;

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
                    .setPermissionName(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                    .setRequestCode(BaseConstant.PERMISSION_CAMERA)
                    .setPageType(IntentType.GOOGLE_SETTING)
                    .setOnPermissionChangeListener(new OnPermissionChangeListener() {
                        @Override
                        public void onSucc(int code, List<String> list) {
                            Log.e("dsds", "cameraSucc ==> code = " + code + ", name = " + list.toString());
                            Toast.makeText(getContext(), "获取相机权限成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(int code, List<String> list) {
                            Log.e("dsds", "cameraFail ==> code = " + code + ", name = " + list.toString());
                            Toast.makeText(getContext(), "获取相机权限失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAgain(int code, List<String> list) {
                            Log.e("dsds", "onAgain ==> code = " + code + ", name = " + list.toString());
                            Toast.makeText(getContext(), "获取相机权限, 弹窗", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDenied(int code, List<String> list, Intent intent) {
                            Log.e("dsds", "onDenied ==> code = " + code + ", name = " + list.toString() + ", action = " + intent.getAction());
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