package com.example.shivansh.krishi_care;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SOSResponse {
    @SerializedName("Response")
    @Expose
    private String Name;

    public SOSResponse(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }
}
