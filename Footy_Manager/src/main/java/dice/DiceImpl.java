package dice;

import java.util.Random;
/**
 * @author Zouhair
 * @class DiceImpl
 * @brief Simula la tirada de un dado
 * 
 */

public class DiceImpl{
	
	public static int rollDice(){
		Random rand = new Random();
		int x = rand.nextInt(6);
		return x+1;
	}
	
	public static int rollDice(Integer i){
		Random rand = new Random();
		int x = rand.nextInt(i);
		return x+1;
	}
	public static int roll2Dice(){
		int accumulator=rollDice();
		accumulator=accumulator+rollDice();
		return accumulator;
		/**
		 * @return devuelve el valor del accumulator.
		 * @author Zouhair
		 */
	}

}
