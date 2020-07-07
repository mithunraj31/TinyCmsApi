package com.cms.model;

import java.time.LocalDateTime;
import java.util.List;

public class ValidateErrorsModel {
    
    private int status;

    private List<String> errors;

    private LocalDateTime timestamp;

    public ValidateErrorsModel(int status, List<String> errors) {
        this.setStatus(status);
        this.setErrors(errors);
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timstamp) {
        this.timestamp = timstamp;
    }



    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }


}