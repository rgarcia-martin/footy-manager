package match;

import java.util.List;
import java.util.Map;

import average.Averages;

import player.Player;
import player.Players;

import team.Team;

public interface StadisticsTeam {
	Team getTeam();
	
	Map<Player,Integer> getGoals();
	Map<Player,Integer> getGoalsConceded();
	Map<Player,Integer> getAssistances();
	
	List<Player> getListGoals();
	
	Players getPlayersInMatch();	

	Formation getFormation();
	
	Averages getAverage();
	
	boolean isPlayerTeam();
}
