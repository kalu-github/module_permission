package lib.kalu.permission.core.listener;

import android.content.Intent;

/**
 * description: 注解回调方法
 * created by kalu on 2017/12/16 17:03
 */
public interface OnAnnotationChangeListener<T> {

    String NAME = "$$OnAnnotationChangeListener";

    void onFail(T object, int code);

    void onSucc(T object, int code);

    void onAgain(T object, int code);

    void onDenied(T object, int code, Intent intent);

    void requestSync(T object);
}