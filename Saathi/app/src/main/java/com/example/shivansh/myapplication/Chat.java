package com.example.shivansh.myapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class Chat extends AppCompatActivity implements AIListener{
    private EditText chat;
    private ImageButton send;
    private ListView chatArea,chatArea2;
    private ArrayList<String> arrayList,arrayListRecieve;
    private ArrayAdapter<String> adapter,adapterRec;
    private AIService aiService;
    private static String TAG = "PermissionDemo";
    private static final int RECORD_REQUEST_CODE = 101;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech t1;
    ImageButton btnSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);chat = (EditText)findViewById(R.id.chat);
        send = (ImageButton)findViewById(R.id.send);
        chatArea = (ListView)findViewById(R.id.chatArea);
        chatArea2 = (ListView)findViewById(R.id.chatArea1);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        arrayListRecieve = new ArrayList<String>();
        arrayList = new ArrayList<String>();
        adapter = new
                ArrayAdapter<String>(this,R.layout.list_item,R.id.text11,arrayList);
        adapterRec = new
                ArrayAdapter<String>(this,R.layout.list_item2,R.id.text12,arrayListRecieve);

        chatArea.setAdapter(adapter);
        chatArea2.setAdapter(adapterRec);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = chat.getText().toString();
                arrayList.add(s);
                chat.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        final AIConfiguration config = new
                AIConfiguration("dd67f29b14a74b12a0ecd3103e0d67cd",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();
        aiRequest.setQuery("Hello");



        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }



    @Override
    public void onResult(AIResponse result) {
        Log.d("onRes", "onResult: " + result);
        ai.api.model.Result result1 = result.getResult();
    }


    @Override
    public void onError(AIError error) {

    }


    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
        Locale.getDefault();
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]
                                                   grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been granted by user");
                }
                return;
            }
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                RECORD_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data

                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String userQuery=result.get(0);
                    if(result.get(0).compareTo("") == 0){
                        userQuery = chat.getText().toString();
                    }
                    arrayList.add(userQuery);
                    adapter = new
                            ArrayAdapter<String>(this,R.layout.list_item,R.id.text11,arrayList);
                    chatArea.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    RetrieveFeedTask task=new RetrieveFeedTask();
                    task.execute(userQuery);


                }
                break;
            }

        }
    }

    public String GetText(String query) throws UnsupportedEncodingException {

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {
            // Defined URL  where to send data
            URL url = new URL("https://api.api.ai/v1/query?v=20150910");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Authorization", "Bearer dd67f29b14a74b12a0ecd3103e0d67cd");
                    conn.setRequestProperty("Content-Type", "application/json");

            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            JSONArray queryArray = new JSONArray();
            queryArray.put(query);
            Log.e("pata karo", "GetText: " + query);
            jsonParam.put("query", queryArray);
//            jsonParam.put("name", "order a medium pizza");
            jsonParam.put("lang", "en");
            jsonParam.put("sessionId", "1234567890");


            OutputStreamWriter wr = new
                    OutputStreamWriter(conn.getOutputStream());
            Log.d("karma", "after conversion is " + jsonParam);
            wr.write(jsonParam.toString());
            wr.flush();
            Log.e("yo", "GetText: " + jsonParam);
            Log.d("karma", "json is " + jsonParam);

            // Get the server response

            reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();



            JSONObject object1 = new JSONObject(text);
            JSONObject object = object1.getJSONObject("result");
            Log.e("log","OBJect : "+object);
            JSONObject fulfillment = null;
            String speech = null;
//            if (object.has("fulfillment")) {
            fulfillment = object.getJSONObject("fulfillment");
            Log.e("log", String.valueOf(fulfillment));
//                if (fulfillment.has("speech")) {
            speech = fulfillment.optString("speech");

//                }
//            }

            //Toast.makeText(this,speech + " aajaa", Toast.LENGTH_LONG).show();
            // Toast.makeText(this," aajaa", Toast.LENGTH_LONG).show();
            Log.e("karma ", "response is " + text);

            return speech;

        } catch (Exception ex) {
            Log.d("karma", "exception at last " + ex);
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        return null;
    }

    public void send(View view) {
        Chat.RetrieveFeedTask task = new RetrieveFeedTask();
        task.execute(chat.getText().toString());

    }

    public void voiceSend(View view) {

        aiService.startListening();
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            String s = null;
            try {

                s = GetText(voids[0]);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("karma", "Exception occurred " + e);
            }
            Log.e("reply aaja", "doInBackground: "+s );
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("reply aaja", "onPostExecute: " + s);
            arrayListRecieve.add(s);

            adapterRec.notifyDataSetChanged();

        }}
}
