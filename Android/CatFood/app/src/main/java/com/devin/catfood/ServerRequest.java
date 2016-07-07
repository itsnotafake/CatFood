package com.devin.catfood;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Devin on 7/4/2016.
 */
public class ServerRequest {
    // server potentially running at 192.168.1.105
    public static final String SERVER_URL = "http://somewhere.com/";

    public static void feedTheCat(RequestQueue queue, Response.Listener responseListener, String user, String meal) {
        String url = String.format("%s/feedTheCat?user=%s&meal=%s", SERVER_URL, user, meal);
        Log.w(MainActivity.TAG, "sending feedTheCat request: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MainActivity.TAG, "onErrorResponse: " + error.getMessage());
                    }
                });
        queue.add(stringRequest);
    }
}
