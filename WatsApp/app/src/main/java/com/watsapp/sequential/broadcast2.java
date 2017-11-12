package com.watsapp.sequential;

import com.watsapp.prelude.*;

public class broadcast2{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public broadcast2(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u1) && ul.isSubset(machine.get_user()) && (BRelation.cross(new BSet<Integer>(u1),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && (BRelation.cross(ul,new BSet<Integer>(u1)).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.CONTENT.has(c) && !machine.get_chatcontent().apply(u1).domain().has(c)); */
	public /*@ pure */ boolean guard_broadcast2( Integer c, Integer u1, BSet<Integer> ul) {
		return (machine.get_user().has(u1) && ul.isSubset(machine.get_user()) && (BRelation.cross(new BSet<>(u1),ul).intersection(machine.get_muted())).equals(BSet.EMPTY) && (BRelation.cross(ul, new BSet<>(u1)).intersection(machine.get_muted())).equals(BSet.EMPTY) && machine.CONTENT.has(c) && !machine.get_chatcontent().apply(u1).domain().has(c));
	}

	/*@ public normal_behavior
		requires guard_broadcast2(c,u1,ul);
		assignable machine.chat, machine.content, machine.chatcontent, machine.owner, machine.toread, machine.inactive, machine.c_size, machine.c_seq;
		ensures guard_broadcast2(c,u1,ul) &&  machine.get_chat().equals(\old((machine.get_chat().union(BRelation.cross(ul,new BSet<Integer>(u1)).union(BRelation.cross(new BSet<Integer>(u1),ul)))))) &&  machine.get_content().equals(\old((machine.get_content().union(new BSet<Integer>(c))))) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().override(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u1,(machine.get_chatcontent().apply(u1).override(new BRelation<Integer,BSet<Integer>>(new Pair<Integer,BSet<Integer>>(c,new Best<Pair>(new JMLObjectSet {Pair<Integer,Integer> uu | (\exists Pair e; ((BRelation.cross(ul,new BSet<Integer>(u1)).union(BRelation.cross(new BSet<Integer>(u1),ul))).has(uu)); e.equals(uu))}))))))))))) &&  machine.get_owner().equals(\old((new BRelation<Integer,Integer>(new Pair<Integer,Integer>(c,u1)).override(machine.get_owner())))) &&  machine.get_toread().equals(\old((machine.get_toread().union(BRelation.cross(ul,new BSet<Integer>(u1)).difference(machine.get_active()))))) &&  machine.get_inactive().equals(\old((machine.get_inactive().difference(BRelation.cross(ul,new BSet<Integer>(u1))).union(BRelation.cross(new BSet<Integer>(u1),ul).difference(machine.get_active()))))) &&  machine.get_c_size() == \old(new Integer(machine.get_c_size() + 1)) &&  machine.get_c_seq().equals(\old((machine.get_c_seq().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(new Integer(machine.get_c_size() + 1),c)).rangeSubtraction(machine.get_c_seq().range()))))); 
	 also
		requires !guard_broadcast2(c,u1,ul);
		assignable \nothing;
		ensures true; */
	public void run_broadcast2( Integer c, Integer u1, BSet<Integer> ul){
		if(guard_broadcast2(c,u1,ul)) {
			BRelation<Integer,Integer> chat_tmp = machine.get_chat();
			BSet<Integer> content_tmp = machine.get_content();
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BRelation<Integer,Integer> owner_tmp = machine.get_owner();
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> inactive_tmp = machine.get_inactive();
			Integer c_size_tmp = machine.get_c_size();
			BRelation<Integer,Integer> c_seq_tmp = machine.get_c_seq();

			machine.set_chat((chat_tmp.union(BRelation.cross(ul, new BSet<>(u1)).union(BRelation.cross(new BSet<>(u1),ul)))));
			machine.set_content((content_tmp.union(new BSet<>(c))));
			machine.set_chatcontent(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_owner((new BRelation<>(new Pair<>(c, u1)).override(owner_tmp)));
			machine.set_toread((toread_tmp.union(BRelation.cross(ul, new BSet<>(u1)).difference(machine.get_active()))));
			machine.set_inactive((inactive_tmp.difference(BRelation.cross(ul, new BSet<>(u1))).union(BRelation.cross(new BSet<>(u1),ul).difference(machine.get_active()))));
			machine.set_c_size(c_size_tmp + 1);
			machine.set_c_seq((c_seq_tmp.union(new BRelation<>(new Pair<>(c_size_tmp + 1, c)).rangeSubtraction(c_seq_tmp.range()))));

			System.out.println("broadcast2 executed c: " + c + " u1: " + u1 + " ul: " + ul + " ");
		}
	}

}
