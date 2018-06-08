package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.content.Context;
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
public final class WrapperFragmentV4 extends WrapperAbstract {

    private final android.support.v4.app.Fragment mFragment;

    public WrapperFragmentV4(android.support.v4.app.Fragment fragmentV4) {
        this.mFragment = fragmentV4;
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
        return WrapperImp.TARGET_FRAGMENT;
    }

    @Override
    public void request() {

        final List<String> requestList = getPermission();
        if (null == requestList || requestList.isEmpty()) return;

        final int requestCode = getRequestCode();
        final Context context = getActivity().getApplicationContext();

        // 5.0
        if (SupportCheck.isUnderBuildL(context)) {
            Log.e("Permission", "fragment(不需要处理权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final OnPermissionChangeListener api1 = getPermissionChangeListener();
            if (null != api1) {
                api1.onSucc(requestCode, requestList);
            } else {
                final OnAnnotationChangeListener api2 = getAnnotationChangeListener(getClassName());
                if (null != api2) {
                    api2.onSucc(getFragment(), requestCode, requestList);
                } else {
                    api2.onFail(getFragment(), requestCode, requestList);
                }
            }
        }
        // 5.0-6.0
        else if (SupportCheck.isUnderBuildM(context)) {
            Log.e("Permission", "fragment(需要处理国产机型权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
        }
        // 6.0
        else {
            Log.e("Permission", "fragment(需要处理型权限问题) ==> " + SupportCheck.getBuildVersion(context) + ", " + SupportCheck.getSystemVersion());
            final ArrayList<String> againList = new ArrayList<>();
            for (String name : requestList) {
                if (getFragment().shouldShowRequestPermissionRationale(name)) {
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
                        api2.onAgain(getFragment(), requestCode, requestList);
                    }
                }
            }

            if (requestList.isEmpty()) return;
            final String[] strings = requestList.toArray(new String[requestList.size()]);
            getFragment().requestPermissions(strings, requestCode);
        }
    }
}
