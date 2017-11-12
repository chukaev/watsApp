package com.watsapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.watsapp.R;
import com.watsapp.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    ProgressDialog pd;
    boolean mInMultiChoiceMode;
    MenuItem sendButton;
    private String[] users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        getSupportActionBar().setTitle("Users");

        users = getIntent().getStringArrayExtra("users");
        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = getString(R.string.db_ref) + "users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);


        usersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (mInMultiChoiceMode) {
                    // if already in multi choice - do nothing
                    return false;
                }

                mInMultiChoiceMode = true;
                // set checked selected item and enter multi selection mode
                usersList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                usersList.setItemChecked(position, true);
                usersList.getChildAt(position).setBackgroundColor(Color.BLUE);
                getSupportActionBar().setTitle("Broadcast");
                sendButton.setVisible(true);
                return true;
            }
        });

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                if (mInMultiChoiceMode) {
                    //exit multi choice mode if number of selected items is 0
                    if (usersList.getCheckedItemCount() == 0) {

                        usersList.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                        mInMultiChoiceMode = false;
                        getSupportActionBar().setTitle("Users");
                        sendButton.setVisible(false);
                    }

                    if (usersList.isItemChecked(position)) {
                        usersList.getChildAt(position).setBackgroundColor(Color.BLUE);
                    }
                    else {
                        usersList.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    }
                } else {
                    // do whatever you should as in normal non-multi item click
                    UserDetails.chatWith = al.get(position);
                    startActivity(new Intent(Users.this, Chat.class));
                    finish();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usersmenu, menu);

        sendButton = menu.findItem(R.id.sendBroadcast);
        sendButton.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sendBroadcast:
                Intent intent = new Intent(Users.this, Broadcast.class);
                List<String> users = new ArrayList<>();
                int len = usersList.getCount();
                SparseBooleanArray checked = usersList.getCheckedItemPositions();
                for (int i = 0; i < len; i++)
                    if (checked.get(i)) {
                        users.add(al.get(i));
                    }
                String[] usersArray = new String[users.size()];
                usersArray = users.toArray(usersArray);
                intent.putExtra("users", usersArray);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key;

            while(i.hasNext()) {

                key = i.next().toString();

                if(!key.equals(UserDetails.username) && !key.equals("nextIndex"))
                {
                    al.add(key);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(al.size() <=0){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al));
        }

        if (users != null)
        if (users.length>0) {

            mInMultiChoiceMode = true;
            // set checked selected item and enter multi selection mode
            usersList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            getSupportActionBar().setTitle("Broadcast");
            sendButton.setVisible(true);

            for (String user : users) {

                int position = al.indexOf(user);
                usersList.setItemChecked(position, true);
                usersList.getChildAt(position).setBackgroundColor(Color.BLUE);
            }
        }
        pd.dismiss();
    }
}

