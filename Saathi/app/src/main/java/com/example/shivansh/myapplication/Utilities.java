package com.example.shivansh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Utilities {

    private static String sessionId;
    static int[][] d  = new int[][]
            {
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
                    {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
                    {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
                    {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
                    {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
                    {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
                    {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
                    {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
            };
    static int[][] p = new int[][]
            {
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
                    {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
                    {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
                    {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
                    {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
                    {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
                    {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}
            };
    static int[] inv = {0, 4, 3, 2, 1, 5, 6, 7, 8, 9};

    public static boolean validateVerhoeff(String num) {
        int c = 0;
        int[] myArray = StringToReversedIntArray(num);
        for (int i = 0; i < myArray.length; i++){
            c = d[c][p[(i % 8)][myArray[i]]];
        }

        return (c == 0);
    }
    private static int[] StringToReversedIntArray(String num) {
        int[] myArray = new int[num.length()];
        for(int i = 0; i < num.length(); i++){
            myArray[i] = Integer.parseInt(num.substring(i, i + 1));
        }
        myArray = Reverse(myArray);
        return myArray;
    }
    private static int[] Reverse(int[] myArray) {
        int[] reversed = new int[myArray.length];
        for(int i = 0; i < myArray.length ; i++){
            reversed[i] = myArray[myArray.length - (i + 1)];
        }
        return reversed;
    }

    //    public static void requestOTP(EditText mobileNumber) {
//
//        Log.e("log","Request");
//        RetrofitInterface apiService =
//                APIClient.getClient().create(RetrofitInterface.class);
//        Call<OTPMessageResponse> call = apiService.sentOTP(mobileNumber.getText().toString());
//        call.enqueue(new Callback<OTPMessageResponse>() {
//            @Override
//            public void onResponse(Call<OTPMessageResponse> call, Response<OTPMessageResponse> response) {
//                Log.e("log", call.request().url().toString());
//                sessionId = response.body().getDetails();
//                Log.e("SenderID", sessionId);
//            }
//
//            @Override
//            public void onFailure(Call<OTPMessageResponse> call, Throwable t) {
//                Log.e("ERROR1", t.toString());
//            }
//        });
//    }
//    public static void validateOTP(String otpAuto, final Context context) {
//        RetrofitInterface apiService =
//                APIClient.getClient().create(RetrofitInterface.class);
//
//        Call<OTPMessageResponse> call = apiService.verifyOTP(sessionId, otpAuto);
//
//        call.enqueue(new Callback<OTPMessageResponse>() {
//
//            @Override
//            public void onResponse(Call<OTPMessageResponse> call, Response<OTPMessageResponse> response) {
//                Log.e("log", call.request().url().toString());
//                try {
//                    if(response.body().getStatus().equals("Success")){
//                        Intent i=new Intent(context,HomeActivity.class);
//                        context.startActivity(i);
//                    }else{
//                        Log.d("Failure", response.body().getDetails()+"|||"+response.body().getStatus());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OTPMessageResponse> call, Throwable t) {
//                Log.e("ERROR", t.toString());
//            }
//
//        });
//    }
    public static String getPostalCodeByCoordinates(Context context, double lat, double lon) throws IOException {

        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        String zipcode=null;
        Address address=null;

        if (mGeocoder != null) {

            List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 5);

            if (addresses != null && addresses.size() > 0) {

                for (int i = 0; i < addresses.size(); i++) {
                    address = addresses.get(i);
                    if (address.getPostalCode() != null) {
                        zipcode = address.getPostalCode();
                        Log.e("log", "Postal code: " + address.getPostalCode());
                        break;
                    }

                }
                return zipcode;
            }
        }

        return null;
    }
    public static void makecall(String phone, Context context){
        String callNumber = phone;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" +callNumber));
        context.startActivity(intent);
    }
    public static void sendmail(String email, Context context){
        String emailId = email;
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",emailId, null));
        context.startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

}
