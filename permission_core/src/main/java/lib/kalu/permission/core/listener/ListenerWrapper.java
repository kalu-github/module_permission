package lib.kalu.permission.core.listener;

import lib.kalu.permission.core.wrapper.WrapperBase;

/**
 * description: 监听回调方法
 * created by kalu on 2017/12/16 17:04
 */
public interface ListenerWrapper {

    WrapperBase setOnPermissionChangeListener(OnPermissionChangeListener listener);

    OnPermissionChangeListener getPermissionChangeListener();
}
