package com.example.shivansh.krishi_care;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPMessageResponse {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Details")
    @Expose
    private String details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
