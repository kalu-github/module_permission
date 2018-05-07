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
        requestPermission();
    }

    @PermissionSucc(BaseConstant.PERMISSION_SD)
    public void cardSucc(int code) {
        goThenKill(MainActivity.class);
        Log.e("dsds", "onSucc ==> code = " + code);
        Toast.makeText(getApplicationContext(), "获取录音权限成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(BaseConstant.PERMISSION_SD)
    public void cardFail(int code) {
        Toast.makeText(getApplicationContext(), "获取录音权限失败", Toast.LENGTH_SHORT).show();
        Log.e("dsds", "onFail ==> code = " + code);
    }

    @PermissionAgain(BaseConstant.PERMISSION_SD)
    public void cardAgainNormal(int code) {
        Toast.makeText(getApplicationContext(), "获取录音权限, 弹窗", Toast.LENGTH_SHORT).show();
        Log.e("dsds", "onAgain ==> code = " + code);
    }

    @PermissionDenied(BaseConstant.PERMISSION_SD)
    public void cardAgainRefuse(int code, final Intent intent) {
        Toast.makeText(getApplicationContext(), "获取录音权限, 拒绝", Toast.LENGTH_SHORT).show();
        Log.e("dsds", "onDenied ==> code = " + code);
    }

    @Override
    public void initDataNet() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.onRequestPermissionsResult(SplashActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void requestPermission() {
        PermissionManager.get(SplashActivity.this)
                .setForce(true)
                .setUnderM(true)
                .setPermission(Manifest.permission.RECORD_AUDIO)
                .setPageType(IntentType.PLATFRRM_SETTING)
                .setCode(BaseConstant.PERMISSION_SD)
                .request();
    }
}