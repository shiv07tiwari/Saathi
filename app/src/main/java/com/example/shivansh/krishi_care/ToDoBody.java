package com.example.shivansh.krishi_care;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToDoBody {
    @SerializedName("Name")
    @Expose
    private  String name;
    @SerializedName("Description")
    @Expose
    private  String description;

    @SerializedName("Day")
    @Expose
    private  String date;
    @SerializedName("Time")
    @Expose
    private  String time;

    public ToDoBody(String name, String description, String date, String time) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}