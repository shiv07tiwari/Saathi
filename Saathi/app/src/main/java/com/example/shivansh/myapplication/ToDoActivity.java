package com.example.shivansh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<ToDoBody> myDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setVisibility(View.GONE);

        RetroFit apiService =
                APIClient.getSOSClient().create(RetroFit.class);
        Call<List<ToDoBody>> call = apiService.getAllToDo();

        call.enqueue(new Callback<List<ToDoBody>>() {

            @Override
            public void onResponse(Call<List<ToDoBody>> call, Response<List<ToDoBody>> response) {
                Log.e("log", call.request().url().toString());
                mRecyclerView.setVisibility(View.VISIBLE);
                ProgressBar bar = findViewById(R.id.progress);
                bar.setVisibility(View.GONE);

                myDataset = new ArrayList<>();
                try {
                    //Toast.makeText(SignUp.this,String.valueOf(response.message()),Toast.LENGTH_LONG).show();
                    Log.e("Log", String.valueOf(response.body()));
                    List<ToDoBody> list = response.body();
                    Log.e("size", String.valueOf(list.size()));
                    for (ToDoBody l: list) {
                        myDataset.add(l);
                        Log.e("logvhfhfj",l.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAdapter = new MyAdapter2(myDataset);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<ToDoBody>> call, Throwable t) {
                Log.e("log", call.request().url().toString());
                Log.e("ERROR2", t.toString());
            }

        });

        mAdapter = new MyAdapter2(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void AddTask(View view) {
        Intent i=new Intent(ToDoActivity.this,AddTask.class);
        startActivity(i);
    }
}
