package lib.kalu.permission.core.support;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.permission.core.intent.IntentType;
import lib.kalu.permission.core.listener.OnAnnotationChangeListener;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;
import lib.kalu.permission.core.platform.PlatformManager;
import lib.kalu.permission.core.wrapper.WrapperImp;

/**
 * description: 权限检测
 * created by kalu on 2018/5/3 11:50
 */
public final class SupportPermission {

    public final static void premissionSucc(WrapperImp wrapper, List<String> list) {

        OnPermissionChangeListener api1 = wrapper.getPermissionChangeListener();
        Log.e("Permission", "premissionSucc ==> api1 = " + api1);
        if (null != api1) {
            Log.e("Permission", "premissionSucc ==> 监听");
            api1.onSucc(wrapper.getRequestCode(), list);
        }

        final String className = wrapper.getClassName();
        if (TextUtils.isEmpty(className)) return;

        final OnAnnotationChangeListener api2 = wrapper.getAnnotationChangeListener(className);
        if (null != api2) {
            if (wrapper.getTarget() == WrapperImp.TARGET_FRAGMENT) {
                api2.onSucc(wrapper.getFragment(), wrapper.getRequestCode(), list);
            } else {
                api2.onSucc(wrapper.getActivity(), wrapper.getRequestCode(), list);
            }
        }
    }

    public final static void premissionFail(WrapperImp wrapper, List<String> list) {

        final ArrayList<String> list1 = new ArrayList<>();
        final ArrayList<String> list2 = new ArrayList<>();

        for (String name : list) {

            Log.e("Permission", "premissionFail ==> 监听");
            if (SupportCheck.isDenied(wrapper, name)) {
                list1.add(name);
            } else {
                list2.add(name);
            }
        }

        if (list1.size() > 0) {
            Activity activity = wrapper.getActivity();
            boolean google = wrapper.getPageType() == IntentType.GOOGLE_SETTING;
            Intent intent = PlatformManager.getIntent(google, activity);

            OnPermissionChangeListener api1 = wrapper.getPermissionChangeListener();
            if (null != api1) {
                api1.onDenied(wrapper.getRequestCode(), list1, intent);
            }

            final OnAnnotationChangeListener api2 = wrapper.getAnnotationChangeListener(wrapper.getClassName());
            if (null != api2) {
                if (wrapper.getTarget() == WrapperImp.TARGET_FRAGMENT) {
                    api2.onDenied(wrapper.getFragment(), wrapper.getRequestCode(), list1, intent);
                } else {
                    api2.onDenied(wrapper.getActivity(), wrapper.getRequestCode(), list1, intent);
                }
            }
        }

        if (list2.size() > 0) {

            OnPermissionChangeListener api1 = wrapper.getPermissionChangeListener();
            if (null != api1) {
                api1.onFail(wrapper.getRequestCode(), list2);
            }

            final OnAnnotationChangeListener api2 = wrapper.getAnnotationChangeListener(wrapper.getClassName());
            if (null != api2) {
                if (wrapper.getTarget() == WrapperImp.TARGET_FRAGMENT) {
                    api2.onFail(wrapper.getFragment(), wrapper.getRequestCode(), list2);
                } else {
                    api2.onFail(wrapper.getActivity(), wrapper.getRequestCode(), list2);
                }
            }
        }
    }
}
