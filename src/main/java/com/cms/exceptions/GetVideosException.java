package com.cms.exceptions;

public class GetVideosException extends RuntimeException {
    public GetVideosException() {
        super();
    }

    public GetVideosException(String errorMessage) {
        super(errorMessage);
    }
}