package com.redhat.demo.achievement.rest;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ErrorResponse {

    private String errorUuid;
    private String errorClass;
    private String errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorUuid, String errorClass, String errorMessage) {
        this.errorUuid = errorUuid;
        this.errorClass = errorClass;
        this.errorMessage = errorMessage;
    }

    public String getErrorUuid() {
        return errorUuid;
    }

    public String getErrorClass() {
        return errorClass;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
