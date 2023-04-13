package com.koba.filestorage.bo;

public class ExceptionResponseBo {

    String statusCode;
    String errorMessage;

    public ExceptionResponseBo(String statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;

    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
