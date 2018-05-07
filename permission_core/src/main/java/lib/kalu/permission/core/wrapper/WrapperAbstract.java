package lib.kalu.permission.core.wrapper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import lib.kalu.permission.core.intent.IntentType;
import lib.kalu.permission.core.listener.OnAnnotationChangeListener;
import lib.kalu.permission.core.listener.OnPermissionChangeListener;
import lib.kalu.permission.core.platform.PlatformSupport;
import lib.kalu.permission.core.support.SupportPermissionForce;
import lib.kalu.permission.core.support.SupportPermissionNormal;

public abstract class WrapperAbstract implements WrapperPermission, Cloneable {

    private final String NULL = "";

    @IntentType
    private static final int DEFAULT_PAGE_TYPE = IntentType.ANDROID_SETTING;
    private static final int DEFAULT_REQUEST_CODE = -1;
    @IntentType
    private int pageType = DEFAULT_PAGE_TYPE;
    private int requestCode = DEFAULT_REQUEST_CODE;
    private int[] requestCodes;
    private String[] permissions;
    private String permission;
    private OnPermissionChangeListener permissionRequestListener;

    // 是否第二次弹出权限提示
    private boolean isAgain = false;
    // 是否需要适配国产5.0, 默认支持
    private boolean underM = true;
    // 是否强制弹出权限申请对话框
    private boolean force = true;

    private static final HashMap<Key, WeakReference<WrapperPermission>> wrapperMap = new HashMap<>();

    public static HashMap<Key, WeakReference<WrapperPermission>> getWrapperMap() {
        return wrapperMap;
    }

    @Override
    public WrapperBase setOnPermissionChangeListener(OnPermissionChangeListener listener) {
        this.permissionRequestListener = listener;
        return this;
    }

    @Override
    public OnPermissionChangeListener getPermissionChangeListener() {
        return permissionRequestListener;
    }

