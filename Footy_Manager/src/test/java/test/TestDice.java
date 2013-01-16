package test;
import static org.junit.Assert.*;

import org.junit.Test;

import dice.DiceImpl;
public class TestDice {

	
	@Test
	public void testRollDice(){
		
		Integer roll= DiceImpl.rollDice();
		assertTrue(roll<7);
	}

	@Test
	public void testRollDiceParameter(){
		
		Integer roll= DiceImpl.rollDice(3);
		assertTrue(roll<4);
	}

	@Test
	public void testRoll2Dice(){
		
		Integer roll= DiceImpl.roll2Dice();
		assertTrue(roll<13);
	}


}
