 ![](https://img.shields.io/badge/Build-Passing-green.svg) ![](https://img.shields.io/badge/API%20-14+-green.svg) [ ![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-%E5%BC%A0%E8%88%AA-red.svg) ](http://www.jianshu.com/u/22a5d2ee8385) ![](https://img.shields.io/badge/%E9%82%AE%E7%AE%B1-153437803@qq.com-red.svg)

![image](https://github.com/153437803/PermissionManager/blob/master/image_20180510214150.gif ) 

#
## 权限适配（4.3-6.0）
```
关于国产机型4.3-6.0之间, 动态权限适配的核心思想
就是主动触发操作, 迫使软件崩溃, catch异常信息, 之后就是回传相应的状态, 就是这么的简单
```
#
## 依赖方式
```
dependencies {
    implementation project(':permission_core')
    implementation project(':permission_annotation')
    annotationProcessor project(':permission_processor')
}
```
#
## 参数说明
```
----------------------------------------------------------------------------------

基本：

code：请求码
List<String> list：请求的权限名字集合

----------------------------------------------------------------------------------

普通回调监听方式：

onSucc(int code, List<String> list)： 成功
onFail(int code, List<String> list)： 失败
onAgain(int code, List<String> list)： 系统确认框
onDenied(int code, List<String> list)： 拒绝

----------------------------------------------------------------------------------

编译时注解方式：

@PermissionSucc(code)： 成功
@PermissionFail(code)： 失败
@PermissionAgain(code)： 系统确认框
@PermissionDenied(code)： 拒绝

----------------------------------------------------------------------------------
```
#
## 普通回调监听
```
基本用法：

PermissionManager.get(HomeFragment.this)
                 .setPermissionName(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                 .setRequestCode(code)
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
#
## 注解回调监听
```
基本用法：

PermissionManager.get(SplashActivity.this)
                 .setPermissionName(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                 .setPageType(IntentType.SYSTEM_SETTING)
                 .setRequestCode(BaseConstant.PERMISSION_SD)
                 .request();
                 
@PermissionSucc(code)
public void onSucc(int code, List<String> list) {
    goThenKill(MainActivity.class);
    Log.e("dsds", "onSucc ==> code = " + code + ", list = " + list.toString());
    Toast.makeText(getApplicationContext(), "获取存储权限成功", Toast.LENGTH_SHORT).show();
}

@PermissionFail(code)
public void onFail(int code, List<String> list) {
    Toast.makeText(getApplicationContext(), "获取存储权限失败", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onFail ==> code = " + code + ", list = " + list.toString());
}

@PermissionAgain(code)
public void onAgain(int code, List<String> list) {
    Toast.makeText(getApplicationContext(), "获取存储权限, 弹窗", Toast.LENGTH_SHORT).show();
    Log.e("dsds", "onAgain ==> code = " + code + ", list = " + list.toString());
}

@PermissionDenied(code)
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
#

# Proguard-Rules
```
-keep class lib.kalu.permission.* {
*;
}
```

#

# License
```
Copyright 2017 张航

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
