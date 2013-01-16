package division;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import team.Team;

public class MatchesInSeasonImpl implements MatchesInSeason{
	
	private static final Integer[][] POSITIONS= {{1,5,2,6,3,7,4,8},{2,1,4,3,6,5,8,7},
												{4,2,8,6,3,1,7,5},{6,3,5,4,2,7,1,8},
												{4,1,7,6,8,5,3,2},{6,4,2,8,1,7,5,3},
												{5,2,4,7,3,8,6,1},{5,1,6,2,7,3,8,4},
												{1,2,3,4,5,6,7,8},{2,4,6,8,1,3,5,7},
												{3,6,4,5,7,2,8,1},{1,4,6,7,5,8,2,3},
												{4,6,8,2,7,1,3,5},{2,5,7,4,8,3,1,6}};

	private final Map<Integer, List<Team>> matches = new HashMap<Integer, List<Team>>();
	
	public MatchesInSeasonImpl(Division d){
		for(int week = 0; week < POSITIONS.length; week++)
		{
			List<Team> insert = new LinkedList<Team>();
			
			for(int j = 0; j < 8; j++)
			{
				insert.add(d.getTeams().get(POSITIONS[week][j] - 1).getTeam());
			}
			
			matches.put(week, insert);
		}		
	}
	
	@Override
	public List<Team> getMatchesForWeek(Integer w) {
		return matches.get(w);
	}

}
