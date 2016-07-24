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
    private CheckBox breakfastBox;
    private CheckBox dinnerBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for using Volley
        VolleySingleton volley = VolleySingleton.getInstance(this);
        final RequestQueue queue = volley.getRequestQueue();

        //on application startup we run mealCheck to update our booleans
        ServerRequest.mealCheck(queue, mMealCheckListener);

        breakfastBox = (CheckBox) findViewById(R.id.breakfastBox);
        breakfastBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                breakfastBox.setEnabled(false);
                Log.w(TAG, "breakfastBox pressed");
                ServerRequest.feedTheCat(queue, mFeedTheCatResponseListener, "devin", "breakfast");
            }
        });

        dinnerBox = (CheckBox) findViewById(R.id.dinnerBox);
        dinnerBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dinnerBox.setEnabled(false);
                Log.w(TAG, "dinnerBox pressed");
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
            //unknown_user, unknown_meal, already_fed, ok, broken_request
            if(response.equals("ok")){
                Log.w(TAG,"FeedTheCatResponse: " + response);
            }else{
                Log.e(TAG,"FeedTheCatResponse: " + response);
            }
        }
    };

    private Response.Listener<String> mMealCheckListener = new Response.Listener<String>(){
        @Override
        public void onResponse(String response) {
            Log.w(TAG, "MealCheckResponse is: " + response);
            String[] booleanResponse = response.split(" ");
            boolean breakfastEnabled = Boolean.getBoolean(booleanResponse[0]);
            boolean dinnerEnabled = Boolean.getBoolean(booleanResponse[1]);
            Log.w(TAG, "<" + dinnerEnabled + ">");


            if(breakfastEnabled){
                breakfastBox.setEnabled(true);
                breakfastBox.setChecked(false);
            }else{
                breakfastBox.setEnabled(false);
                breakfastBox.setChecked(true);
            }

            if(dinnerEnabled){
                dinnerBox.setEnabled(true);
                dinnerBox.setChecked(false);
            }else {
                dinnerBox.setEnabled(false);
                dinnerBox.setChecked(true);
            }
        }
    };
}
