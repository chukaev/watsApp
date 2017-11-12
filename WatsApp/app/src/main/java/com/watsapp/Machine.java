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
import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;
import com.watsapp.prelude.Pair;
import com.watsapp.sequential.machine3;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Albina on 12.11.2017.
 */

public class Machine extends machine3 {

    public void add_active(BRelation<Integer,Integer> active) {
        this.set_active(this.get_active().union(active));
    };

//    /*@ spec_public */ private BRelation<Integer,Integer> c_seq;
//
//    /*@ spec_public */ private Integer c_size;

    public void add_chat(BRelation<Integer,Integer> chat) {
        this.set_chat(this.get_chat().union(chat));
    };

    public void add_chatcontent(BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent) {
        this.set_chatcontent(this.get_chatcontent().union(chatcontent));
    };

    public void add_content(BSet<Integer> content) {
        this.set_content(this.get_content().union(content));
    };

    public void add_inactive(BRelation<Integer,Integer> inactive) {
        this.set_inactive(this.get_inactive().union(inactive));
    };

    public void add_muted(BRelation<Integer,Integer> muted) {
        this.set_muted(this.get_muted().union(muted));
    };

    public void add_owner(BRelation<Integer,Integer> owner) {
        this.set_owner(this.get_owner().union(owner));
    };

    public void add_toread(BRelation<Integer,Integer> toread) {
        this.set_toread(this.get_toread().union(toread));
    };

    public void add_user(BSet<Integer> user) {
        this.set_user(this.get_user().union(user));
    };

}
