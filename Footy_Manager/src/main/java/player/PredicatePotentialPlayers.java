package player;

import com.google.common.base.Predicate;

/**
 * 
 * @author Rafael
 * @biref Potencial del Jugador, lo minimo y maximo que puede llegar.
 */
public class PredicatePotentialPlayers implements Predicate<Player>{
	
	private static final Integer MIN_ABILITY=6;
	public static final Integer GOALKEEPER_DECLINE_AGE=34;
	public static final Integer OUTFIELD_DECLINE_AGE= 31;
	
	public boolean apply(Player player) {

		boolean condition1=((player.getPositions().contains(PositionTypes.GK) && player.getAge() < GOALKEEPER_DECLINE_AGE) || (!player.getPositions().contains(PositionTypes.GK) && player.getAge()< OUTFIELD_DECLINE_AGE));

		boolean condition2= player.getAbility()>=MIN_ABILITY;	
		
		boolean condition3= player.getPotential();
			
		return (condition1 && condition2 && condition3);
	}	
}