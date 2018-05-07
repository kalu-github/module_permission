package com.quasar.demo;

/**
 * description: 常量
 * created by kalu on 2018/4/16 22:10
 */
public class BaseConstant {

    public static final String TYPE_PATIENT = "0"; //患者
    public static final String TYPE_DOCTOR = "1";  //医生
    public static final String SIZE_BANNER = "5";  //轮播图显示数量
    public static final String NEW_LINE = "\n";
    public static final String NULL_STR = "";
    public static final String UNDERLINE = "_";
    public static final String RUNG = "-";
    public static final String COLON = ":";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String PERCENT = "%";
    public static final String ONE = "1";
    public static final String FLOAT_ONE = "1.0";
    public static final String ZERO = "0";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String X_LOGIN = "xLogin";
    public static final String X_CACHED = "xCached";
    public static final String POST = "POST";

    // cache
    public static final String CACHE_ROOT = "quasar";
    public static final String CACHE_IMAGE = CACHE_ROOT + "/image";
    public static final String CACHE_HTTP = CACHE_ROOT + "/http";
    public static final String CACHE_UPDATA = CACHE_ROOT + "/apk";
    public static final String CACHE_USER = CACHE_ROOT + "/user";
    public static final String CACHE_WEB = CACHE_ROOT + "/web";
    public static final String CACHE_DB = CACHE_ROOT + "/db";
    public static final String CACHE_CAMERA = CACHE_ROOT + "/camera/";
    public static final String CACHE_COMPRESS = CACHE_ROOT + "/compress";
    // json
    public static final String JSON_CITY = "city.json";
    // type
    public static final String USER_PATIENT = "1";
    // platfrom
    public static final String PLATFROM = "1";
    // version
    public static final String VERSION = "12";
    // httpcode
    public static final String HTTP_CODE_LOGIN_FAILURE = "-00010001"; // 用户已经失效
    public static final String HTTP_CODE_LOGIN_SUCCESS = "00010001"; // 用户登录成功
    public static final String HTTP_CODE_LOGIN_AUDIT = "00010000"; // 用户登录成功, 处于审核状态
    public static final String HTTP_CODE_LOGIN_NOT_TRUE = "00010002"; // 用户审核失败, 资料不真实
    public static final String HTTP_CODE_LOGIN_INCOMPLETE = "00010003"; // 用户资料不全, 需要补充
    public static final String HTTP_CODE_LOGIN_FREEZE = "00010004"; // 用户账户被冻结

    /******************************************************************************************/

    public static final int PERMISSION_SD = 1001;
    public static final int PERMISSION_CAMERA = 1002;

    /******************************************************************************************/

    public static boolean IS_DEBUG = true;
    private static final String API_DEV = "http://192.168.1.200/";
    // private static final String API_DEV = "http://192.168.1.172/";
    ///private static final String API_NET = "http://124.232.150.34:60001/";
    private static final String API_NET = "http://192.144.147.161/";
    public static final String API_URL = true ? API_DEV : API_NET;
}