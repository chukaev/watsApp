package com.watsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.util.HashMap;

/**
 * Created by yuriy on 02/11/2017.
 */
public class Broadcast extends AppCompatActivity {

    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    String[] users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        getSupportActionBar().setTitle("Broadcast");
        users = getIntent().getStringArrayExtra("users");
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                if(!messageText.equals("")){
                    HashMap map = new HashMap();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("timestamp", ServerValue.TIMESTAMP);
                    for (String chatWith: users) {
                        new Firebase(String.format(getString(R.string.db_ref) + "users/%s/sessions/%s/messages", UserDetails.username, chatWith)).push().setValue(map);
                    }
                    map.put("unread", true);
                    for (String chatWith: users) {
                        Firebase refTo = new Firebase(String.format(getString(R.string.db_ref) + "users/%s/sessions/%s", chatWith, UserDetails.username));
                        refTo.child("messages").push().setValue(map);
                        refTo.child("to_read").push().setValue(true);
                    }
                    messageArea.setText("");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Sessions.class));
        this.finish();
    }
}
