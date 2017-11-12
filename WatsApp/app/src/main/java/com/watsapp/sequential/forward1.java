package com.watsapp.sequential;

import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;

public class forward1{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public forward1(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u) && ul.isProperSubset(machine.get_user()) && (BRelation.cross(new BSet<Integer>(u),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && (BRelation.cross(ul,new BSet<Integer>(u)).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.get_content().has(c) && !machine.get_chatcontent().apply(u).domain().has(c) && BRelation.cross(new BSet<Integer>(u),ul).isSubset(machine.get_chat())); */
	public /*@ pure */ boolean guard_forward1( Integer c, Integer u, BSet<Integer> ul) {
		return (machine.get_user().has(u) && ul.isProperSubset(machine.get_user()) && (BRelation.cross(new BSet<>(u),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && (BRelation.cross(ul, new BSet<>(u)).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.get_content().has(c) && !machine.get_chatcontent().apply(u).domain().has(c) && BRelation.cross(new BSet<>(u),ul).isSubset(machine.get_chat()));
	}

	/*@ public normal_behavior
		requires guard_forward1(c,u,ul);
		assignable machine.chat, machine.chatcontent, machine.toread, machine.inactive;
		ensures guard_forward1(c,u,ul) &&  machine.get_chat().equals(\old((machine.get_chat().union(BRelation.cross(ul,new BSet<Integer>(u)))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u,(machine.get_chatcontent().apply(u).override(new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(c,new Best<Pair>(new JMLObjectSet {Pair<Integer,Integer> uu | (\exists Pair e; ((BRelation.cross(ul,new BSet<Integer>(u)).union(BRelation.cross(new BSet<Integer>(u),ul))).has(uu)); e.equals(uu))}))))))))))) &&  machine.get_toread().equals(\old((machine.get_toread().union(BRelation.cross(ul,new BSet<Integer>(u)).difference(machine.get_active()))))) &&  machine.get_inactive().equals(\old(machine.get_inactive().difference(BRelation.cross(ul,new BSet<Integer>(u))))); 
	 also
		requires !guard_forward1(c,u,ul);
		assignable \nothing;
		ensures true; */
	public void run_forward1( Integer c, Integer u, BSet<Integer> ul){
		if(guard_forward1(c,u,ul)) {
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();

			machine.set_chat((chat_tmp.union(BRelation.cross(ul, new BSet<>(u)))));
			machine.set_chatcontent(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_toread((toread_tmp.union(BRelation.cross(ul, new BSet<>(u)).difference(machine.get_active()))));
			machine.set_inactive(inactive_tmp.difference(BRelation.cross(ul, new BSet<>(u))));

			System.out.println("forward1 executed c: " + c + " u: " + u + " ul: " + ul + " ");
		}
	}

}
