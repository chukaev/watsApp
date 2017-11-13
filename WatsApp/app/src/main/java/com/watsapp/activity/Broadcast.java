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
import com.watsapp.App;
import com.watsapp.R;
import com.watsapp.UserDetails;

import java.util.HashMap;

public class Broadcast extends AppCompatActivity {

    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        getSupportActionBar().setTitle("Broadcast");
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
                    map.put("user", UserDetails.user);
                    map.put("timestamp", ServerValue.TIMESTAMP);
                    for (Integer chatWith: App.users) {
                        new Firebase(String.format(getString(R.string.db_ref) + "users/%s/sessions/%s/messages", UserDetails.user, chatWith)).push().setValue(map);
                    }
                    map.put("unread", true);
                    for (Integer chatWith: App.users) {
                        Firebase refTo = new Firebase(String.format(getString(R.string.db_ref) + "users/%s/sessions/%s", chatWith, UserDetails.user));
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
