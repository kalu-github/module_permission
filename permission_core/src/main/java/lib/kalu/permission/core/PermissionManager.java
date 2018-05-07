package lib.kalu.permission.core;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import lib.kalu.permission.core.listener.OnPermissionChangeListener;
import lib.kalu.permission.core.support.SupportPermissionForce;
import lib.kalu.permission.core.support.SupportPermissionNormal;
import lib.kalu.permission.core.wrapper.WrapperPermission;
import lib.kalu.permission.core.wrapper.WrapperAbstract;
import lib.kalu.permission.core.wrapper.WrapperBase;
import lib.kalu.permission.core.wrapper.WrapperActivity;
import lib.kalu.permission.core.wrapper.WrapperFragmentV4;

/**
 * description: 权限管理
 * created by kalu on 2017/12/16 16:06
 */
public final class PermissionManager {

    public static WrapperBase get(Activity activity) {
        return new WrapperActivity(activity);
    }

    public static WrapperBase get(android.support.v4.app.Fragment fragment) {
        return new WrapperFragmentV4(fragment);
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults) {

        if (null == activity || null == grantResults || grantResults.length <= 0) return;

        WrapperAbstract.Key key = new WrapperAbstract.Key(activity, requestCode);
        if (null == key) return;

        final HashMap<WrapperAbstract.Key, WeakReference<WrapperPermission>> map = WrapperAbstract.getWrapperMap();
        if (null == map) return;

        final WeakReference<WrapperPermission> reference = map.get(key);
        if (null == reference) return;

        WrapperPermission wrapper = reference.get();
        if (null == wrapper) return;

        OnPermissionChangeListener listener = wrapper.getPermissionChangeListener();

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (wrapper.isForce()) {
                if (null == listener) {
                    SupportPermissionForce.succAnnotation(wrapper);
                } else {
                    SupportPermissionForce.succListener(wrapper);
                }
            } else {
                if (null == listener) {
                    SupportPermissionNormal.succAnnotation(wrapper);
                } else {
                    SupportPermissionNormal.succListener(wrapper);
                }
            }
        } else {
            if (null == listener) {
                SupportPermissionNormal.failAnnotation(wrapper);
            } else {
                SupportPermissionNormal.failListener(wrapper);
            }
        }
    }

    public static void onRequestPermissionsResult(android.support.v4.app.Fragment fragment, int requestCode, @NonNull int[] grantResults) {

        if (null == fragment || null == grantResults || grantResults.length <= 0) return;

        WrapperAbstract.Key key = new WrapperAbstract.Key(fragment, requestCode);
        if (null == key) return;

        final HashMap<WrapperAbstract.Key, WeakReference<WrapperPermission>> map = WrapperAbstract.getWrapperMap();
        if (null == map) return;

        final WeakReference<WrapperPermission> reference = map.get(key);
        if (null == reference) return;

        WrapperPermission wrapper = reference.get();
        if (null == wrapper) return;

        OnPermissionChangeListener listener = wrapper.getPermissionChangeListener();

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (wrapper.isForce()) {
                if (null == listener) {
                    SupportPermissionForce.succAnnotation(wrapper);
                } else {
                    SupportPermissionForce.succListener(wrapper);
                }
            } else {
                if (null == listener) {
                    SupportPermissionNormal.succAnnotation(wrapper);
                } else {
                    SupportPermissionNormal.succListener(wrapper);
                }
            }
        } else {
            if (null == listener) {
                SupportPermissionNormal.failAnnotation(wrapper);
            } else {
                SupportPermissionNormal.failListener(wrapper);
            }
        }
    }

    public static void requestPermission(Activity activity, String permission, int requestCode) {
        new WrapperActivity(activity)
                .setPermission(permission)
                .setCode(requestCode)
                .request();
    }

    public static void requestPermission(android.support.v4.app.Fragment fragment, String permission, int requestCode) {
        new WrapperFragmentV4(fragment)
                .setPermission(permission)
                .setCode(requestCode)
                .request();
    }
}
