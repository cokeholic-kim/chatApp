package org.chatapp.chatapp.common.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.chatapp.chatapp.common.api.Api;
import org.chatapp.chatapp.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception exception){
        log.error("",exception);

        return ResponseEntity.status(500).body(Api.ERROR(ErrorCode.SERVER_ERROR));
    }
}
