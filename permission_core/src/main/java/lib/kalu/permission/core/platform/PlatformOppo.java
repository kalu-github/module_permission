package lib.kalu.permission.core.platform;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

/**
 * support:
 * 1. oppo a57 android 6.0.1/coloros3.0
 * <p>
 * manager home page, permissions manage page does not work!!!, or
 * {@link PlatformGoogle#settingIntent()}
 * <p>
 * Created by joker on 2017/8/4.
 */

public class PlatformOppo implements PlatformImp {
    private final String PKG = "com.coloros.safecenter";
    private final String MANAGER_OUT_CLS = "com.coloros.safecenter.permission.singlepage" +
            ".PermissionSinglePageActivity";

    final Activity activity;

    public PlatformOppo(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Intent settingIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACKAGE, activity.getPackageName());
        ComponentName comp;
        comp = new ComponentName(PKG, MANAGER_OUT_CLS);
        // do not work!!
//        comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission" + ".PermissionAppAllPermissionActivity");
        intent.setComponent(comp);

        return intent;
    }
}
