package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import lib.kalu.permission.core.listener.OnAnnotationChangeListener;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;

public final class WrapperActivity extends WrapperAbstract implements WrapperBase {

    private final Activity activity;

    public WrapperActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void requestSync() {
        requestSync(activity);
    }

    @Override
    void originalRequest() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{getRequestPermission()},
                getRequestCode());
    }

    @Override
    final void beginAnnotation() {
        int requestCode = getRequestCode();

        final Activity activity = getActivity();
        if (null == activity) return;

        final String requestPermission = getRequestPermission();
        if (TextUtils.isEmpty(requestPermission)) return;

        boolean ok = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            ok = activity.shouldShowRequestPermissionRationale(requestPermission);
        }
        if (ok) {

            final String name = activity.getClass().getName();
            if (TextUtils.isEmpty(name)) return;
            Log.e("WrapperActivity", "beginAnnotation ==>  name = " + name);

            final OnAnnotationChangeListener api = getAnnotationListener(name);
            if (null == api) return;

            api.onAgain(activity, requestCode);
            ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
        }
    }

    @Override
    final void beginListener() {

        int requestCode = getRequestCode();

        String requestPermission = getRequestPermission();
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
            OnPermissionChangeListener requestListener = getPermissionChangeListener();
            if (requestListener != null) {
                requestListener.onAgain(requestCode);
            }
        }
        ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
    }

    @Override
    public String getClassName() {
        return activity.getClass().getName();
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public int getTarget() {
        return WrapperBase.TARGET_ACTIVITY;
    }
}
