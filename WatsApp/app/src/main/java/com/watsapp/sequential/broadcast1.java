package com.watsapp.sequential;

import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;
import com.watsapp.prelude.Pair;

public class broadcast1{
	/*@ spec_public */
	private machine3 machine; // reference to the machine

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public broadcast1(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u1) && ul.isSubset(machine.get_user()) && (BRelation.cross(new BSet<Integer>(u1),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && (BRelation.cross(ul,new BSet<Integer>(u1)).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.get_content().has(c) && machine.get_chatcontent().apply(u1).domain().has(c)); */
	public /*@ pure */ boolean guard_broadcast1( Integer c, Integer u1, BSet<Integer> ul) {
		return (machine.get_user().has(u1) && ul.isSubset(machine.get_user()) && (BRelation.cross(new BSet<>(u1),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && (BRelation.cross(ul, new BSet<>(u1)).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.get_content().has(c) && machine.get_chatcontent().apply(u1).domain().has(c));
	}

	/*@ public normal_behavior
		requires guard_broadcast1(c,u1,ul);
		assignable machine.chat, machine.chatcontent, machine.toread, machine.inactive;
		ensures guard_broadcast1(c,u1,ul) &&  machine.get_chat().equals(\old((machine.get_chat().union(BRelation.cross(ul,new BSet<Integer>(u1)).union(BRelation.cross(new BSet<Integer>(u1),ul)))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u1,(machine.get_chatcontent().apply(u1).override(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(c,(machine.get_chatcontent().apply(u1).apply(c).union((BRelation.cross(ul,new BSet<Integer>(u1)).union(BRelation.cross(new BSet<Integer>(u1),ul))))))))))))))) &&  machine.get_toread().equals(\old((machine.get_toread().union(BRelation.cross(ul,new BSet<Integer>(u1)).difference(machine.get_active()))))) &&  machine.get_inactive().equals(\old((machine.get_inactive().difference(BRelation.cross(ul,new BSet<Integer>(u1))).union(BRelation.cross(new BSet<Integer>(u1),ul).difference(machine.get_active()))))); 
	 also
		requires !guard_broadcast1(c,u1,ul);
		assignable \nothing;
		ensures true; */
	public void run_broadcast1( Integer c, Integer u1, BSet<Integer> ul){
		if(guard_broadcast1(c,u1,ul)) {
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();

			machine.set_chat((chat_tmp.union(BRelation.cross(ul, new BSet<>(u1)).union(BRelation.cross(new BSet<>(u1),ul)))));
			machine.set_chatcontent((chatcontent_tmp.override(new BRelation<>(new Pair<>(u1, (chatcontent_tmp.apply(u1).override(new BRelation<>(new Pair<>(c, (chatcontent_tmp.apply(u1).apply(c).union((BRelation.cross(ul, new BSet<>(u1)).union(BRelation.cross(new BSet<>(u1), ul))))))))))))));
			machine.set_toread((toread_tmp.union(BRelation.cross(ul, new BSet<>(u1)).difference(machine.get_active()))));
			machine.set_inactive((inactive_tmp.difference(BRelation.cross(ul, new BSet<>(u1))).union(BRelation.cross(new BSet<>(u1),ul).difference(machine.get_active()))));

			System.out.println("broadcast1 executed c: " + c + " u1: " + u1 + " ul: " + ul + " ");
		}
	}

}
