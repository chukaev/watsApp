package com.watsapp.sequential;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;

public class Machine extends machine3 {

    ObjectMapper mapper = new ObjectMapper();
    Gson gson = new GsonBuilder().create();

    public Machine() {
        super();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey() == null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        onDataChange(snapshot);
                    return;
                }
                try {
                    switch (dataSnapshot.getKey()) {

                        case "owner":
                            Machine.super.set_owner(mapper.readValue(dataSnapshot.getValue(String.class), BRelation.class));
                            break;
                        case "toread":
                            Machine.super.set_toread(mapper.readValue(dataSnapshot.getValue(String.class), BRelation.class));
                            break;
                        case "inactive":
                            Machine.super.set_inactive(mapper.readValue(dataSnapshot.getValue(String.class), BRelation.class));
                            break;
                        case "chatcontent":
                            Machine.super.set_chatcontent(mapper.readValue(dataSnapshot.getValue(String.class), BRelation.class));
                            //EventBus.getDefault().post(new UsersEvent());
                            break;
                        case "chat":
                            Machine.super.set_chat(mapper.readValue(dataSnapshot.getValue(String.class), BRelation.class));
                            break;
                        case "user":
                            Machine.super.set_user(mapper.readValue(dataSnapshot.getValue(String.class), BSet.class));
                            break;
                        case "active":
                            Machine.super.set_active(mapper.readValue(dataSnapshot.getValue(String.class), BRelation.class));
                            break;
                        case "muted":
                            Machine.super.set_muted(mapper.readValue(dataSnapshot.getValue(String.class), BRelation.class));
                            break;
                        case "content":
                            Machine.super.set_content(mapper.readValue(dataSnapshot.getValue(String.class), BSet.class));
                            break;
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        reference.addValueEventListener(valueEventListener);
    }

    @Override
    public void set_owner(BRelation<Integer, Integer> owner) {
        super.set_owner(owner);
        FirebaseDatabase.getInstance().getReference("owner").setValue(gson.toJson(owner));
    }

    @Override
    public void set_toread(BRelation<Integer, Integer> toread) {
        super.set_toread(toread);
        FirebaseDatabase.getInstance().getReference("toread").setValue(gson.toJson(toread));
    }

    @Override
    public void set_inactive(BRelation<Integer, Integer> inactive) {
        super.set_inactive(inactive);
        FirebaseDatabase.getInstance().getReference("inactive").setValue(gson.toJson(inactive));
    }

    @Override
    public void set_chat(BRelation<Integer, Integer> chat) {
        super.set_chat(chat);
        FirebaseDatabase.getInstance().getReference("chat").setValue(gson.toJson(chat));
    }

    @Override
    public void set_active(BRelation<Integer, Integer> active) {
        super.set_active(active);
        FirebaseDatabase.getInstance().getReference("active").setValue(gson.toJson(active));
    }

    @Override
    public void set_muted(BRelation<Integer, Integer> muted) {
        super.set_muted(muted);
        FirebaseDatabase.getInstance().getReference("muted").setValue(gson.toJson(muted));
    }

    @Override
    public void set_user(BSet<Integer> user) {
        super.set_user(user);
        FirebaseDatabase.getInstance().getReference("user").setValue(gson.toJson(user));
    }

    @Override
    public void set_content(BSet<Integer> content) {
        super.set_content(content);
        FirebaseDatabase.getInstance().getReference("content").setValue(gson.toJson(content));
    }

    public broadcast1 get_broadcast1() {
        return evt_broadcast1;
    }

    public broadcast1 get_broadcast() {
        return evt_broadcast1;
    }

    public unselect_chat get_unselect_chat() {
        return evt_unselect_chat;
    }

    public forward1 get_forward1() {
        return evt_forward1;
    }

    public forward2 get_forward2() {
        return evt_forward2;
    }

    public delete_content get_delete_content() {
        return evt_delete_content;
    }

    public create_chat_session get_create_chat_session() {
        return evt_create_chat_session;
    }

    public reading_chat get_reading_chat() {
        return evt_reading_chat;
    }

    public delete_chat_session get_delete_chat_session() {
        return evt_delete_chat_session;
    }

    public unmute_chat get_unmute_chat() {
        return evt_unmute_chat;
    }

    public select_chat get_select_chat() {
        return evt_select_chat;
    }

    public mute_chat get_mute_chat() {
        return evt_mute_chat;
    }

    public chatting get_chatting() {
        return evt_chatting;
    }

    public remove_content get_remove_content() {
        return evt_remove_content;
    }
}
