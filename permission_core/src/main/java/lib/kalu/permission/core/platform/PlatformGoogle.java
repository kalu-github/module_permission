package lib.kalu.permission.core.platform;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * description: android原生权限设置页面
 * created by kalu on 2017/12/19 22:33
 */
public final class PlatformGoogle implements PlatformImp {
    final Activity activity;

    public PlatformGoogle(Activity activity) {
        this.activity = activity;
    }

    public Intent settingIntent() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(PACKAGE, activity.getPackageName(), null);
        intent.setData(uri);
        return intent;
    }
}
