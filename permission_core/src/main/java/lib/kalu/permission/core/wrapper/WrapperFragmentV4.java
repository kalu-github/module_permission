package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import lib.kalu.permission.core.listener.OnAnnotationChangeListener;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;

public final class WrapperFragmentV4 extends WrapperAbstract implements WrapperBase {

    private final android.support.v4.app.Fragment mFragment;

    public WrapperFragmentV4(android.support.v4.app.Fragment fragmentV4) {
        this.mFragment = fragmentV4;
    }

    @Override
    void originalRequest() {
        String permission = getRequestPermission();
        int code = getRequestCode();
        mFragment.requestPermissions(new String[]{permission}, code);
    }

    @Override
    final void beginAnnotation() {

        final String name = mFragment.getClass().getName();
        // Log.e("WrapperFragmentV4", "beginAnnotation ==>  name = "+name);

        if (TextUtils.isEmpty(name)) return;

        final int requestCode = getRequestCode();
        final String requestPermission = getRequestPermission();
        if (TextUtils.isEmpty(requestPermission)) return;

        final boolean result = mFragment.shouldShowRequestPermissionRationale(requestPermission);
        if (result) {

            final OnAnnotationChangeListener api = getAnnotationListener(name);
            if (null == api) return;

            api.onAgain(mFragment, requestCode);
            originalRequest();
        } else {
            originalRequest();
        }
    }

    @Override
    final void beginListener() {

        int requestCode = getRequestCode();
        String requestPermission = getRequestPermission();
        OnPermissionChangeListener requestListener = getPermissionChangeListener();

        if (mFragment.shouldShowRequestPermissionRationale
                (requestPermission)) {
            requestListener.onAgain(requestCode);
            originalRequest();
        } else {
            originalRequest();
        }
    }

    @Override
    public void requestSync() {
        requestSync(mFragment);
    }

    @Override
    public String getClassName() {
        return mFragment.getClass().getName();
    }

    @Override
    public Activity getActivity() {
        return mFragment.getActivity();
    }

    @Override
    public Fragment getFragment() {
        return mFragment;
    }

    @Override
    public int getTarget() {
        return WrapperBase.TARGET_FRAGMENT;
    }
}
