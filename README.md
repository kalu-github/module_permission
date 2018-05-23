 ![](https://img.shields.io/badge/Build-Passing-green.svg) ![](https://img.shields.io/badge/API%20-14+-green.svg) [ ![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-%E5%BC%A0%E8%88%AA-red.svg) ](http://www.jianshu.com/u/22a5d2ee8385) ![](https://img.shields.io/badge/%E9%82%AE%E7%AE%B1-153437803@qq.com-red.svg)

android 6.0 运行时权限管理, 
编译时注解, 普通监听回调

![image](https://github.com/153437803/PermissionManager/blob/master/image_20180510214150.gif ) 

```
简介说明,本项目可以干什么

1. 普通回调监听

2. 编译注解监听

3.关于国产机型5.0-6.0之间, 动态权限适配的核心思想
  ==> 就是主动触发操作, 迫使软件崩溃, catch异常信息, 之后就是回传相应的状态, 就是这么的简单
```

```
1.普通回调监听方式：
PermissionManager.get(HomeFragment.this)
                 .setForce(true)
                 .setPermissionName(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                 .setRequestCode(BaseConstant.PERMISSION_CAMERA)
                 .setPageType(IntentType.GOOGLE_SETTING)
                 .setOnPermissionChangeListener(new OnPermissionChangeListener() {
                        @Override
                        public void onSucc(int code, List<String> list) {
                            Log.e("dsds", "cameraSucc ==> code = " + code + ", name = " + list.toString());
                            Toast.makeText(getContext(), "获取相机权限成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(int code, List<String> list) {
                            Log.e("dsds", "cameraFail ==> code = " + code + ", name = " + list.toString());
                            Toast.makeText(getContext(), "获取相机权限失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAgain(int code, List<String> list) {
                            Log.e("dsds", "onAgain ==> code = " + code + ", name = " + list.toString());
                            Toast.makeText(getContext(), "获取相机权限, 弹窗", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDenied(int code, List<String> list, Intent intent) {
                            Log.e("dsds", "onDenied ==> code = " + code + ", name = " + list.toString() + ", action = " + intent.getAction());
                            Toast.makeText(getContext(), "获取相机权限, 拒绝", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .request();
                    
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    PermissionManager.onRequestPermissionsResult(SplashActivity.this, requestCode, grantResults);
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

```
2.编译时注解方式:

PermissionManager.get(SplashActivity.this)
                 .setForce(true)
                 .setPermissionName(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                 .setPageType(IntentType.SYSTEM_SETTING)
                 .setRequestCode(BaseConstant.PERMISSION_SD)
                 .request();
                 
@PermissionSucc(BaseConstant.PERMISSION_SD)
public void onSucc(int code, List<String> list) {
    goThenKill(MainActivity.class);
    Log.e("dsds", "onSucc ==> code = " + code + ", list = " + list.toString());
    Toast.makeText(getApplicationContext(), "获取存储权限成功", Toast.LENGTH_SHORT).show();
}

@PermissionFail(BaseConstant.PERMISSION_SD)
public void onFail(int code, List<String> list) {
    Toast.makeText(getApplicationContext(), "获取存储权限失败", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onFail ==> code = " + code + ", list = " + list.toString());
}

@PermissionAgain(BaseConstant.PERMISSION_SD)
public void onAgain(int code, List<String> list) {
    Toast.makeText(getApplicationContext(), "获取存储权限, 弹窗", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onAgain ==> code = " + code + ", list = " + list.toString());
}

@PermissionDenied(BaseConstant.PERMISSION_SD)
public void onDenied(int code, List<String> list, final Intent intent) {
    Toast.makeText(getApplicationContext(), "获取存储权限, 拒绝", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onDenied ==> code = " + code + ", list = " + list.toString() + ", action = " + intent.getAction());
}

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    PermissionManager.onRequestPermissionsResult(SplashActivity.this, requestCode, grantResults);
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```
