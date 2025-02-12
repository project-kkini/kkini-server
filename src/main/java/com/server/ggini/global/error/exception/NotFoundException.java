package com.server.ggini.global.error.exception;

public class NotFoundException extends BusinessException{

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
