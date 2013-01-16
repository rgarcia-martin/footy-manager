package division;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixturesImpl implements Fixtures {
	
	
	
	private final Map<DivisionType, MatchesInSeason> matches = new HashMap<DivisionType, MatchesInSeason>();

	public FixturesImpl(List<Division> divisions){
		for(Division d: divisions)
		{			
			matches.put(d.getCategory(), new MatchesInSeasonImpl(d));
		}
	}
	
	@Override
	public MatchesInSeason getMatchesSeasonForDivision(DivisionType d) {
		return matches.get(d);
	}
}
