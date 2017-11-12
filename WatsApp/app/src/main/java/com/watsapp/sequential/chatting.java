package com.watsapp.sequential;

import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;
import com.watsapp.prelude.Pair;

public class chatting{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public chatting(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<Integer,Integer>(u1,u2)) && machine.get_active().has(new Pair<Integer,Integer>(u1,u2)) && machine.CONTENT.difference(machine.get_content()).has(c) && !machine.get_muted().has(new Pair<Integer,Integer>(u2,u1)) && !machine.get_muted().has(new Pair<Integer,Integer>(u1,u2))); */
	public /*@ pure */ boolean guard_chatting( Integer c, Integer u1, Integer u2) {
		return (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<>(u1, u2)) && machine.get_active().has(new Pair<>(u1, u2)) && machine.CONTENT.difference(machine.get_content()).has(c) && !machine.get_muted().has(new Pair<>(u2, u1)) && !machine.get_muted().has(new Pair<>(u1, u2)));
	}

	/*@ public normal_behavior
		requires guard_chatting(c,u1,u2);
		assignable machine.content, machine.owner, machine.chat, machine.chatcontent, machine.toread, machine.inactive, machine.c_size, machine.c_seq;
		ensures guard_chatting(c,u1,u2) &&  machine.get_content().equals(\old((machine.get_content().union(new BSet<Integer>(c))))) &&  machine.get_owner().equals(\old((machine.get_owner().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(c,u1)))))) &&  machine.get_chat().equals(\old((machine.get_chat().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u2,u1)))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().domainSubtraction(new BSet<Integer>(u1)).union(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u1,(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(c,new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u2,u1),new Pair<Integer,Integer>(u1,u2)))).union(machine.get_chatcontent().apply(u1))))))))) &&  machine.get_toread().equals(\old((machine.get_toread().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u2,u1)).difference(machine.get_active()))))) &&  machine.get_inactive().equals(\old(machine.get_inactive().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u2,u1))))) &&  machine.get_c_size() == \old(new Integer(machine.get_c_size() + 1)) &&  machine.get_c_seq().equals(\old((machine.get_c_seq().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(new Integer(machine.get_c_size() + 1),c)))))); 
	 also
		requires !guard_chatting(c,u1,u2);
		assignable \nothing;
		ensures true; */
	public void run_chatting( Integer c, Integer u1, Integer u2){
		if(guard_chatting(c,u1,u2)) {
			BSet<Integer> content_tmp = machine.get_content();
			BRelation<Integer,Integer> owner_tmp = machine.get_owner();
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();
			Integer c_size_tmp = machine.get_c_size();
			BRelation<Integer,Integer> c_seq_tmp = machine.get_c_seq();

			machine.set_content((content_tmp.union(new BSet<>(c))));
			machine.set_owner((owner_tmp.union(new BRelation<>(new Pair<>(c, u1)))));
			machine.set_chat((chat_tmp.union(new BRelation<>(new Pair<>(u2, u1)))));
			machine.set_chatcontent((chatcontent_tmp.domainSubtraction(new BSet<>(u1)).union(new BRelation<>(new Pair<>(u1, (new BRelation<>(new Pair<>(c, new BRelation<>(new Pair<>(u2, u1), new Pair<>(u1, u2)))).union(chatcontent_tmp.apply(u1))))))));
			machine.set_toread((toread_tmp.union(new BRelation<>(new Pair<>(u2, u1)).difference(machine.get_active()))));
			machine.set_inactive(inactive_tmp.difference(new BRelation<>(new Pair<>(u2, u1))));
			machine.set_c_size(c_size_tmp + 1);
			machine.set_c_seq((c_seq_tmp.union(new BRelation<>(new Pair<>(c_size_tmp + 1, c)))));

			System.out.println("chatting executed c: " + c + " u1: " + u1 + " u2: " + u2 + " ");
		}
	}

}
