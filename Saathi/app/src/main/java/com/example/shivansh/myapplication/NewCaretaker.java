package com.example.shivansh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import javax.crypto.BadPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCaretaker extends AppCompatActivity {


    private String mName;
    private String mContact;
    private String mRelation;
    private String fName;
    private String fContact;
    private String fRelation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_caretaker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mName= null;
                mContact=null;
                mRelation=null;
            } else {
                mContact= extras.getString("contact");
                mName=extras.getString("name");
                mRelation=extras.getString("relation");
            }
        }
        final EditText name = findViewById(R.id.care_name);
        final EditText phone = findViewById(R.id.care_number);
        final EditText relation = findViewById(R.id.care_relation);
        Button submit = findViewById(R.id.create_new_caretaker);
        name.setText(mName);
        phone.setText(mContact);
        relation.setText(mRelation);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fName = name.getText().toString();
                fContact=phone.getText().toString();
                fRelation=relation.getText().toString();
                RetroFit apiService =
                APIClient.getSOSClient().create(RetroFit.class);
        Log.e("log",fName);
        Call<AddCaretakerBody> call = apiService.addCaretaker(fName,fContact,fRelation);

        call.enqueue(new Callback<AddCaretakerBody>() {

            @Override
            public void onResponse(Call<AddCaretakerBody> call, Response<AddCaretakerBody> response) {
                Log.e("log", call.request().url().toString());
                try {
                    //Toast.makeText(SignUp.this,String.valueOf(response.message()),Toast.LENGTH_LONG).show();
                   // Log.e("Log", String.valueOf(response.body()));
                    AddCaretakerBody list = response.body();
                    Log.e("log", String.valueOf(response.message()));
                    Log.e("log",list.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddCaretakerBody> call, Throwable t) {
                Log.e("log", call.request().url().toString());
                Log.e("ERROR2", t.toString());
            }

        });
        }
        });
    }

}
