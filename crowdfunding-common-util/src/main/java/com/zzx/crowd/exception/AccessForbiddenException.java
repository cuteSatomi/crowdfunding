package com.zzx.crowd.exception;

/**
 * @author zzx
 * @date 2020-12-30 14:30:46
 *
 * 用户未登录就访问受保护资源抛出的异常
 */
public class AccessForbiddenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
