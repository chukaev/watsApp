package com.watsapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.watsapp.R;
import com.watsapp.UserDetails;
import com.watsapp.adapter.ArraySwipeAdapterSample;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sessions extends AppCompatActivity {
    ListView sessionsList;
    ArrayList<String> al = new ArrayList<>();
    ProgressDialog pd;
    MenuItem sendButton;
    boolean mInMultiChoiceMode;
    private ArraySwipeAdapterSample<String> mAdapter;
    StringRequest request;
    private ArrayList<String> toRead = new ArrayList<>();

    private void setWithId(final String user) {
        String url = getString(R.string.db_ref) + "users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    UserDetails.chatWith_id = obj.getJSONObject(user).getInt("id");
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

        Volley.newRequestQueue(Sessions.this).add(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sessions);
        getSupportActionBar().setTitle("Sessions");

        sessionsList    = (ListView) findViewById(R.id.sessionsList);

        pd = new ProgressDialog(Sessions.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = getString(R.string.db_ref) + "users/"+ UserDetails.username+"/sessions.json";

        request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        Volley.newRequestQueue(Sessions.this).add(request);

        sessionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mInMultiChoiceMode) {
                    //exit multi choice mode if number of selected items is 0
                    if (sessionsList.getCheckedItemCount() == 0) {

                        sessionsList.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                        mInMultiChoiceMode = false;
                        getSupportActionBar().setTitle("Users");
                        sendButton.setVisible(false);
                    }

                    if (sessionsList.isItemChecked(position)) {
                        sessionsList.getChildAt(position).setBackgroundColor(Color.BLUE);
                    }
                    else {
                        sessionsList.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    }
                } else {
                    UserDetails.chatWith = al.get(position);

                    String url = getString(R.string.db_ref) + "users.json";
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject obj = new JSONObject(s);
                                UserDetails.chatWith_id = obj.getJSONObject(UserDetails.chatWith).getInt("id");
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
                    Volley.newRequestQueue(Sessions.this).add(request);

                    startActivity(new Intent(Sessions.this, Chat.class));
                    finish();
                }
            }
        });

        sessionsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                if (mInMultiChoiceMode) {
                    // if already in multi choice - do nothing
                    return false;
                }

                mInMultiChoiceMode = true;
                // set checked selected item and enter multi selection mode
                sessionsList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                sessionsList.setItemChecked(position, true);
                sessionsList.getChildAt(position).setBackgroundColor(Color.BLUE);
                getSupportActionBar().setTitle("Broadcast");
                sendButton.setVisible(true);
                return true;
            }
        });
    }

    public boolean deleteSession(String user)
    {
        Firebase.setAndroidContext(this);
        new Firebase(String.format(getString(R.string.db_ref) + "users/%s/sessions/", UserDetails.username)).child(user).removeValue();

        if(sessionsList.getChildCount() == 1){
            startActivity(new Intent(this, Users.class));
            this.finish();
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Volley.newRequestQueue(Sessions.this).add(request);
                }
            }, 2000);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<String> users = new ArrayList<>();
        int len = sessionsList.getCount();
        SparseBooleanArray checked = sessionsList.getCheckedItemPositions();
        if(checked!=null)
        for (int i = 0; i < len; i++)
            if (checked.get(i)) {
                users.add(al.get(i));
            }
        String[] usersArray = new String[0];
        usersArray = users.toArray(usersArray);
        switch (item.getItemId()) {
            case R.id.createSession:{
                Intent intent = new Intent(Sessions.this, Users.class);
                intent.putExtra("users", usersArray);
                startActivity(intent);
                return true;}
            case R.id.sendBroadcast:{
                Intent intent = new Intent(Sessions.this, Broadcast.class);
                intent.putExtra("users", usersArray);
                startActivity(intent);
                finish();
                return true;}
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sessionsmenu, menu);

        sendButton = menu.findItem(R.id.sendBroadcast);
        sendButton.setVisible(false);
        return true;
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key;

            al.clear();
            toRead.clear();

            while(i.hasNext()) {
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {

                    if (obj.getJSONObject(key).has("unread") && obj.getJSONObject(key).getBoolean("unread"))

                        toRead.add(key);

                    al.add(key);
                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if(al.size() <=0){
            startActivity(new Intent(this, Users.class));
            this.finish();
        }

        else{
            sessionsList.setVisibility(View.VISIBLE);
            if (mAdapter == null) {

                mAdapter = new ArraySwipeAdapterSample<>(this, R.layout.listview_item, R.id.position, al, toRead);
                sessionsList.setAdapter(mAdapter);
            }
            else mAdapter.notifyDataSetChanged();
        }

        pd.dismiss();
    }
}
