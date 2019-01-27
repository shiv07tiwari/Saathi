package com.example.shivansh.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCaretakerBody {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Phone")
    @Expose
    private String contact;
    @SerializedName("Relation")
    @Expose
    private String relation;

    public AddCaretakerBody(String name, String phone, String relation) {
        this.name = name;
        this.contact = phone;
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.contact = phone;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
