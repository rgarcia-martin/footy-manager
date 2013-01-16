package player;


import com.google.common.base.Predicate;

/**
 * @author  Rafael
 */
public class PredicatePlayerWithPosition implements Predicate<Player>{


	private final PositionTypes position;
	
	public PredicatePlayerWithPosition(PositionTypes pos)
	{
		position = pos;
	}
	
	@Override
	public boolean apply(Player p) {
		return p.getPositions().contains(position);
	}

}
