package com.watsapp;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.watsapp.prelude.BSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by yuriy on 12/11/2017.
 */

public class App extends Application {
    public static Machine getMachine() {
        return machine;
    }

    private static Machine machine;

    @Override
    public void onCreate(){
        super.onCreate();
        machine = new Machine();

        String url = getString(R.string.db_ref) + "users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    Iterator i = obj.keys();
                    String key;

                    while(i.hasNext()) {
                        key = i.next().toString();
                        machine.add_user(new BSet<Integer>(obj.getJSONObject(key).getInt("id")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
        Volley.newRequestQueue(App.this).add(request);
    }
}
