package sponsor;

import java.util.Set;

import com.google.common.base.Predicate;
/**
 *
 * @author Alberto
 *
 */
public class PredicateRightSponsorFile implements Predicate<String>{

	private Set<String> sponsorNames;
	
	private static final Integer SPONSOR_FIELDS=3;
	private static final Integer NAME_POSITION= 0;	
	private static final Integer ONE_SEASON_CASH_POSITION= 1;	
	private static final Integer TWO_SEASONS_CASH_POSITION= 2;	

	public PredicateRightSponsorFile(Set<String> sponsorNam) {
	
		sponsorNames= sponsorNam;
	}

	public boolean apply(String text) {

		boolean rightText=false;
		String[] fields= text.split(";");
		
		if (fields.length==SPONSOR_FIELDS){
					
			String name= fields[NAME_POSITION];
			Integer oneSeasonCash= new Integer(fields[ONE_SEASON_CASH_POSITION]);
			Integer twoSeasonsCash= new Integer(fields[TWO_SEASONS_CASH_POSITION]);
			
			if(sponsorNames.add(name) && twoSeasonsCash>oneSeasonCash && !name.isEmpty())
				rightText=true;
		}	

		return rightText;
	}
}
