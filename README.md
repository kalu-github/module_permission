android 6.0 运行时权限管理, 
编译时注解, 普通监听回调

![image](https://github.com/153437803/PermissionManager/blob/master/image_20180510214150.gif ) 

<table><tr><td bgcolor=#FF4500><font color=#0099ff size=7 face="黑体">国产6.0以下机型的运行时权限, 就是主动触发操作，try-catch获取异常</font></td></tr></table>

```
1.普通回调监听方式：

PermissionManager.get(HomeFragment.this)
                 .setForce(true)
                 .setPermission(Manifest.permission.CAMERA)
                 .setCode(BaseConstant.PERMISSION_CAMERA)
                 .setPageType(IntentType.PLATFRRM_SETTING)
                 .setOnPermissionChangeListener(new OnPermissionChangeListener() {
                  
                       @Override
                       public void onSucc(int code) {
                            Log.e("dsds", "cameraSucc ==> code = " + code);
                            Toast.makeText(getContext(), "获取相机权限成功", Toast.LENGTH_SHORT).show();
                       }

                        @Override
                        public void onFail(int code) {
                            Log.e("dsds", "cameraFail ==> code = " + code);
                            Toast.makeText(getContext(), "获取相机权限失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAgain(int code) {
                            Log.e("dsds", "onAgain ==> code = " + code);
                            Toast.makeText(getContext(), "获取相机权限, 弹窗", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDenied(int code, Intent intent) {
                            Log.e("dsds", "onDenied ==> code = " + code);
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
                 .setUnderM(true)
                 .setPermission(Manifest.permission.RECORD_AUDIO)
                 .setPageType(IntentType.PLATFRRM_SETTING)
                 .setCode(BaseConstant.PERMISSION_SD)
                 .request();
                 
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    PermissionManager.onRequestPermissionsResult(SplashActivity.this, requestCode, grantResults);
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}

@PermissionSucc(BaseConstant.PERMISSION_SD)
public void cardSucc(int code) {
    goThenKill(MainActivity.class);
    Log.e("dsds", "onSucc ==> code = " + code);
    Toast.makeText(getApplicationContext(), "获取录音权限成功", Toast.LENGTH_SHORT).show();
}

@PermissionFail(BaseConstant.PERMISSION_SD)
public void cardFail(int code) {
    Toast.makeText(getApplicationContext(), "获取录音权限失败", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onFail ==> code = " + code);
}

@PermissionAgain(BaseConstant.PERMISSION_SD)
public void cardAgainNormal(int code) {
    Toast.makeText(getApplicationContext(), "获取录音权限, 弹窗", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onAgain ==> code = " + code);
}

@PermissionDenied(BaseConstant.PERMISSION_SD)
public void cardAgainRefuse(int code, final Intent intent) {
    Toast.makeText(getApplicationContext(), "获取录音权限, 拒绝", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onDenied ==> code = " + code);
}
```
