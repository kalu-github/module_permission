package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import lib.kalu.permission.core.listener.OnAnnotationChangeListener;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;
import lib.kalu.permission.core.support.SupportCheck;

/**
 * description: 当前类描述信息
 * created by kalu on 2018/5/13 1:08
 */
public final class WrapperActivity extends WrapperAbstract {

    private final Activity activity;

    public WrapperActivity(Activity activity) {
        this.activity = activity;
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
        return WrapperImp.TARGET_ACTIVITY;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void request() {

        final List<String> requestList = getPermission();
        if (null == requestList || requestList.isEmpty()) return;

        final int requestCode = getRequestCode();
        final Context context = getActivity().getApplicationContext();

        // 5.0
        if (SupportCheck.isUnderBuildL(context)) {
            Log.e("Permission", "activity(不需要处理权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final OnPermissionChangeListener api1 = getPermissionChangeListener();
            if (null != api1) {
                api1.onSucc(requestCode, requestList);
            } else {
                final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                if (null != api2) {
                    api2.onSucc(getActivity(), requestCode, requestList);
                } else {
                    api2.onFail(getActivity(), requestCode, requestList);
                }
            }
        }
        // 5.0-6.0
        else if (SupportCheck.isUnderBuildM(context)) {
            Log.e("Permission", "activity(需要处理国产机型权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
        }
        // 6.0
        else {
            Log.e("Permission", "activity(需要处理型权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final ArrayList<String> againList = new ArrayList<>();
            for (String name : requestList) {
                if (getActivity().shouldShowRequestPermissionRationale(name)) {
                    againList.add(name);
                }
            }

            if (!againList.isEmpty()) {
                final OnPermissionChangeListener api1 = getPermissionChangeListener();
                if (null != api1) {
                    api1.onAgain(requestCode, requestList);
                } else {
                    final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                    if (null != api2) {
                        api2.onAgain(getActivity(), requestCode, requestList);
                    }
                }
            }

            if (requestList.isEmpty()) return;
            final String[] strings = requestList.toArray(new String[requestList.size()]);
            ActivityCompat.requestPermissions(getActivity(), strings, requestCode);
        }
    }
}
