package player;


import com.google.common.base.Predicate;

/**
 * 
 * @author Alberto
 *
 */
public class PredicateDeclinePlayers implements Predicate<Player>{
		
	public boolean apply(Player player) {
		
		boolean condition1=((player.getPositions().contains(PositionTypes.GK) && player.getAge() >= PredicatePotentialPlayers.GOALKEEPER_DECLINE_AGE) || (!player.getPositions().contains(PositionTypes.GK) && player.getAge()>= PredicatePotentialPlayers.OUTFIELD_DECLINE_AGE));

		return condition1;
	}
}


