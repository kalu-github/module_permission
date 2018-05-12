package lib.kalu.permission.core.listener;

import android.content.Intent;

import java.util.List;

public interface OnPermissionChangeListener {

    void onSucc(int requestCode, List<String> list);

    void onFail(int requestCode, List<String> list);

    void onAgain(int requestCode, List<String> list);

    void onDenied(int requestCode, List<String> list, Intent settingIntent);
}
