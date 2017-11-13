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
import android.widget.ListView;

import com.watsapp.App;
import com.watsapp.R;
import com.watsapp.UserDetails;
import com.watsapp.adapter.ArraySwipeAdapterSample;
import com.watsapp.prelude.BSet;

import java.util.ArrayList;
import java.util.Arrays;

public class Sessions extends AppCompatActivity {
    ListView sessionsList;
    ArrayList<Integer> users = new ArrayList<>();
    MenuItem sendButton;
    boolean mInMultiChoiceMode;
    private ArraySwipeAdapterSample<String> mAdapter;
    private ArrayList<String> toRead = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.machine.set_active(App.machine.get_active().domainSubtraction(new BSet<>(UserDetails.user)));

        setContentView(R.layout.activity_sessions);
        getSupportActionBar().setTitle("Sessions");

        sessionsList = (ListView) findViewById(R.id.sessionsList);

        BSet<Integer> bSessions = App.machine.get_chat().restrictDomainTo(new BSet<>(UserDetails.user)).range();
        ArrayList<Integer> sessions = new ArrayList<>(Arrays.asList(Arrays.copyOf(bSessions.toArray(), bSessions.size(), Integer[].class)));

        BSet<Integer> bUsers = App.machine.get_user();
        Integer[] arUsers = new Integer[bUsers.size()];
        bUsers.toArray(arUsers);
        users = new ArrayList<>(Arrays.asList(arUsers));

        if (bSessions.size() <= 0) {
            startActivity(new Intent(this, Users.class));
            this.finish();
        } else {
            sessionsList.setVisibility(View.VISIBLE);

            if (mAdapter == null) {

                mAdapter = new ArraySwipeAdapterSample<>(this, R.layout.listview_item, R.id.position, sessions, toRead);
                sessionsList.setAdapter(mAdapter);
            } else mAdapter.notifyDataSetChanged();
        }


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
                    } else {
                        sessionsList.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    }
                } else {
                    UserDetails.chatWith = users.get(position);
                    if (!App.machine.get_select_chat().guard_select_chat(UserDetails.user, UserDetails.chatWith))
                        if (App.machine.get_create_chat_session().guard_create_chat_session(UserDetails.user, UserDetails.chatWith))
                            App.machine.get_create_chat_session().run_create_chat_session(UserDetails.user, UserDetails.chatWith);

                    if (App.machine.get_select_chat().guard_select_chat(UserDetails.user, UserDetails.chatWith)) {

                        App.machine.get_select_chat().run_select_chat(UserDetails.user, UserDetails.chatWith);
                        UserDetails.chatWith = users.get(position);
                        startActivity(new Intent(Sessions.this, Chat.class));
                        finish();
                    }
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

    public boolean deleteSession(Integer user)
    {
        if(App.machine.get_delete_chat_session().guard_delete_chat_session(UserDetails.user, user))

            App.machine.get_delete_chat_session().run_delete_chat_session(UserDetails.user, user);

        else return false;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int len = sessionsList.getCount();
        SparseBooleanArray checked = sessionsList.getCheckedItemPositions();
        if(checked!=null)
        for (int i = 0; i < len; i++)
            if (checked.get(i)) {
                App.users.add(users.get(i));
            }

        switch (item.getItemId()) {
            case R.id.createSession:{
                Intent intent = new Intent(Sessions.this, Users.class);
                startActivity(intent);
                return true;}
            case R.id.sendBroadcast:{
                Intent intent = new Intent(Sessions.this, Broadcast.class);
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
}
