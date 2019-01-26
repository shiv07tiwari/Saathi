package com.example.shivansh.myapplication;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetroFit {

    @GET("{users_phone_no}/AUTOGEN")
    Call<OTPMessageResponse> sentOTP(@Path("users_phone_no")String phone_no);

    @GET("VERIFY/{session_id}/{otp_entered_by_user}")
    Call<OTPMessageResponse> verifyOTP(@Path("session_id")String session_id,@Path("otp_entered_by_user")String otp_entered_by_user);

    @GET("home")
    Call<List<OTPMessageResponse>> makeSOS();

    @Multipart
    @POST("v1/group/new")
    Call<ArrayList<String>> newGroup(
            @Header("token") String token,
            @Part MultipartBody.Part photo,
            @Part("title") RequestBody subject,
            @Part("members[]") List<RequestBody> members);
}
