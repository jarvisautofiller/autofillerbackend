package com.autofiller.exception;


public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse() {}  // <- Default constructor is required

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters are required
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

