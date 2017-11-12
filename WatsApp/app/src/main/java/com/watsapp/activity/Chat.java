package com.watsapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.watsapp.App;
import com.watsapp.R;
import com.watsapp.UserDetails;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase refFrom, refTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setTitle(UserDetails.chatWith);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        refFrom = new Firebase(String.format(getString(R.string.db_ref) + "users/%s/sessions/%s/messages", UserDetails.username, UserDetails.chatWith));
        refTo = new Firebase(String.format(getString(R.string.db_ref) + "users/%s/sessions/%s", UserDetails.chatWith, UserDetails.username));

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                if(!messageText.equals("")){

                    HashMap map = new HashMap();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("timestamp", ServerValue.TIMESTAMP);
//                    if (App.getMachine().get_chat())
                    refFrom.push().setValue(map);

                    map.put("unread", true);
                    refTo.child("messages").push().setValue(map);

                    map = new HashMap();
                    map.put("unread", true);
                    refTo.updateChildren(map);

                    messageArea.setText("");
                }
            }
        });

        refFrom.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                if (map == null) return;
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                boolean unread = map.get("unread") != null && map.get("unread").toString() == "true";

                if(userName.equals(UserDetails.username)){
                    addMessageBox(message, 1, unread);
                }
                else{
                    addMessageBox(message, 2, unread);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, Sessions.class));
        this.finish();
    }

    public void addMessageBox(String message, int type, boolean unread){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);

        if (unread)

            textView.setTextColor(Color.DKGRAY);

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}