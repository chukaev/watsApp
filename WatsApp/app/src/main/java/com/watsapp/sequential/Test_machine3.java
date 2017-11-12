package com.watsapp.sequential;
import java.util.Random;
import com.watsapp.util.Utilities;
import com.watsapp.prelude.*;

public class Test_machine3{


	static Random rnd = new Random();

	public static void main(String[] args){

		/** User defined code that reflects axioms and theorems: Begin **/
		/** User defined code that reflects axioms and theorems: End **/

		machine3 machine = new machine3();
		int n = 14; //the number of events in the machine

		Integer u1 = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));
		Integer u2 = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));

		Integer c = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));
		Integer u = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));
		BSet<Integer> ul = Utilities.someSet(new BSet<>((new Enumerated(1, Utilities.max_integer))));


		while (true){
			switch (rnd.nextInt(n)){
			case 0: if (machine.evt_create_chat_session.guard_create_chat_session(u1,u2))
				machine.evt_create_chat_session.run_create_chat_session(u1,u2); break;
			case 1: if (machine.evt_select_chat.guard_select_chat(u1,u2))
				machine.evt_select_chat.run_select_chat(u1,u2); break;
			case 2: if (machine.evt_chatting.guard_chatting(c,u1,u2))
				machine.evt_chatting.run_chatting(c,u1,u2); break;
			case 3: if (machine.evt_delete_content.guard_delete_content(c,u1,u2))
				machine.evt_delete_content.run_delete_content(c,u1,u2); break;
			case 4: if (machine.evt_remove_content.guard_remove_content(c,u1,u2))
				machine.evt_remove_content.run_remove_content(c,u1,u2); break;
			case 5: if (machine.evt_delete_chat_session.guard_delete_chat_session(u1,u2))
				machine.evt_delete_chat_session.run_delete_chat_session(u1,u2); break;
			case 6: if (machine.evt_mute_chat.guard_mute_chat(u1,u2))
				machine.evt_mute_chat.run_mute_chat(u1,u2); break;
			case 7: if (machine.evt_unmute_chat.guard_unmute_chat(u1,u2))
				machine.evt_unmute_chat.run_unmute_chat(u1,u2); break;
			case 8: if (machine.evt_broadcast1.guard_broadcast1(c,u1,ul))
				machine.evt_broadcast1.run_broadcast1(c,u1,ul); break;
			case 9: if (machine.evt_forward1.guard_forward1(c,u,ul))
				machine.evt_forward1.run_forward1(c,u,ul); break;
			case 10: if (machine.evt_broadcast2.guard_broadcast2(c,u1,ul))
				machine.evt_broadcast2.run_broadcast2(c,u1,ul); break;
			case 11: if (machine.evt_forward2.guard_forward2(c,u,ul))
				machine.evt_forward2.run_forward2(c,u,ul); break;
			case 12: if (machine.evt_unselect_chat.guard_unselect_chat(u1,u2))
				machine.evt_unselect_chat.run_unselect_chat(u1,u2); break;
			case 13: if (machine.evt_reading_chat.guard_reading_chat(u1,u2))
				machine.evt_reading_chat.run_reading_chat(u1,u2); break;
			}
			u1 = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));
			u2 = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));
			c = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));
			u = Utilities.someVal(new BSet<>((new Enumerated(1, Utilities.max_integer))));
			ul = Utilities.someSet(new BSet<>((new Enumerated(1, Utilities.max_integer))));
		}
	}

}
