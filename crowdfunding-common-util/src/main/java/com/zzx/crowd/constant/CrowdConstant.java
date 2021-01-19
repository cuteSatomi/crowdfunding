package com.zzx.crowd.constant;

/**
 * @author zzx
 * @date 2020-12-26 19:39
 */
public class CrowdConstant {
    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String ATTR_NAME_TEMP_PROJECT = "tempProject";

    public static final String MESSAGE_LOGIN_FAILED = " 用户名或密码错误，请重新输入 ";
    public static final String MESSAGE_LOGIN_ACCOUNT_ALREADY_EXIST = " 用户名已存在 ";
    public static final String MESSAGE_ACCESS_FORBIDDEN = " 拒绝访问，请登录后再试 ";
    public static final String MESSAGE_STRING_INVALIDATE = " 参数错误，请不要传入空字符串 ";
    public static final String MESSAGE_SYSTEM_ERROR_LOGINACCT_NOT_UNIQUE = " 系统错误，登录账号不唯一 ";
    public static final String MESSAGE_DELETE_FAILED = " 自己无法干掉自己 ";
    public static final String MESSAGE_ACCESS_DENIED = " 宁没有权限访问这个功能 ";
    public static final String MESSAGE_CODE_NOT_EXISTS = " 验证码已过期，请重新发送 ";
    public static final String MESSAGE_CODE_INVALID = " 验证码错误 ";
    public static final String MESSAGE_HEADER_PIC_UPLOAD_FAILED = " 头图上传失败 ";
    public static final String MESSAGE_HEADER_PIC_EMPTY = " 头图为空 ";
    public static final String MESSAGE_DETAIL_PIC_EMPTY = " 详情图为空 ";
    public static final String MESSAGE_DETAIL_PIC_UPLOAD_FAILED = " 详情图上传失败 ";
    public static final String MESSAGE_TEMP_PROJECT_MISSING = " 临时存储的project对象丢失 ";

    public static final String REDIS_VERIFY_CODE_PREFIX = "REDIS_VERIFY_CODE_";
}
