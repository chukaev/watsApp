package com.watsapp.sequential;

import com.watsapp.prelude.*;

public class delete_chat_session{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public delete_chat_session(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<Integer,Integer>(u1,u2)) && machine.get_active().has(new Pair<Integer,Integer>(u1,u2))); */
	public /*@ pure */ boolean guard_delete_chat_session( Integer u1, Integer u2) {
		return (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<>(u1, u2)) && machine.get_active().has(new Pair<>(u1, u2)));
	}

	/*@ public normal_behavior
		requires guard_delete_chat_session(u1,u2);
		assignable machine.chat, machine.active, machine.muted, machine.chatcontent, machine.content, machine.owner, machine.toread, machine.inactive, machine.c_seq;
		ensures guard_delete_chat_session(u1,u2) &&  machine.get_chat().equals(\old(machine.get_chat().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))) &&  machine.get_active().equals(\old(machine.get_active().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))) &&  machine.get_muted().equals(\old(machine.get_muted().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))) &&  machine.get_chatcontent().equals(\old(((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u1,(machine.get_chatcontent().apply(u1).domainSubtraction(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().apply(u1).domain().has(cc) && machine.get_chatcontent().apply(u1).apply(cc).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))).equals(BRelation.EMPTY)); e.equals(cc))})).override(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().apply(u1).domain().has(cc) && machine.get_chatcontent().apply(u1).apply(cc).has(new Pair<Integer,Integer>(u1,u2)) && ); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(cc,machine.get_chatcontent().apply(u1).apply(cc).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))))}))))))).override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u2,(machine.get_chatcontent().apply(u2).domainSubtraction(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().apply(u2).domain().has(cc) && machine.get_chatcontent().apply(u2).apply(cc).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))).equals(BRelation.EMPTY)); e.equals(cc))})).override(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_chatcontent().apply(u2).domain().has(cc) && machine.get_chatcontent().apply(u2).apply(cc).has(new Pair<Integer,Integer>(u1,u2)) && ); e.equals(new Pair<BRelation<Integer,Integer>,ERROR>(cc,machine.get_chatcontent().apply(u2).apply(cc).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))))}))))))))) &&  machine.get_content().equals(\old(machine.get_content().difference(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_content().has(cc) && BRelation.cross(machine.get_user(),BRelation.cross(machine.get_content().difference(new BSet<ERROR>(cc)),(BRelation.cross(machine.get_user(),machine.get_user())).pow())).has(machine.get_chatcontent())); e.equals(cc))})))) &&  machine.get_owner().equals(\old(machine.get_owner().difference(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_content().has(cc) && BRelation.cross(machine.get_user(),BRelation.cross(machine.get_content().difference(new BSet<ERROR>(cc)),(BRelation.cross(machine.get_user(),machine.get_user())).pow())).has(machine.get_chatcontent())); e.equals(new Pair<Integer,ERROR>(cc,machine.get_owner().apply(cc))))})))) &&  machine.get_toread().equals(\old(machine.get_toread().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))) &&  machine.get_inactive().equals(\old(machine.get_inactive().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))) &&  machine.get_c_seq().equals(\old(machine.get_c_seq().domainSubtraction(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (machine.get_content().has(cc) && BRelation.cross(machine.get_user(),BRelation.cross(machine.get_content().difference(new BSet<ERROR>(cc)),(BRelation.cross(machine.get_user(),machine.get_user())).pow())).has(machine.get_chatcontent())); e.equals(machine.get_c_seq().inverse().apply(cc)))})))); 
	 also
		requires !guard_delete_chat_session(u1,u2);
		assignable \nothing;
		ensures true; */
	public void run_delete_chat_session( Integer u1, Integer u2){
		if(guard_delete_chat_session(u1,u2)) {
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,Integer> active_tmp = machine.get_active();
			BRelation<Integer,Integer> muted_tmp = machine.get_muted();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BSet<Integer> content_tmp = machine.get_content();
			BRelation<Integer,Integer> owner_tmp = machine.get_owner();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();
			BRelation<Integer,Integer> c_seq_tmp = machine.get_c_seq();

			machine.set_chat(chat_tmp.difference(new BRelation<>(new Pair<>(u1, u2))));
			machine.set_active(active_tmp.difference(new BRelation<>(new Pair<>(u1, u2))));
			machine.set_muted(muted_tmp.difference(new BRelation<>(new Pair<>(u1, u2))));
			//machine.set_chatcontent(null); // Set Comprehension: feature not supported by EventB2Java
			//machine.set_content(null); // Set Comprehension: feature not supported by EventB2Java
			//machine.set_owner(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_toread(toread_tmp.difference(new BRelation<>(new Pair<>(u1, u2))));
			machine.set_inactive(inactive_tmp.difference(new BRelation<>(new Pair<>(u1, u2))));
			//machine.set_c_seq(null); // Set Comprehension: feature not supported by EventB2Java

			System.out.println("delete_chat_session executed u1: " + u1 + " u2: " + u2 + " ");
		}
	}

}
