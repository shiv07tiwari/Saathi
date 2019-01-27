package com.example.shivansh.krishi_care;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAnalysis extends AppCompatActivity {

    private String per;
    ProgressBar p2;
    ProgressBar p1;
    LinearLayout l;
    @Override
    protected void onStart() {
        super.onStart();
        RetrofitInterface apiService =
                APIClient.getClient().create(RetrofitInterface.class);
        Call<SOSResponse> call = apiService.getSentiment();

        call.enqueue(new Callback<SOSResponse>() {
            @Override
            public void onResponse(Call<SOSResponse> call, Response<SOSResponse> response) {
                Log.e("log", call.request().url().toString());

                try {
                    p1.setVisibility(View.GONE);
                    l.setVisibility(View.VISIBLE);
                    Log.e("log","Check");
                    //Toast.makeText(SignUpActivity.this,String.valueOf(response.message()),Toast.LENGTH_LONG).show();
                    Log.e("hey",response.message());
                    Log.e("Log", response.body().getName());
                    per = response.body().getName();
                    float t = Float.parseFloat(per);
                    p2.setProgress((int) (t*100));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SOSResponse> call, Throwable t) {
                Log.e("log", call.request().url().toString());
                Log.e("ERROR2", t.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        p1 = findViewById(R.id.progressBar);
        p2 = findViewById(R.id.ProgressBarShow);
        l = findViewById(R.id.linear_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
