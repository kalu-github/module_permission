package lib.kalu.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 多个权限同步申请
 * created by kalu on 2017/12/16 17:19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface PermissionMulitSync {

    // 权限代码
    int[] value();

    // 权限名字
    String[] permission();
}
