package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void request() {

        final List<String> list1 = getPermission();
        if (null == list1 || list1.size() == 0) return;

        final int requestCode = getRequestCode();
        final String[] names = new String[list1.size()];
        final List<String> list2 = new ArrayList<>();

        for (int i = 0; i < list1.size(); i++) {

            final String name = list1.get(i);
            names[i] = name;

            if (SupportCheck.isAndroidM() && activity.shouldShowRequestPermissionRationale(name)) {
                list2.add(name);
            }
        }

        final OnPermissionChangeListener api1 = getPermissionChangeListener();
        if (null != api1 && list2.size() > 0) {
            api1.onAgain(requestCode, list2);
        }

        final OnPermissionChangeListener api2= getPermissionChangeListener();
        if (null != api2 && list2.size() > 0) {
            api2.onAgain(requestCode, list2);
        }

        ActivityCompat.requestPermissions(activity, names, requestCode);
    }
}
