package com.example.quizapp_fe.api;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    public Integer statusCode;
    public String message;

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    @NonNull
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
