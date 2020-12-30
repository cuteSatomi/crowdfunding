package com.zzx.crowd.exception;

/**
 * @author zzx
 * @date 2020-12-30 19:55
 */
public class DeleteFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeleteFailedException() {
    }

    public DeleteFailedException(String message) {
        super(message);
    }

    public DeleteFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteFailedException(Throwable cause) {
        super(cause);
    }

    public DeleteFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
