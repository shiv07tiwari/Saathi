package com.example.shivansh.myapplication;

public class AddCaretakerBody {

   private String name;
   private String contact;
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
