package lib.kalu.permission.core.platform;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import lib.kalu.permission.core.support.SupportCheck;

/**
 * Created by joker on 2017/8/24.
 */

public class PlatformMeizu implements PlatformImp {

    private final String N_MANAGER_OUT_CLS = "com.meizu.safe.permission.PermissionMainActivity";
    private final String L_MANAGER_OUT_CLS = "com.meizu.safe.SecurityMainActivity";
    private final String PKG = "com.meizu.safe";

    final Activity activity;

    public PlatformMeizu(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Intent settingIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACKAGE, activity.getPackageName());
        ComponentName comp = new ComponentName(PKG, getCls());
        intent.setComponent(comp);

        return intent;
    }

    private String getCls() {
        if (SupportCheck.isAndroidL()) {
            return L_MANAGER_OUT_CLS;
        } else {
            return N_MANAGER_OUT_CLS;
        }
    }
}
