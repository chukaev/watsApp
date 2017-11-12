package com.watsapp.sequential;

import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;
import com.watsapp.prelude.Pair;

public class delete_content{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public delete_content(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<Integer,Integer>(u1,u2)) && machine.get_content().has(c) && machine.get_chatcontent().apply(u2).domain().has(c) && machine.get_active().has(new Pair<Integer,Integer>(u1,u2))); */
	public /*@ pure */ boolean guard_delete_content( Integer c, Integer u1, Integer u2) {
		return (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<>(u1, u2)) && machine.get_content().has(c) && machine.get_chatcontent().apply(u2).domain().has(c) && machine.get_active().has(new Pair<>(u1, u2)));
	}

	/*@ public normal_behavior
		requires guard_delete_content(c,u1,u2);
		assignable machine.chatcontent, machine.content, machine.owner, machine.c_seq;
		ensures guard_delete_content(c,u1,u2) &&  machine.get_chatcontent().equals(\old((machine.get_chatcontent().domainSubtraction(new BSet<Integer>(u2)).union(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u2,(machine.get_chatcontent().apply(u2).domainSubtraction(new BSet<Integer>(c)).union(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(c,machine.get_chatcontent().apply(u2).apply(c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))))))))))) &&  machine.get_content().equals(\old(machine.get_content().difference(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (cc.equals(c) && BRelation.cross(machine.get_user(),BRelation.cross(machine.get_content().difference(new BSet<ERROR>(cc)),(BRelation.cross(machine.get_user(),machine.get_user())).pow())).has((machine.get_chatcontent().domainSubtraction(new BSet<Integer>(u2)).union(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u2,(machine.get_chatcontent().apply(u2).domainSubtraction(new BSet<Integer>(c)).union(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(c,machine.get_chatcontent().apply(u2).apply(c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))))))))))); e.equals(cc))})))) &&  machine.get_owner().equals(\old(machine.get_owner().domainSubtraction(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (cc.equals(c) && BRelation.cross(machine.get_user(),BRelation.cross(machine.get_content().difference(new BSet<ERROR>(cc)),(BRelation.cross(machine.get_user(),machine.get_user())).pow())).has((machine.get_chatcontent().domainSubtraction(new BSet<Integer>(u2)).union(new BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(new Pair<Integer,BRelation<Integer,BRelation<Integer,Integer>>>(u2,(machine.get_chatcontent().apply(u2).domainSubtraction(new BSet<Integer>(c)).union(new BRelation<Integer,BRelation<Integer,Integer>>(new Pair<Integer,BRelation<Integer,Integer>>(c,machine.get_chatcontent().apply(u2).apply(c).difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))))))))))); e.equals(cc))})))) &&  machine.get_c_seq().equals(\old(machine.get_c_seq().domainSubtraction(new Best<Integer>(new JMLObjectSet {Integer cc | (\exists Integer e; (cc.equals(c) && BRelation.cross(machine.get_user(),BRelation.cross(machine.get_content().difference(new BSet<ERROR>(cc)),(BRelation.cross(machine.get_user(),machine.get_user())).pow())).has(machine.get_chatcontent())); e.equals(machine.get_c_seq().inverse().apply(cc)))})))); 
	 also
		requires !guard_delete_content(c,u1,u2);
		assignable \nothing;
		ensures true; */
	public void run_delete_content( Integer c, Integer u1, Integer u2){
		if(guard_delete_content(c,u1,u2)) {
			BRelation<Integer,BRelation<Integer,BRelation<Integer,Integer>>> chatcontent_tmp = machine.get_chatcontent();
			BSet<Integer> content_tmp = machine.get_content();
			BRelation<Integer,Integer> owner_tmp = machine.get_owner();
			BRelation<Integer,Integer> c_seq_tmp = machine.get_c_seq();

			machine.set_chatcontent((chatcontent_tmp.domainSubtraction(new BSet<>(u2)).union(new BRelation<>(new Pair<>(u2, (chatcontent_tmp.apply(u2).domainSubtraction(new BSet<>(c)).union(new BRelation<>(new Pair<>(c, chatcontent_tmp.apply(u2).apply(c).difference(new BRelation<>(new Pair<>(u1, u2))))))))))));
			machine.set_content(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_owner(null); // Set Comprehension: feature not supported by EventB2Java
			machine.set_c_seq(null); // Set Comprehension: feature not supported by EventB2Java

			System.out.println("delete_content executed c: " + c + " u1: " + u1 + " u2: " + u2 + " ");
		}
	}

}
