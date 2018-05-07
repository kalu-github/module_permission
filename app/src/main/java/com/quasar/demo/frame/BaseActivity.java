package com.quasar.demo.frame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * description: 基类Activity
 * created by kalu on 17-10-16 下午3:17
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView<T> {

    private T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOrientation();
        setContentView(initView());

        mPresenter = initPresenter();
        initDataLocal();
        initDataNet();
    }

    // 初始化横竖屏
    public void initOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 竖屏
    }

    @Override
    public void initDataLocal() {
    }

    @Override
    public void onBackPressed() {

        // 销毁数据
        if (null != mPresenter) {
            mPresenter.recycler();
        }
        super.onBackPressed();
    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }

    /**********************************************************************************************/

    @Override
    public void showToast(int strId) {
        Toast.makeText(getApplicationContext(), strId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String strId) {
        Toast.makeText(getApplicationContext(), strId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void go(Class<?> clazz) {

        startActivity(new Intent(getApplicationContext(), clazz));
    }

    @Override
    public void go(Intent intent) {

        startActivity(intent);
    }

    @Override
    public void goThenKill(Class<?> clazz) {

        startActivity(new Intent(getApplicationContext(), clazz));
        onBackPressed();
    }

    @Override
    public void goThenKill(Intent intent) {

        startActivity(intent);
        onBackPressed();
    }

    /**********************************************************************************************/
}
