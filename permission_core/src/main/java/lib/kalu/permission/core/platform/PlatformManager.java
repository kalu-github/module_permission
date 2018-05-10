package lib.kalu.permission.core.platform;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * description: 权限跳转页面 - 平台管理
 * created by kalu on 2017/12/19 22:15
 */
public class PlatformManager {

    static final String MANUFACTURER_HUAWEI = "huawei";
    static final String MANUFACTURER_XIAOMI = "xiaomi";
    static final String MANUFACTURER_OPPO = "oppo";
    static final String MANUFACTURER_VIVO = "vivo";
    static final String MANUFACTURER_MEIZU = "meizu";

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static Intent getPlatformSetting(Activity activity) {

        PlatformGoogle platformSimple;
        try {
            if (MANUFACTURER_HUAWEI.equalsIgnoreCase(Build.MANUFACTURER)) {
                platformSimple = new PlatformHuawei(activity);
            } else if (MANUFACTURER_OPPO.equalsIgnoreCase(Build.MANUFACTURER)) {
                platformSimple = new PlatformOppo(activity);
            } else if (MANUFACTURER_VIVO.equalsIgnoreCase(Build.MANUFACTURER)) {
                platformSimple = new PlatformVivo(activity);
            } else if (MANUFACTURER_XIAOMI.equalsIgnoreCase(Build.MANUFACTURER)) {
                platformSimple = new PlatformMi(activity);
            } else if (MANUFACTURER_MEIZU.equalsIgnoreCase(Build.MANUFACTURER)) {
                platformSimple = new PlatformMeizu(activity);
            } else {
                platformSimple = new PlatformGoogle(activity);
            }

            return platformSimple.settingIntent();
        } catch (Exception e) {
            Log.e("PlatformManager", "手机品牌为：" + Build.MANUFACTURER + "异常抛出，：" + e.getMessage(), e);
            platformSimple = new PlatformGoogle(activity);
            return platformSimple.settingIntent();
        }
    }

    public static Intent getAndroidSetting(Activity activity) {
        return new PlatformGoogle(activity).settingIntent();
    }

    public static boolean isXIAOMI() {
        return getManufacturer().equalsIgnoreCase(MANUFACTURER_XIAOMI);
    }

    public static boolean isOPPO() {
        return getManufacturer().equalsIgnoreCase(MANUFACTURER_OPPO);
    }

    public static boolean isMEIZU() {
        return getManufacturer().equalsIgnoreCase(MANUFACTURER_MEIZU);
    }
}