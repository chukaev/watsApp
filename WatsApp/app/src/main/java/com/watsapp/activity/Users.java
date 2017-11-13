package com.watsapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.watsapp.App;
import com.watsapp.R;
import com.watsapp.UserDetails;
import com.watsapp.prelude.BSet;

import java.util.ArrayList;
import java.util.Arrays;


public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    boolean mInMultiChoiceMode;
    MenuItem sendButton;
    ArrayList<Integer> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        getSupportActionBar().setTitle("Users");

        usersList = findViewById(R.id.usersList);
        noUsersText = findViewById(R.id.noUsersText);

        BSet<Integer> bUsers = App.machine.get_user();
        Integer[] arUsers = new Integer[bUsers.size()];
        bUsers.toArray(arUsers);
        users = new ArrayList<>(Arrays.asList(arUsers));

        if (bUsers.size() <= 0) {
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        } else {
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users));
        }

        if (App.users != null)
            if (App.users.size() > 0) {

                mInMultiChoiceMode = true;
                // set checked selected item and enter multi selection mode
                usersList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                getSupportActionBar().setTitle("Broadcast");
                sendButton.setVisible(true);

                for (Integer user : App.users) {

                    usersList.setItemChecked(users.indexOf(user), true);
                }
            }


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
                    } else {
                        usersList.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    }
                } else {
                    // do whatever you should as in normal non-multi item click
                    UserDetails.chatWith = users.get(position);
                    if (!App.machine.get_select_chat().guard_select_chat(UserDetails.user, UserDetails.chatWith))
                        if (App.machine.get_create_chat_session().guard_create_chat_session(UserDetails.user, UserDetails.chatWith))

                            App.machine.get_create_chat_session().run_create_chat_session(UserDetails.user, UserDetails.chatWith);

                    if (App.machine.get_select_chat().guard_select_chat(UserDetails.user, UserDetails.chatWith)) {

                        App.machine.get_select_chat().run_select_chat(UserDetails.user, UserDetails.chatWith);
                        UserDetails.chatWith = users.get(position);
                        startActivity(new Intent(Users.this, Chat.class));
                        finish();
                    }
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
                int len = usersList.getCount();
                SparseBooleanArray checked = usersList.getCheckedItemPositions();
                for (int i = 0; i < len; i++)
                    if (checked.get(i)) {
                        App.users.add(users.get(i));
                    }
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

