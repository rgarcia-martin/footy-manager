package player;

import java.util.Set;
import java.util.SortedSet;

import com.google.common.base.Predicate;
/**
 * 
 * @author Rafael
 *
 */
public class PredicateRightNonPlayerTeamFile implements Predicate<String>{

	private SortedSet<Integer> nonPlayerTeamRankings;
	private Set<String> nonPlayerTeamNames;
	
	private static final Integer NONPLAYERTEAM_FIELDS=11;
	private static final Integer RANKING_POSITION=0;
	private static final Integer NAME_POSITION=1;

	public PredicateRightNonPlayerTeamFile(SortedSet<Integer> nonPlayerRank, Set<String> nonPlayerName) {
	
		nonPlayerTeamRankings= nonPlayerRank;
		nonPlayerTeamNames= nonPlayerName;
	}

	public boolean apply(String text) {

		boolean rightText=false;
		String[] fields= text.split(";");
		
		if (fields.length==NONPLAYERTEAM_FIELDS){
					
			Integer ranking= new Integer(fields[RANKING_POSITION]);
			String name= fields[NAME_POSITION];
			
			if(nonPlayerTeamRankings.add(ranking) && nonPlayerTeamNames.add(name) && !name.isEmpty())
					rightText=true;
		}	

		return rightText;
	}	
}