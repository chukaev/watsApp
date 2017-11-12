package com.watsapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.firebase.client.Firebase;
import com.watsapp.sequential.machine3;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Albina on 12.11.2017.
 */

public class Machine extends machine3 {
    private static Integer nextUserIndex;

    public static Integer get_nextUserIndex(){
        String url = "https://models-chat.firebaseio.com/" + "nextUserIndex.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://models-chat.firebaseio.com/" + "nextUserIndex");

                if(s.equals("null")) {
                    reference.child("nextUserIndex").setValue(1);
                    nextUserIndex = 1;
                }
                else {
                    try {
                        JSONObject obj = new JSONObject(s);
                        nextUserIndex = obj.getInt("nextUserIndex");
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

        return nextUserIndex;
    }

    public static void set_nextUserIndex(final Integer index){
        String url = "https://models-chat.firebaseio.com/" + "nextUserIndex.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://models-chat.firebaseio.com/" + "nextUserIndex");

                if(s.equals("null")) {
                    reference.child("nextUserIndex").setValue(index);
                    nextUserIndex = index;
                }
                else {
                    try {
                        JSONObject obj = new JSONObject(s);
                        nextUserIndex = obj.getInt("nextUserIndex");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                reference.child("nextUserIndex").setValue(index);
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
