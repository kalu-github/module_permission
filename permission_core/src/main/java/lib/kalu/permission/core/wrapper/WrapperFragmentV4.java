package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.permission.core.listener.OnPermissionChangeListener;

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

        final List<String> list1 = getPermission();
        if (null == list1 || list1.size() == 0) return;

        final int requestCode = getRequestCode();
        final String[] names = new String[list1.size()];
        final List<String> list2 = new ArrayList<>();

        for (int i = 0; i < list1.size(); i++) {

            final String name = list1.get(i);
            names[i] = name;

            final boolean again = mFragment.shouldShowRequestPermissionRationale(name);
            if (again) {
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

        mFragment.requestPermissions(names, requestCode);
    }
}
