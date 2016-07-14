package com.devin.catfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    public static String TAG = "dg";
    public static int MEALCHECK_CYCLE = 20;
    public boolean breakfastEnabled = true;
    public boolean dinnerEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for using Volley
        VolleySingleton volley = VolleySingleton.getInstance(this);
        final RequestQueue queue = volley.getRequestQueue();

        //on application startup we run mealCheck to update our booleans
        ServerRequest.mealCheck(queue, mMealCheckListener);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setEnabled(breakfastEnabled);
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBox.setEnabled(false);
                Log.w(TAG, "checkBox pressed");
                ServerRequest.feedTheCat(queue, mFeedTheCatResponseListener, "devin", "breakfast");
            }
        });

        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setEnabled(dinnerEnabled);
        checkBox2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkBox2.setEnabled(false);
                Log.w(TAG, "checkBox2 pressed");
                ServerRequest.feedTheCat(queue, mFeedTheCatResponseListener, "devin", "dinner");
            }
        });

        Runnable helloRunnable = new Runnable() {
            //@Override
            public void run() {
                Log.w(TAG, "Hello Runnable: " + Thread.currentThread().getId());
                ServerRequest.mealCheck(queue, mMealCheckListener);
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, MEALCHECK_CYCLE, TimeUnit.SECONDS);

        // test sending a server request
        //ServerRequest.feedTheCat(queue, mFeedTheCatResponseListener, "devin", "breakfast");
        Log.w(TAG, "Hello MainThread: " + Thread.currentThread().getId());
    }

    private Response.Listener<String> mFeedTheCatResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.w(TAG, "FeedTheCatResponse is: " + response);
        }
    };

    private Response.Listener<String> mMealCheckListener = new Response.Listener<String>(){
        @Override
        public void onResponse(String response) {
            Log.w(TAG, "MealCheckResponse is: " + response);
        }
    };
}
