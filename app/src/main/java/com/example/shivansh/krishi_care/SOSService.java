package com.example.shivansh.krishi_care;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SOSService extends IntentService{
    private static String CHANNEL_ID="101";
    public static int NOTIFICATION_ID = 6882;
    private Context mContext;
    public SOSService() {
        super("SOS Service");
        mContext=this;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        for(int i=0;i<1;i++) {
            RetrofitInterface apiService =
                    APIClient.getClient().create(RetrofitInterface.class);
            Call<SOSResponse> call = apiService.soscheck();

            call.enqueue(new Callback<SOSResponse>() {
                @Override
                public void onResponse(Call<SOSResponse> call, Response<SOSResponse> response) {
                    Log.e("log", call.request().url().toString());
                    try {
                        Log.e("log","Check");
                        //Toast.makeText(SignUpActivity.this,String.valueOf(response.message()),Toast.LENGTH_LONG).show();
                        Log.e("hey",response.message());
                        Log.e("Log", response.body().getName());
                        if(response.body().getName().equals("false")) {
                            Log.e("log","Build Notification");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Log.e("log","Channel");
                                CharSequence name = "Channel";
                                String description = "Description";
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                                channel.setDescription(description);
                                // Register the channel with the system; you can't change the importance
                                // or other notification behaviors after this
                                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.createNotificationChannel(channel);
                            }
                            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                                    R.drawable.ic_android_black_24dp);

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                                    .setLargeIcon(icon)
                                    .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                                    .setContentTitle("SOS Message")
                                    .setContentText("Hello this is a SOS notification")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true);
                            Log.e("log Builder", String.valueOf(mBuilder));
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                        }
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
    }
}
