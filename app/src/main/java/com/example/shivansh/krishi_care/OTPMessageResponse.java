package com.example.shivansh.krishi_care;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPMessageResponse {

    //oor Irish immigrants, Sarah and Joseph Rogers.[54] Joseph died when Steve was a child, and Sarah died of pneumonia while Stev
    @SerializedName("name")
    @Expose
    private String Name;
    @SerializedName("realname")
    @Expose
    private String RealName;
    @SerializedName("team")
    @Expose
    private String Team;
    @SerializedName("firstappearance")
    @Expose
    private String firstApp;
    @SerializedName("createdby")
    @Expose
    private String createdBy;
    @SerializedName("publisher")
    @Expose
    private String Publisher;
    @SerializedName("imageurl")
    @Expose
    private String imageUrl;
    @SerializedName("bio")
    @Expose
    private String Bio;

    public OTPMessageResponse(String name, String realName, String team, String firstApp, String createdBy, String publisher, String imageUrl, String bio) {
        Name = name;
        RealName = realName;
        Team = team;
        this.firstApp = firstApp;
        this.createdBy = createdBy;
        Publisher = publisher;
        this.imageUrl = imageUrl;
        Bio = bio;
    }

    public String getName() {
        return Name;
    }

    public String getRealName() {
        return RealName;
    }

    public String getTeam() {
        return Team;
    }

    public String getFirstApp() {
        return firstApp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBio() {
        return Bio;
    }
}
