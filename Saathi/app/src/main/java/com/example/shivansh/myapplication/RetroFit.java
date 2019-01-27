package com.example.shivansh.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetroFit {

    @GET("{users_phone_no}/AUTOGEN")
    Call<OTPMessageResponse> sentOTP(@Path("users_phone_no")String phone_no);

    @GET("VERIFY/{session_id}/{otp_entered_by_user}")
    Call<OTPMessageResponse> verifyOTP(@Path("session_id")String session_id,@Path("otp_entered_by_user")String otp_entered_by_user);


    @GET("SOS")
    Call<SOSResponse> makeSOS();


    @GET("/care/{name}/{contact}/{relation}")
    Call<SOSResponse> addCaretaker(@Path("name") String name, @Path("contact") String contact,@ Path("relation") String relation);

    @GET("/getcare")
    Call<List<AddCaretakerBody>> getAllCaretaker();

    @GET("/todo/{name}/{description}/{time}/{date}")
    Call<SOSResponse> addTodoTask(@Path("name") String name,@Path("description") String description,@Path("time") String time, @Path("date") String date);

    @GET("/getTODO")
    Call<List<ToDoBody>> getAllToDo();

    @FormUrlEncoded
    @POST("/senti")
    Call<SOSResponse>getLandingPageReport(@FieldMap Map<String, String> learning_objective_uuids);
}
