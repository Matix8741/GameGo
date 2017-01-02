package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Messege.MessageSurrender;
import Messege.MessegeFirst;
import Messege.MessegeMove;

public class MessageTest {

	@Test
	public void surr() {
		MessageSurrender ms = new MessageSurrender();
		assertEquals("FF", ms.createMessega());
	}
	
	@Test 
	public void first(){
		MessegeFirst ms = new MessegeFirst();
		ms.setPlayerType("Player");
		ms.setSize("12");
		assertEquals("SS12PLPlayer", ms.createMessega());
		
	}

	@Test
	public void move(){
		MessegeMove ms = new MessegeMove();
		ms.setMove("123");
		assertEquals("MV123", ms.createMessega());
	}
}
