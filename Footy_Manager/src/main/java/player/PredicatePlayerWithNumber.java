package player;


import com.google.common.base.Predicate;
/**
 * 
 * @author Rafael
 *
 */
public class PredicatePlayerWithNumber implements Predicate<Player>{

	private final Integer number;
	
	public PredicatePlayerWithNumber(Integer num){
		
		number = num;
	}
	
	public boolean apply(Player p) {

		return p.getNumber().equals(this.number);
	}
}

