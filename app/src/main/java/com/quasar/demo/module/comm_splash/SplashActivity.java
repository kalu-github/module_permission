package com.quasar.demo.module.comm_splash;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.quasar.demo.BaseConstant;
import com.quasar.demo.R;
import com.quasar.demo.frame.BaseActivity;
import com.quasar.demo.module.comm_main.MainActivity;

import java.util.List;

import lib.kalu.permission.annotation.PermissionAgain;
import lib.kalu.permission.annotation.PermissionDenied;
import lib.kalu.permission.annotation.PermissionFail;
import lib.kalu.permission.annotation.PermissionSucc;
import lib.kalu.permission.core.PermissionManager;
import lib.kalu.permission.core.intent.IntentType;

/**
 * description: 闪屏, 需要获取SD卡写入权限
 * created by kalu on 2018/4/4 10:33
 */
public class SplashActivity extends BaseActivity implements SplashView {

    @Override
    public Object initPresenter() {
        return null;
    }

    @Override
    public int initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_comm_splash;
    }

    @Override
    public void initDataLocal() {

//        goThenKill(MainActivity.class);

        PermissionManager.get(SplashActivity.this)
                .setForce(true)
                .setPermissionName(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .setPageType(IntentType.SYSTEM_SETTING)
                .setRequestCode(BaseConstant.PERMISSION_SD)
                .request();
    }

    @PermissionSucc(BaseConstant.PERMISSION_SD)
    public void onSucc(int code, List<String> list) {
        goThenKill(MainActivity.class);
        Log.e("dsds", "onSucc ==> code = " + code + ", list = " + list.toString());
        Toast.makeText(getApplicationContext(), "获取存储权限成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(BaseConstant.PERMISSION_SD)
    public void onFail(int code, List<String> list) {
        Toast.makeText(getApplicationContext(), "获取存储权限失败", Toast.LENGTH_SHORT).show();
        Log.e("dsds", "onFail ==> code = " + code + ", list = " + list.toString());
    }

    @PermissionAgain(BaseConstant.PERMISSION_SD)
    public void onAgain(int code, List<String> list) {
        Toast.makeText(getApplicationContext(), "获取存储权限, 弹窗", Toast.LENGTH_SHORT).show();
        Log.e("dsds", "onAgain ==> code = " + code + ", list = " + list.toString());
    }

    @PermissionDenied(BaseConstant.PERMISSION_SD)
    public void onDenied(int code, List<String> list, final Intent intent) {
        Toast.makeText(getApplicationContext(), "获取存储权限, 拒绝", Toast.LENGTH_SHORT).show();
        Log.e("dsds", "onDenied ==> code = " + code + ", list = " + list.toString() + ", action = " + intent.getAction());
    }

    @Override
    public void initDataNet() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.onRequestPermissionsResult(SplashActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}