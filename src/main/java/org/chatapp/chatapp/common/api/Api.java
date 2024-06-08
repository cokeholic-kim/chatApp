package org.chatapp.chatapp.common.api;

import jakarta.validation.Valid;
import org.chatapp.chatapp.common.error.ErrorCodeIfs;

public class Api<T> {

    private final Result result;
    @Valid
    private final T body;

    public Api(Result result, T body) {
        this.result = result;
        this.body = body;
    }

    public static <T> Api<T> OK(T body) {
        return new Api<>(Result.OK(), body);
    }

    public static <T> Api<T> ERROR(Result result) {
        return new Api<>(result, null);
    }

    public static <T> Api<T> ERROR(ErrorCodeIfs errorCodeIfs) {
        return new Api<>(Result.ERROR(errorCodeIfs), null);
    }

    public static <T> Api<T> ERROR(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        return new Api<>(Result.ERROR(errorCodeIfs, throwable), null);
    }

    public static <T> Api<T> ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        return new Api<>(Result.ERROR(errorCodeIfs, description), null);
    }

    public Result getResult() {
        return result;
    }

    public T getBody() {
        return body;
    }
}