    @Override
    public OnAnnotationChangeListener getAnnotationListener(String className) {
        String clazz = className + OnAnnotationChangeListener.NAME;
        try {
            return (OnAnnotationChangeListener) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            Log.e("PermissionManager", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public WrapperBase requestCode(int code) {
        if (code < 0) {
            throw new IllegalArgumentException("请求码必须大于0, code = " + code);
        }
        this.requestCode = code;
        return this;
    }

    @Override
    public WrapperBase setCode(int... codes) {
        this.requestCodes = codes;
        return this;
    }

    @Override
    public WrapperBase requestPermission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public WrapperBase setPermission(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public WrapperBase setPageType(@IntentType int pageType) {
        this.pageType = pageType;
        return this;
    }


    @Override
    public WrapperBase requestAgain() {
        isAgain = true;
        return this;
    }

    @Override
    public WrapperBase setForce(boolean force) {
        this.force = force;
        return this;
    }

    @Override
    public boolean isForce() {
        return force;
    }

    @Override
    public boolean isUnderM() {
        return underM;
    }

    @Override
    public WrapperBase setUnderM(boolean underM) {
        this.underM = underM;
        return this;
    }

    @Override
    public int getRequestCode() {
        return requestCode;
    }

    @Override
    public int[] getRequestCodes() {
        return requestCodes;
    }

    @Override
    public String getRequestPermission() {
        return permission;
    }

    @Override
    public String[] getRequestPermissions() {
        return permissions;
    }

    @Override
    public int getPageType() {
        return pageType;
    }

    @Override
    public boolean isAgain() {
        return isAgain;
    }

    @Override
    public void request() {

        boolean again = isAgain();
        Log.e("WrapperAbstract", "request ==> again = " + again);

        if (again) {
            addEntity(getRequestPermissions()[0], getRequestCodes()[0], false);
            originalRequest();
        } else {
            OnPermissionChangeListener listener = getPermissionChangeListener();
            Log.e("WrapperAbstract", "request ==> listener = " + listener);

            if (null != listener) {
                initArrayAndEntity();
                requestListener();
            } else {
                addEntity(getRequestPermissions()[0], getRequestCodes()[0], true);
                requestAnnotation();
            }
        }
    }

    private void initArrayAndEntity() {
        String[] permissions = getRequestPermissions();
        String[] targetPermissions = new String[permissions.length];
        if (permissions.length != requestCodes.length) {
            throw new IllegalArgumentException("permissions' length is different from codes' length");
        }
        int[] requestCodes = getRequestCodes();
        int[] targetCodes = new int[requestCodes.length];
        for (int i = permissions.length - 1; i >= 0; i--) {
            targetPermissions[permissions.length - i - 1] = permissions[i];
            targetCodes[permissions.length - i - 1] = requestCodes[i];
        }

        for (int i = 0; i < targetPermissions.length; i++) {
            if (i == 0) {
                setCode(DEFAULT_REQUEST_CODE);
                setPermission(NULL);
            } else {
                setCode(targetCodes[i - 1]);
                setPermission(targetPermissions[i - 1]);
            }

            addEntity(targetPermissions[i], targetCodes[i], true);
        }
    }

    private final void addEntity(String permission, int requestCode, boolean add) {
        requestCode(requestCode);
        requestPermission(permission);
        if (!add) return;
        try {
            final Key key = new Key(getActivity(), requestCode);
            final WeakReference<WrapperPermission> value = new WeakReference<>((WrapperPermission) this.clone());
            wrapperMap.put(key, value);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    final void requestSync(Activity activity) {
        requestSync(activity);
    }

    final void requestSync(android.support.v4.app.Fragment fragment) {
        requestSync(fragment);
    }

    private final void requestSync(Object object) {
        String name = object.getClass().getName();
        getAnnotationListener(name).requestSync(object);
    }

    private void mayGrantedWithListener() {

        boolean force = isForce();
        if (force) {
            SupportPermissionForce.succListener(this);
        } else {
            SupportPermissionNormal.failListener(this);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static class Key {
        private int requestCode;
        private WeakReference<Object> object;

        public Key(Object object, int requestCode) {
            this.object = new WeakReference<>(object);
            this.requestCode = requestCode;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Key && ((Key) obj)
                    .getObject().get() != null && object.get().getClass() != null && ((Key) obj)
                    .getRequestCode() == requestCode && object.get().getClass().getName().equals(((Key)
                    obj).getObject().get().getClass()
                    .getName());
        }

        @Override
        public int hashCode() {
            return (object.get().hashCode() + requestCode) / 10;
        }

        public int getRequestCode() {
            return requestCode;
        }

        public void setRequestCode(int requestCode) {
            this.requestCode = requestCode;
        }

        public WeakReference<Object> getObject() {
            return object;
        }

        public void setObject(WeakReference<Object> object) {
            this.object = object;
        }
    }

    /*******************************************/

    public final void requestListener() {

        // 是否需要适配国产5.0
        boolean underM = isUnderM();
        // 检测系统是否需要支持
        boolean needUnderM = PlatformSupport.isUnderMNeedChecked(underM);
        Log.e("WrapperAbstract", "requestListener ==> underM = " + underM + ", needUnderM = " + needUnderM);

        if (needUnderM) {
            if (PlatformSupport.isPermissionGranted(getActivity(), getRequestPermission())) {
                SupportPermissionNormal.succListener(this);
            } else {
                SupportPermissionForce.deniedWithListenerForUnderM(this);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("WrapperAbstract", "requestListener ==> 系统版本大于5.0");

            if (ContextCompat.checkSelfPermission(getActivity(), getRequestPermission()) != PackageManager
                    .PERMISSION_GRANTED) {
                beginListener();
            } else {
                mayGrantedWithListener();
            }
        } else {
            SupportPermissionNormal.failListener(this);
        }
    }

    /**
     * 权限已被同意
     */
    private final void requestAnnotation() {

        // 是否需要适配国产5.0
        boolean underM = isUnderM();
        // 检测系统是否需要支持
        boolean needUnderM = PlatformSupport.isUnderMNeedChecked(underM);
        Log.e("WrapperAbstract", "requestAnnotation ==> underM = " + underM + ", needUnderM = " + needUnderM);

        if (needUnderM) {
            if (PlatformSupport.isPermissionGranted(getActivity(), getRequestPermission())) {
                SupportPermissionNormal.succAnnotation(this);
            } else {
                SupportPermissionForce.deniedWithAnnotationForUnderM(this);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission = getRequestPermission();
            final int state = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (state != PackageManager.PERMISSION_GRANTED) {
                Log.e("WrapperAbstract", "requestAnnotation(系统版本大于5.0, 开始申请) ==> permission = " + permission);
                beginAnnotation();
            } else {
                Log.e("WrapperAbstract", "requestAnnotation(系统版本大于5.0, 申请通过) ==> permission = " + permission);
                succAnnotation();
            }
        } else {
            SupportPermissionNormal.succAnnotation(this);
        }
    }

    /**
     * 权限已被同意
     */
    private final void succAnnotation() {

        boolean force = isForce();
        if (force) {
            SupportPermissionForce.succAnnotation(this);
        } else {
            SupportPermissionNormal.succAnnotation(this);
        }
    }

    /*******************************************/

    abstract void originalRequest();

    abstract void beginAnnotation();

    abstract void beginListener();
}
