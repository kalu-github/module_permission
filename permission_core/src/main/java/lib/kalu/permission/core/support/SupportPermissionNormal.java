package lib.kalu.permission.core.support;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import lib.kalu.permission.core.intent.IntentType;
import lib.kalu.permission.core.listener.OnAnnotationChangeListener;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;
import lib.kalu.permission.core.platform.PlatformManager;
import lib.kalu.permission.core.wrapper.WrapperAbstract;
import lib.kalu.permission.core.wrapper.WrapperBase;
import lib.kalu.permission.core.wrapper.WrapperPermission;

/**
 * description: 当前类描述信息
 * created by kalu on 2018/5/3 11:50
 */
public class SupportPermissionNormal {

    public static void succAnnotation(WrapperPermission wrapper) {

        if (null == wrapper) return;

        final String name = wrapper.getClassName();
        if (TextUtils.isEmpty(name)) return;

        final OnAnnotationChangeListener listener = wrapper.getAnnotationListener(name);
        if (null == listener) return;

        if (wrapper.getTarget() == WrapperBase.TARGET_FRAGMENT) {
            listener.onSucc(wrapper.getFragment(), wrapper.getRequestCode());
        } else {
            listener.onSucc(wrapper.getActivity(), wrapper.getRequestCode());
        }
    }

    public final static void failAnnotation(WrapperPermission wrapper) {

        if (null == wrapper) return;
        final String name = wrapper.getClassName();
        if (TextUtils.isEmpty(name)) return;

        final OnAnnotationChangeListener listener = wrapper.getAnnotationListener(name);
        if (null == listener) return;

        if (SupportCheck.isDenied(wrapper)) {
            Activity activity = wrapper.getActivity();
            boolean androidPage = wrapper.getPageType() == IntentType.ANDROID_SETTING;
            Intent intent = androidPage ? PlatformManager.getAndroidSetting(activity) : PlatformManager.getPlatformSetting(activity);

            if (wrapper.getTarget() == WrapperBase.TARGET_FRAGMENT) {
                listener.onDenied(wrapper.getFragment(), wrapper.getRequestCode(), intent);
            } else {
                listener.onDenied(wrapper.getActivity(), wrapper.getRequestCode(), intent);
            }
        } else {

            if (wrapper.getTarget() == WrapperBase.TARGET_FRAGMENT) {
                listener.onFail(wrapper.getFragment(), wrapper.getRequestCode());
            } else {
                listener.onFail(wrapper.getActivity(), wrapper.getRequestCode());
            }
        }
    }

    public final static void succListener(WrapperPermission wrapper) {

        OnPermissionChangeListener listener = wrapper.getPermissionChangeListener();
        if (null != listener) {
            listener.onSucc(wrapper.getRequestCode());
        }

        requestNextPermission(wrapper);
    }

    public final static void failListener(WrapperPermission wrapper) {
        OnPermissionChangeListener listener = wrapper.getPermissionChangeListener();
        if (null == listener) return;

        if (SupportCheck.isDenied(wrapper)) {
            Activity activity = wrapper.getActivity();
            boolean androidPage = wrapper.getPageType() == IntentType.ANDROID_SETTING;
            Intent intent = androidPage
                    ? PlatformManager.getAndroidSetting(activity)
                    : PlatformManager.getPlatformSetting(activity);
            listener.onDenied(wrapper.getRequestCode(), intent);
        } else {
            listener.onFail(wrapper.getRequestCode());
        }

        requestNextPermission(wrapper);
    }

    private static void requestNextPermission(WrapperPermission wrapper) {

        if (null == wrapper) return;
        int nextCode = wrapper.getRequestCodes()[0];
        if (wrapper.getRequestCode() == nextCode) return;

        final HashMap<WrapperAbstract.Key, WeakReference<WrapperPermission>> map = WrapperAbstract.getWrapperMap();
        final WrapperAbstract.Key key = new WrapperAbstract.Key(wrapper.getActivity(), nextCode);

        final WeakReference<WrapperPermission> reference = map.get(key);
        if (null == reference) return;

        WrapperAbstract abstracts = (WrapperAbstract) reference.get();
        if (null == abstracts) return;

        abstracts.requestListener();
    }
}
