package com.devin.catfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.android.volley.RequestQueue;
import com.android.volley.Response;


public class MainActivity extends AppCompatActivity {

    public static String TAG = "dg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBox.setEnabled(false);
                Log.w(TAG, "checkBox pressed");
            }
        });

        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBox2.setEnabled(false);
                Log.w(TAG, "checkBox2 pressed");
            }
        });

        // for using Volley
        VolleySingleton volley = VolleySingleton.getInstance(this);
        RequestQueue queue = volley.getRequestQueue();

        // test sending a server request
        ServerRequest.feedTheCat(queue, mFeedTheCatResponseListener, "devin", "breakfast");
    }

    private Response.Listener<String> mFeedTheCatResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.w(TAG, "FeedTheCatResponse is: " + response);
        }
    };
}
