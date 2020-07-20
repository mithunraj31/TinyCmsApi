package com.cms.exceptions;

public class VideoNotFullfilledException extends RuntimeException{
    public VideoNotFullfilledException() {
        super();
    }

    public VideoNotFullfilledException(String errorMessage) {
        super(errorMessage);
    }
}