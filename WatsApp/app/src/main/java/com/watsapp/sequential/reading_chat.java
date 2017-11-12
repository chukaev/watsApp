package com.watsapp.sequential;

import com.watsapp.prelude.BRelation;
import com.watsapp.prelude.BSet;
import com.watsapp.prelude.Pair;

public class reading_chat{
	/*@ spec_public */ private machine3 machine; // reference to the machine 

	/*@ public normal_behavior
		requires true;
		assignable \everything;
		ensures this.machine == m; */
	public reading_chat(machine3 m) {
		this.machine = m;
	}

	/*@ public normal_behavior
		requires true;
 		assignable \nothing;
		ensures \result <==> (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<Integer,Integer>(u1,u2)) && BSet.EMPTY.has((machine.get_active().intersection(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))) && BSet.EMPTY.has((machine.get_muted().intersection(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2)))))); */
	public /*@ pure */ boolean guard_reading_chat( Integer u1, Integer u2) {
		return (machine.get_user().has(u1) && machine.get_user().has(u2) && machine.get_chat().has(new Pair<>(u1, u2)) && BSet.EMPTY.has((machine.get_active().intersection(new BRelation<>(new Pair<>(u1, u2))))) && BSet.EMPTY.has((machine.get_muted().intersection(new BRelation<>(new Pair<>(u1, u2))))));
	}

	/*@ public normal_behavior
		requires guard_reading_chat(u1,u2);
		assignable machine.toread, machine.active;
		ensures guard_reading_chat(u1,u2) &&  machine.get_toread().equals(\old(machine.get_toread().difference(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2))))) &&  machine.get_active().equals(\old((machine.get_active().union(new BRelation<Integer,Integer>(new Pair<Integer,Integer>(u1,u2)))))); 
	 also
		requires !guard_reading_chat(u1,u2);
		assignable \nothing;
		ensures true; */
	public void run_reading_chat( Integer u1, Integer u2){
		if(guard_reading_chat(u1,u2)) {
			BRelation<Integer,Integer> toread_tmp = machine.get_toread();
			BRelation<Integer,Integer> active_tmp = machine.get_active();

			machine.set_toread(toread_tmp.difference(new BRelation<>(new Pair<>(u1, u2))));
			machine.set_active((active_tmp.union(new BRelation<>(new Pair<>(u1, u2)))));

			System.out.println("reading_chat executed u1: " + u1 + " u2: " + u2 + " ");
		}
	}

}
