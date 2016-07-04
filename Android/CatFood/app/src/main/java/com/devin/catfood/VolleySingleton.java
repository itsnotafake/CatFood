package com.devin.catfood;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by s.gibbs on 7/4/2016.
 */
public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;

    private VolleySingleton(final Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleySingleton getInstance(Context context) {
        if (instance == null)
            instance = new VolleySingleton(context);
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
