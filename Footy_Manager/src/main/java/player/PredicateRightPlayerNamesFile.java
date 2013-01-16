package player;

import com.google.common.base.Predicate;
/**
 * 
 * @author Alberto
 *
 */
public class PredicateRightPlayerNamesFile implements Predicate<String>{
	
	public boolean apply(String text) {

		boolean rightText=false;
					
		if(!text.isEmpty() && text.length()<25)
			rightText=true;
			
		return rightText;
	}
}