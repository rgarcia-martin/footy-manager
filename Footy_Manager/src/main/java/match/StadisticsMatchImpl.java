package match;

import average.AverageLocation;
import team.Team;

public class StadisticsMatchImpl implements StadisticsMatch {

	private final StadisticsTeam home;
	private final StadisticsTeam away;
	
	//Constructor para Player vs Player
	public StadisticsMatchImpl(Team h, Squad hs, Team a, Squad as){
		home = new StadisticsTeamImpl(h, hs.getPlayers(), hs.getFormation());
		away = new StadisticsTeamImpl(a, as.getPlayers(), as.getFormation());
	}
	
	//Constructor para Maquina vs Player
	public StadisticsMatchImpl(Team t1, AverageLocation lt1, Team t2, Squad t2s){
		if(lt1.equals(AverageLocation.HOME))
		{
			home = new StadisticsTeamImpl(t1, lt1);
			away = new StadisticsTeamImpl(t2, t2s.getPlayers(), t2s.getFormation());
		}
		else
		{
			home = new StadisticsTeamImpl(t2, t2s.getPlayers(), t2s.getFormation());
			away = new StadisticsTeamImpl(t1, lt1);			
		}
	}
	
	//Constructor para Maquina vs Maquina
	public StadisticsMatchImpl(Team t1, Team t2){
		home = new StadisticsTeamImpl(t1, AverageLocation.HOME);
		away = new StadisticsTeamImpl(t2, AverageLocation.AWAY);
	}

	@Override
	public StadisticsTeam getHome() {
		return home;
	}

	@Override
	public StadisticsTeam getAway() {
		return away;
	}

}
