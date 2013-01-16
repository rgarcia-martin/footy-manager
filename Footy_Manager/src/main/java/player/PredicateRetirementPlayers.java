package player;

import com.google.common.base.Predicate;

/**
 * 
 * @author Rafael
 *
 */
public class PredicateRetirementPlayers implements Predicate<Player>{
	
	private static final Integer GOALKEEPER_RETIREMENT_AGE=35;
	private static final Integer OUTFIELD_RETIREMENT_AGE=33;
	
	public boolean apply(Player player) {

		boolean condition=false;
		
		if(player.getPositions().contains(PositionTypes.GK) && player.getAge()>=GOALKEEPER_RETIREMENT_AGE)
			condition=true;

		else if(!player.getPositions().contains(PositionTypes.GK) && player.getAge()>=OUTFIELD_RETIREMENT_AGE)
			condition=true;
		
		return condition;
	}
}