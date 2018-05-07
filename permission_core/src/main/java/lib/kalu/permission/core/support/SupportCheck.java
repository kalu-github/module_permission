package lib.kalu.permission.core.support;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import lib.kalu.permission.core.wrapper.WrapperPermission;

/**
 * description: 检测用户是否点击, 在此显示权限对话框
 * created by kalu on 2018/5/2 18:35
 */
final class SupportCheck {

    final static boolean isDenied(WrapperPermission wrapper) {

        if (null == wrapper) return true;

        final Activity activity = wrapper.getActivity();
        if (null == activity) return true;

        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, wrapper.getRequestPermission());
    }
}
