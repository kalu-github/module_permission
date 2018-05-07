package lib.kalu.permission.core.support;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import lib.kalu.permission.core.intent.IntentType;
import lib.kalu.permission.core.listener.OnAnnotationChangeListener;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;
import lib.kalu.permission.core.platform.PlatformManager;
import lib.kalu.permission.core.platform.PlatformSupport;
import lib.kalu.permission.core.wrapper.WrapperPermission;
import lib.kalu.permission.core.wrapper.WrapperBase;

/**
 * description: 当前类描述信息
 * created by kalu on 2018/5/2 18:22
 */
public class SupportPermissionForce {

    public static void succListener(WrapperPermission wrapper) {
        if (PlatformSupport.isPermissionGranted(wrapper.getActivity(), wrapper.getRequestPermission()))
            SupportPermissionNormal.succListener(wrapper);
        else
            SupportPermissionNormal.failListener(wrapper);
    }

    public static void succAnnotation(WrapperPermission wrapper) {
        if (PlatformSupport.isPermissionGranted(wrapper.getActivity(), wrapper.getRequestPermission())) {
            SupportPermissionNormal.succAnnotation(wrapper);
        } else {
            SupportPermissionNormal.failAnnotation(wrapper);
        }
    }

    public final static void deniedWithListenerForUnderM(WrapperPermission wrapper) {
        Activity activity = wrapper.getActivity();
        OnPermissionChangeListener listener = wrapper.getPermissionChangeListener();
        if (listener == null) return;

        if (SupportCheck.isDenied(wrapper)) {
            boolean androidPage = wrapper.getPageType() == IntentType.ANDROID_SETTING;
            Intent intent = androidPage ? PlatformManager.getAndroidSetting(activity) :
                    PlatformManager.getPlatformSetting(activity);
            listener.onDenied(wrapper.getRequestCode(), intent);
        } else {
            listener.onFail(wrapper.getRequestCode());
        }
    }

    public final static void deniedWithAnnotationForUnderM(WrapperPermission wrapper) {

        String name = wrapper.getActivity().getClass().getName();
        OnAnnotationChangeListener listener = wrapper.getAnnotationListener(name);

        if (SupportCheck.isDenied(wrapper)) {
            final Activity activity = wrapper.getActivity();
            boolean androidPage = wrapper.getPageType() == IntentType.ANDROID_SETTING;
            Intent intent = androidPage ? PlatformManager.getAndroidSetting(activity) :
                    PlatformManager.getPlatformSetting(activity);

            listener.onDenied(wrapper.getActivity(), wrapper.getRequestCode(), intent);
        } else {
            listener.onFail(wrapper.getActivity(), wrapper.getRequestCode());
        }
    }
}
