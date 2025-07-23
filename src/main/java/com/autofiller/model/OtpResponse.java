package com.autofiller.model;

public class OtpResponse {
    private String call_sid;
    private String status;

    // Getters and setters
    public String getCall_sid() {
        return call_sid;
    }

    public void setCall_sid(String call_sid) {
        this.call_sid = call_sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
