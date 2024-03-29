package com.you.common.core.constant;

/**
 * 缓存常量信息
 */
public class CacheConstants {
    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 720;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public final static long REFRESH_TIME = 120;

    /**
     * 密码最大错误次数
     */
    public final static int PASSWORD_MAX_RETRY_COUNT = 5;

    /**
     * 密码锁定时间，默认10（分钟）
     */
    public final static long PASSWORD_LOCK_TIME = 10;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码保存时间(秒)
     */
    public static final long CAPTCHA_EXPIRATION = 120;

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 登录IP黑名单 cache key
     */
    public static final String SYS_LOGIN_BLACKIPLIST = SYS_CONFIG_KEY + "sys.login.blackIPList";

    /**
     * 用户权限 redis key
     */
    public static final String REDIS_USER_PERMISSION_KEY = "user:permission:";

    /**
     * 用户角色字符串 redis key
     */
    public static final String REDIS_USER_ROLE_KEY = "user:role:";

    /**
     * 数据字典 redis key
     */
    public static final String REDIS_SYS_DICT_KEY = "sys:dict:";
}
