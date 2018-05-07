package lib.kalu.permission.core.platform;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

/**
 * Created by joker on 2017/8/24.
 */

public class PlatformMeizu extends PlatformGoogle {

    private final String N_MANAGER_OUT_CLS = "com.meizu.safe.permission.PermissionMainActivity";
    private final String L_MANAGER_OUT_CLS = "com.meizu.safe.SecurityMainActivity";
    private final String PKG = "com.meizu.safe";

    public PlatformMeizu(Activity activity) {
        super(activity);
    }

    @Override
    public Intent settingIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACK_TAG, activity.getPackageName());
        ComponentName comp = new ComponentName(PKG, getCls());
        intent.setComponent(comp);

        return intent;
    }

    private String getCls() {
        if (PlatformSupport.isAndroidL()) {
            return L_MANAGER_OUT_CLS;
        } else {
            return N_MANAGER_OUT_CLS;
        }
    }
}
