package com.cms.exceptions;

public class GetCamerasException extends RuntimeException {
    public GetCamerasException() {
        super();
    }

    public GetCamerasException(String errorMessage) {
        super(errorMessage);
    }
}