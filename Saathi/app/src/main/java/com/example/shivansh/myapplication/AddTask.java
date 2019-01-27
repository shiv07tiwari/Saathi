package com.example.shivansh.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTask extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button setDate,setTime;
    private String day,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final EditText title = findViewById(R.id.add_title);
        final EditText desc = findViewById(R.id.add_description);
        setDate = findViewById(R.id.setDate);
        setTime = findViewById(R.id.setTime);

        Button send = findViewById(R.id.submit);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = title.getText().toString();
                String mdesc = desc.getText().toString();
                RetroFit apiService =
                        APIClient.getSOSClient().create(RetroFit.class);
                Call<SOSResponse> call = apiService.addTodoTask(fname,mdesc,time,day);

                call.enqueue(new Callback<SOSResponse>() {

                    @Override
                    public void onResponse(Call<SOSResponse> call, Response<SOSResponse> response) {
                        Log.e("log", call.request().url().toString());
                        try {
                            //Toast.makeText(SignUp.this,String.valueOf(response.message()),Toast.LENGTH_LONG).show();
                            // Log.e("Log", String.valueOf(response.body()));
                            SOSResponse list = response.body();
                            Log.e("log", String.valueOf(response.message()));
                            Log.e("log",list.getName());
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
                finish();
            }
        });


    }

    public void setDate(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        day = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        setDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();



    }

    public void setTime(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        time = hourOfDay + ":" + minute;
                        setTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}




