package lib.kalu.permission.core.listener;

import android.content.Intent;

public interface OnPermissionChangeListener {

    void onSucc(int code);

    void onFail(int code);

    void onAgain(int code);

    void onDenied(int code, Intent intent);
}
