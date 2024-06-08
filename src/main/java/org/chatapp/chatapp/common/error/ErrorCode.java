package org.chatapp.chatapp.common.error;

public enum ErrorCode implements ErrorCodeIfs{

    OK(200, 200, "성공"),
    BAD_REQUEST(400, 400, "잘못된 요청"),
    SERVER_ERROR(500, 500, "서버에러"),
    NULL_POINT(500, 512, "Null point");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

    ErrorCode(Integer httpStatusCode, Integer errorCode, String description) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.description = description;
    }

    @Override
    public Integer getHttpStatusCode() {
        return null;
    }

    @Override
    public Integer getErrorCode() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
