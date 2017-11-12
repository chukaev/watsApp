package com.watsapp.sequential;

import com.watsapp.util.Utilities;
import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;
import com.watsapp.prelude.Enumerated;

public class machine3{
	private static final Integer max_integer = Utilities.max_integer;
	private static final Integer min_integer = Utilities.min_integer;

	forward1 evt_forward1 = new forward1(this);
	forward2 evt_forward2 = new forward2(this);
	unselect_chat evt_unselect_chat = new unselect_chat(this);
	broadcast2 evt_broadcast2 = new broadcast2(this);
	delete_content evt_delete_content = new delete_content(this);
	broadcast1 evt_broadcast1 = new broadcast1(this);
	create_chat_session evt_create_chat_session = new create_chat_session(this);
	reading_chat evt_reading_chat = new reading_chat(this);
	delete_chat_session evt_delete_chat_session = new delete_chat_session(this);
	unmute_chat evt_unmute_chat = new unmute_chat(this);
	select_chat evt_select_chat = new select_chat(this);
	mute_chat evt_mute_chat = new mute_chat(this);
	chatting evt_chatting = new chatting(this);
	remove_content evt_remove_content = new remove_content(this);


	/******Set definitions******/
	//@ public static constraint CONTENT.equals(\old(CONTENT)); 
	public static final BSet<Integer> CONTENT = new Enumerated(min_integer,max_integer);

	//@ public static constraint USER.equals(\old(USER)); 
	public static final BSet<Integer> USER = new Enumerated(min_integer,max_integer);


	/******Constant definitions******/


	/******Axiom definitions******/


	/******Variable definitions******/
	/*@ spec_public */ private BRelation<Integer,Integer> active;

	/*@ spec_public */ private BRelation<Integer,Integer> c_seq;

	/*@ spec_public */ private Integer c_size;

	/*@ spec_public */ private BRelation<Integer,Integer> chat;

	/*@ spec_public */ private BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent;

	/*@ spec_public */ private BSet<Integer> content;

	/*@ spec_public */ private BRelation<Integer,Integer> inactive;

	/*@ spec_public */ private BRelation<Integer,Integer> muted;

	/*@ spec_public */ private BRelation<Integer,Integer> owner;

	/*@ spec_public */ private BRelation<Integer,Integer> toread;

	/*@ spec_public */ private BSet<Integer> user;


	/******Invariant definition******/
	/*@ public invariant
		user.isSubset(USER) &&
		content.isSubset(CONTENT) &&
		 chat.domain().isSubset(user) && chat.range().isSubset(user) && BRelation.cross(user,user).has(chat) &&
		 active.domain().isSubset(user) && active.range().isSubset(user) && active.isaFunction() && BRelation.cross(user,user).has(active) &&
		 muted.domain().isSubset(user) && muted.range().isSubset(user) && BRelation.cross(user,user).has(muted) &&
		active.isSubset(chat) &&
		muted.isSubset(chat) &&
		 chatcontent.domain().equals(user) && chatcontent.range().isSubset(BRelation.cross(content,((BRelation.cross(user,user)).pow()))) && chatcontent.isaFunction() && BRelation.cross(user,BRelation.cross(content,((BRelation.cross(user,user)).pow()))).has(chatcontent) &&
		 owner.domain().equals(content) && owner.range().isSubset(user) && owner.isaFunction() && BRelation.cross(content,user).has(owner) &&
		(active.intersection(muted)).equals(BSet.EMPTY) &&
		 (\forall Integer u1;  (\forall Integer u2;((user.has(u1) && user.has(u2) && chatcontent.image(new BSet<Integer>(u1,u2)).range().has(new Pair<Integer,Integer>(u1,u2))) ==> (chat.has(new Pair<Integer,Integer>(u1,u2)))))) &&
		 toread.domain().isSubset(user) && toread.range().isSubset(user) && BRelation.cross(user,user).has(toread) &&
		toread.isSubset(chat) &&
		inactive.isSubset(chat) &&
		(active.union(toread.union(inactive))).equals(chat) &&
		(active.intersection(toread)).equals(BSet.EMPTY) &&
		(active.intersection(inactive)).equals(BSet.EMPTY) &&
		owner.domain().equals(content) &&
		 (\forall Integer cc;  (\forall Integer uu1;((content.has(cc) && user.has(uu1) && owner.has(new Pair<Integer,Integer>(cc,uu1))) ==> (new BSet<Integer>(owner.apply(cc)).difference(new BSet<Integer>(uu1)).equals(BSet.EMPTY))))) &&
		(c_size).compareTo(new Integer(0)) >= 0 &&
		 c_seq.domain().equals(new Enumerated(new Integer(1),c_size)) && c_seq.range().equals(content) && c_seq.isaFunction() && c_seq.inverse().isaFunction() && BRelation.cross(new Enumerated(new Integer(1),c_size),content).has(c_seq) &&
		content.isSubset(new Best<Integer>(new JMLObjectSet {Integer i | (\exists Integer e; (new Enumerated(new Integer(1),c_size).has(i) && c_seq.domain().has(i)); e.equals(c_seq.apply(i)))})); */


