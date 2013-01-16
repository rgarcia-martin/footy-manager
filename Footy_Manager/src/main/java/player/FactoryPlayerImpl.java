package player;

import java.util.Iterator;

import dice.DiceImpl;
import readers.TextToIterableImpl;
import team.PlayerControlledTeam;

public class FactoryPlayerImpl implements FactoryPlayer {

	private static final String dir_fich = "src/main/resources/PlayerNames";	
	
	private static final Iterable<String> itNames = new TextToIterableImpl(dir_fich);
	private static Iterator<String> it = itNames.iterator();
		
	@Override
	public Player getNewPlayer(PlayerControlledTeam t) {
		Player res;
		Integer age = 16, number = 1;
		Double ability = 5.0;
		
		FormTypes form = null;
		PositionTypes position = null;

		Integer rollDice = DiceImpl.rollDice();
		
				if(rollDice <= 2)					age += 1; /**< Como minimo el jugador tendra 17 años  */
		else	if(rollDice > 2 && rollDice <= 4)	age += 2;
		else	if(rollDice > 4 && rollDice <= 6)	age += 3;

		rollDice = DiceImpl.rollDice();
		
				if(rollDice <= 2)					ability += 0.0;
		else	if(rollDice > 2 && rollDice <= 4)	ability += 0.5;
		else	if(rollDice > 4 && rollDice <= 6)	ability += 1.0;

		rollDice = DiceImpl.rollDice();
		
				if(rollDice <= 3)					form = FormTypes.MINUS;
		else	if(rollDice > 3 && rollDice <= 6)	form = FormTypes.PLUS;
		
		String name = getNewLine();
				
		res = new PlayerImpl(name, age, ability, form, position, number, t);
		
		return res;
	}

	@Override
	public Player getNewYoungPlayer(PlayerControlledTeam t) {
		String name="Jugador Canterano";
		
		Player res;
		Integer age = 16, number = 999;
		Double ability = 5.0;
		
		FormTypes form = null;
		PositionTypes position = null;

		Integer rollDice = DiceImpl.rollDice();
		
				if(rollDice <= 2)					ability += 0.0;
		else	if(rollDice > 2 && rollDice <= 4)	ability += 0.5;
		else	if(rollDice > 4 && rollDice <= 6)	ability += 1.0;

		rollDice = DiceImpl.rollDice();
		
				if(rollDice <= 3)					form = FormTypes.MINUS;
		else	if(rollDice > 3 && rollDice <= 6)	form = FormTypes.PLUS;
				
		res = new PlayerImpl(name, age, ability, form, position, number, t);
		
		return res;
	}

	private String getNewLine(){
		String res = null;
		
		if(it.hasNext())
		{
			res = it.next();
		}
		else
		{
			it = itNames.iterator();
			res = getNewLine();
		}
		
		return res;
	}
}
