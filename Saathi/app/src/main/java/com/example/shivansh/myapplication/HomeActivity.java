package com.example.shivansh.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shivansh.myapplication.SignUp.MY_PREFS_NAME;

public class HomeActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> myDataset;

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("log","On Start");
        RetroFit apiService =
                APIClient.getSOSClient().create(RetroFit.class);
        Call<List<AddCaretakerBody>> call = apiService.getAllCaretaker();
        call.enqueue(new Callback<List<AddCaretakerBody>>() {

            @Override
            public void onResponse(Call<List<AddCaretakerBody>> call, Response<List<AddCaretakerBody>> response) {
                Log.e("log", call.request().url().toString());
                mRecyclerView.setVisibility(View.VISIBLE);
                ProgressBar bar = findViewById(R.id.progress);
                bar.setVisibility(View.GONE);

                myDataset = new ArrayList<>();
                try {
                    //Toast.makeText(SignUp.this,String.valueOf(response.message()),Toast.LENGTH_LONG).show();
                    Log.e("Log", String.valueOf(response.body()));
                    List<AddCaretakerBody> list = response.body();
                    Log.e("size", String.valueOf(list.size()));
                    for (AddCaretakerBody l: list
                         ) {
                        myDataset.add(l.getName());
                        Log.e("log",l.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                myDataset.add("Test");
                mAdapter = new MyAdapter(myDataset,HomeActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<AddCaretakerBody>> call, Throwable t) {
                Log.e("log", call.request().url().toString());
                Log.e("ERROR2", t.toString());
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setVisibility(View.GONE);
        ProgressBar bar = findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        mGoogleApiClient.connect();

        ImageView user_image = findViewById(R.id.home_image);
        TextView user_name = findViewById(R.id.home_name);

        CardView sos = findViewById(R.id.SOS_button);
        CardView chatBotStart = findViewById(R.id.chatBotStart);
        CardView contactDoctor = findViewById(R.id.contactDoc);
        CardView todo = findViewById(R.id.todo_button);

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this,ToDoActivity.class);
                startActivity(i1);
            }
        });
        contactDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.makecall("8989419500",HomeActivity.this);
            }
        });

        chatBotStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this,Chat.class);
                startActivity(i1);
            }
        });

        user_name.setText("Shivansh Tiwari");

        sos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                        RetroFit apiService =
                APIClient.getSOSClient().create(RetroFit.class);
        Call<SOSResponse> call = apiService.makeSOS();

        call.enqueue(new Callback<SOSResponse>() {

            @Override
            public void onResponse(Call<SOSResponse> call, Response<SOSResponse> response) {
                Log.e("log", call.request().url().toString());
                try {
                    Log.e("log","Check");
                    SOSResponse list = response.body();
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
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_settings) {
            SignUp.editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            SignUp.editor.putBoolean("is_logedin", false);
            SignUp.editor.apply();
            Intent myIntent = new Intent(HomeActivity.this, SignUp.class);
            HomeActivity.this.startActivity(myIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("Log","Connected");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.e("log","Permission");
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            Log.e("log","Null");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Log.e("log", String.valueOf(currentLatitude+currentLongitude));
            //Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("log","Location Changed");
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        //Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }
}