	/******Getter and Mutator methods definition******/
	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.owner;*/
	public /*@ pure */ BRelation<Integer,Integer> get_owner(){
		return this.owner;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.owner;
	    ensures this.owner == owner;*/
	public void set_owner(BRelation<Integer,Integer> owner){
		this.owner = owner;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.toread;*/
	public /*@ pure */ BRelation<Integer,Integer> get_toread(){
		return this.toread;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.toread;
	    ensures this.toread == toread;*/
	public void set_toread(BRelation<Integer,Integer> toread){
		this.toread = toread;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.c_seq;*/
	public /*@ pure */ BRelation<Integer,Integer> get_c_seq(){
		return this.c_seq;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.c_seq;
	    ensures this.c_seq == c_seq;*/
	public void set_c_seq(BRelation<Integer,Integer> c_seq){
		this.c_seq = c_seq;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.inactive;*/
	public /*@ pure */ BRelation<Integer,Integer> get_inactive(){
		return this.inactive;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.inactive;
	    ensures this.inactive == inactive;*/
	public void set_inactive(BRelation<Integer,Integer> inactive){
		this.inactive = inactive;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.chatcontent;*/
	public /*@ pure */ BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> get_chatcontent(){
		return this.chatcontent;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.chatcontent;
	    ensures this.chatcontent == chatcontent;*/
	public void set_chatcontent(BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent){
		this.chatcontent = chatcontent;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.chat;*/
	public /*@ pure */ BRelation<Integer,Integer> get_chat(){
		return this.chat;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.chat;
	    ensures this.chat == chat;*/
	public void set_chat(BRelation<Integer,Integer> chat){
		this.chat = chat;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.active;*/
	public /*@ pure */ BRelation<Integer,Integer> get_active(){
		return this.active;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.active;
	    ensures this.active == active;*/
	public void set_active(BRelation<Integer,Integer> active){
		this.active = active;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.c_size;*/
	public /*@ pure */ Integer get_c_size(){
		return this.c_size;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.c_size;
	    ensures this.c_size == c_size;*/
	public void set_c_size(Integer c_size){
		this.c_size = c_size;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.muted;*/
	public /*@ pure */ BRelation<Integer,Integer> get_muted(){
		return this.muted;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.muted;
	    ensures this.muted == muted;*/
	public void set_muted(BRelation<Integer,Integer> muted){
		this.muted = muted;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.user;*/
	public /*@ pure */ BSet<Integer> get_user(){
		return this.user;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.user;
	    ensures this.user == user;*/
	public void set_user(BSet<Integer> user){
		this.user = user;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.content;*/
	public /*@ pure */ BSet<Integer> get_content(){
		return this.content;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.content;
	    ensures this.content == content;*/
	public void set_content(BSet<Integer> content){
		this.content = content;
	}



	/*@ public normal_behavior
	    requires true;
	    assignable \everything;
	    ensures
		user.isEmpty() &&
		content.isEmpty() &&
		chat.isEmpty() &&
		active.isEmpty() &&
		chatcontent.isEmpty() &&
		muted.isEmpty() &&
		owner.isEmpty() &&
		toread.isEmpty() &&
		inactive.isEmpty() &&
		c_size == 0 &&
		c_seq.isEmpty();*/
	public machine3(){
		user = new BSet<>();
		content = new BSet<>();
		chat = new BRelation<>();
		active = new BRelation<>();
		chatcontent = new BRelation<>();
		muted = new BRelation<>();
		owner = new BRelation<>();
		toread = new BRelation<>();
		inactive = new BRelation<>();
		c_size = 0;
		c_seq = new BRelation<>();

	}
}