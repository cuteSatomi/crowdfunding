package com.zzx.crowd.exception;

/**
 * 新增或更新用户时，账号重复的异常
 * @author zzx
 * @date 2020-12-30 21:11
 */
public class LoginAcctAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginAcctAlreadyExistException() {
    }

    public LoginAcctAlreadyExistException(String message) {
        super(message);
    }

    public LoginAcctAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
