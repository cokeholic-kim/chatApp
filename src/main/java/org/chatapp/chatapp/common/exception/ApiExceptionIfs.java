package org.chatapp.chatapp.common.exception;

import org.chatapp.chatapp.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {
    ErrorCodeIfs errorcodeifs();
    String errorDescription();
}
