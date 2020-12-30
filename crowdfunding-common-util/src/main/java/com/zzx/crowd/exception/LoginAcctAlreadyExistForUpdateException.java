package com.zzx.crowd.exception;

/**
 * @author zzx
 * @date 2020-12-30 22:10
 */
public class LoginAcctAlreadyExistForUpdateException extends RuntimeException {

    private static final long serialVersionUID = -7034897190745766939L;

    public LoginAcctAlreadyExistForUpdateException() {
    }

    public LoginAcctAlreadyExistForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyExistForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyExistForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyExistForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
