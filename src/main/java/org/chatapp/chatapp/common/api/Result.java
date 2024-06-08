package org.chatapp.chatapp.common.api;


import org.chatapp.chatapp.common.error.ErrorCode;
import org.chatapp.chatapp.common.error.ErrorCodeIfs;

public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    private Result(Integer resultCode, String resultMessage, String resultDescription) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultDescription = resultDescription;
    }

    public static Result OK() {
        return new Result(
                ErrorCode.OK.getErrorCode(),
                ErrorCode.OK.getDescription(),
                "성공"
        );
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs) {
        return new Result(
                errorCodeIfs.getErrorCode(),
                errorCodeIfs.getDescription(),
                "에러발생"
        );
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        return new Result(
                errorCodeIfs.getErrorCode(),
                errorCodeIfs.getDescription(),
                throwable.getLocalizedMessage()
        );
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        return new Result(
                errorCodeIfs.getErrorCode(),
                errorCodeIfs.getDescription(),
                description
        );
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultDescription() {
        return resultDescription;
    }
}
