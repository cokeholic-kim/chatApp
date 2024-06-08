package org.chatapp.chatapp.common.exception;

import org.chatapp.chatapp.common.error.ErrorCodeIfs;
public class ApiException extends RuntimeException implements ApiExceptionIfs {

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;
    public ApiException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        super(errorDescription);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(Throwable throwable, ErrorCodeIfs errorCodeIfs, String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }

    @Override
    public ErrorCodeIfs errorcodeifs() {
        return errorCodeIfs;
    }

    @Override
    public String errorDescription() {
        return errorDescription;
    }
}
