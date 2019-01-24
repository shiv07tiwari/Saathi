package com.example.shivansh.krishi_care;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ImageHeaderParser;

import org.w3c.dom.Text;

import static com.example.shivansh.krishi_care.SignUpActivity.MY_PREFS_NAME;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView user_image = findViewById(R.id.home_image);
        TextView user_name = findViewById(R.id.home_name);
        TextView user_email = findViewById(R.id.home_email);
        TextView user_phone = findViewById(R.id.home_contact);
        TextView user_aadhar = findViewById(R.id.home_aadhar);

        SharedPreferences sharedPref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        user_name.setText(sharedPref.getString("user_name","Hello"));
        user_email.setText(sharedPref.getString("user_email","sih@iiita.ac.in"));
        user_aadhar.setText(sharedPref.getString("user_aadhar","XXXXXXXXXX"));
        user_phone.setText(sharedPref.getString("user_phone","8989419500"));
        String user_uri = sharedPref.getString("user_image_uri",null);
        user_image.setImageURI(Uri.parse(user_uri));

        ImageView krishi_bot = findViewById(R.id.krishi_bot_image);
        ImageView krishi_image = findViewById(R.id.krishi_weather);
        ImageView krishi_weather = findViewById(R.id.krishi_history);
        ImageView krishi_predcit = findViewById(R.id.krishi_predict);
        ImageView krishi_expert = findViewById(R.id.krishi_advice);
        ImageView krishi_disease = findViewById(R.id.krishi_disease);
        ImageView krishi_general = findViewById(R.id.krishi_general);
        ImageView krishi_complaint = findViewById(R.id.krishi_complaint);
        ImageView krishi_soil = findViewById(R.id.krishi_soil);

        Glide.with(this).load(R.drawable.chatbot).into(krishi_bot);
        Glide.with(this).load(R.drawable.weather).into(krishi_image);
        Glide.with(this).load(R.drawable.history).into(krishi_weather);
        Glide.with(this).load(R.drawable.predict).into(krishi_predcit);
        Glide.with(this).load(R.drawable.expert_1346618).into(krishi_expert);
        Glide.with(this).load(R.drawable.disease).into(krishi_disease);
        Glide.with(this).load(R.drawable.tips).into(krishi_general);
        Glide.with(this).load(R.drawable.farmer).into(krishi_complaint);
        Glide.with(this).load(R.drawable.sand).into(krishi_soil);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
