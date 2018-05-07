package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import lib.kalu.permission.core.intent.IntentType;
import lib.kalu.permission.core.listener.ListenerWrapper;
import lib.kalu.permission.core.listener.OnAnnotationChangeListener;

public interface WrapperBase extends ListenerWrapper {

    int TARGET_ACTIVITY = 1;
    int TARGET_FRAGMENT = 2;

    String getClassName();

    Activity getActivity();

    Fragment getFragment();

    int getTarget();

    /**
     * 是否第二次弹出权限提示
     */
    boolean isAgain();

    /**
     * 第二次弹出权限提示
     */
    WrapperBase requestAgain();

    /**
     * 是否需要适配国产5.0
     */
    WrapperBase setUnderM(boolean underM);

    boolean isUnderM();

    /**
     * 是否强制弹出权限申请对话框
     */
    WrapperBase setForce(boolean force);

    boolean isForce();

    WrapperBase setCode(int... code);

    WrapperBase setPermission(String... permission);

    WrapperBase setPageType(@IntentType int pageType);

    void request();

    void requestSync();

    int[] getRequestCodes();

    String[] getRequestPermissions();

    @IntentType
    int getPageType();

    OnAnnotationChangeListener getAnnotationListener(String className);
}
