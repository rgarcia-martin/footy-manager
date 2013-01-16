package match;

import java.util.List;

import average.AverageLocation;
import team.Team;

public interface Match {
	Integer getGoalsHome();
	Integer getGoalsAway();
	List<Integer> getGoals();

	StadisticsMatch getStatistics();

	StadisticsTeam getMyStatistics(Team t);

	Integer getMyGoals(Team t);
	Integer getMyGoalsConceded(Team t);
	AverageLocation getMyLocation(Team t);
	
	boolean playingAtHome(Team t);
	
	void playMatch();
}
