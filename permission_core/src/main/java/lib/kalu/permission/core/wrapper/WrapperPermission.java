package lib.kalu.permission.core.wrapper;

public interface WrapperPermission extends WrapperBase {
    String getRequestPermission();

    int getRequestCode();

    WrapperBase requestPermission(String permission);

    WrapperBase requestCode(int code);
}
