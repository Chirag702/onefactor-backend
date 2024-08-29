package com.onefactor.app.Response;

public class ApiResponse<T> {
    private int statusCode;
    private String error;
    private T data;

    public ApiResponse(int statusCode, String error, T data) {
        this.statusCode = statusCode;
        this.error = error;
        this.data = data;
    }

    // Getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
