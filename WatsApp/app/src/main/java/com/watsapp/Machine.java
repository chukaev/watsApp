package com.watsapp;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.watsapp.activity.Register;
import com.watsapp.sequential.machine3;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Albina on 12.11.2017.
 */

public class Machine extends machine3 {
    private Integer nextUserIndex;

    public Machine() {}

    public void setNextUserIndex(Integer index) {
        this.nextUserIndex = index;
    }

    public Integer get_nextUserIndex(){
        String url = "https://models-chat.firebaseio.com/" + "users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://models-chat.firebaseio.com/" + "users");

                if(s.equals("null")) {
                    reference.child("nextIndex").child("User").setValue(1);
                    new Machine().set_nextUserIndex(1);
                }
                else {
                    try {
                        JSONObject obj = new JSONObject(s);
                        new Machine().set_nextUserIndex(obj.getJSONObject("nextIndex").getInt("User"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError );
            }
        });

//        RequestQueue rQueue = Volley.newRequestQueue();
//        rQueue.add(request);

        if (nextUserIndex != null) {
            return nextUserIndex;
        } else {
            return 1;
        }
    }

    public void set_nextUserIndex(final Integer index){
        String url = "https://models-chat.firebaseio.com/" + "users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://models-chat.firebaseio.com/" + "users");

                reference.child("nextUserIndex").child("User").setValue(index);
                nextUserIndex = index;
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError );
            }
        });
    }
}
