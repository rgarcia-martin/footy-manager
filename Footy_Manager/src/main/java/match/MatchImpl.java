package match;

import java.util.LinkedList;
import java.util.List;

import team.Team;

import average.*;

public class MatchImpl implements Match {
	private final List<Integer> score;
	private final StadisticsMatch stadistics;
	private final List<Integer> shoots;

	private static final Integer POS_HOME = 0;
	private static final Integer POS_AWAY = 1;
	
	//Constructor para Player vs Player
	public MatchImpl(Team h, Squad hs, Team a, Squad as){
		score = new LinkedList<Integer>();
		score.add(0);
		score.add(0);
		
		stadistics = new StadisticsMatchImpl(h, hs, a, as);
		shoots = new LinkedList<Integer>();
	}
	
	//Constructor para Maquina vs Player
	public MatchImpl(Team t1, AverageLocation lt1, Team t2, Squad t2s){
		score = new LinkedList<Integer>();
		score.add(0);
		score.add(0);
		
		stadistics = new StadisticsMatchImpl(t1, lt1, t2, t2s);
		shoots = new LinkedList<Integer>();
	}
	
	//Constructor para Maquina vs Maquina
	public MatchImpl(Team t1, Team t2){
		score = new LinkedList<Integer>();
		score.add(0);
		score.add(0);
		
		stadistics = new StadisticsMatchImpl(t1, t2);
		shoots = new LinkedList<Integer>();
	}

	@Override
	public StadisticsMatch getStatistics() {
		return stadistics;
	}

	@Override
	public Integer getGoalsHome() {
		return score.get(POS_HOME);
	}

	@Override
	public Integer getGoalsAway() {
		return score.get(POS_AWAY);
	}

	@Override
	public void playMatch() {
		calcShoots();

		AverageLocation loc;
		Integer teamShoots;
		StadisticsTeam teamShooter;
		StadisticsTeam teamKilled;
		
		Goals gs;
		Assistances as;
		Injuries ls;
		
		//Calculo de los tiros que han sido gol por cada equipo
		for(int t = 0; t < 2; t++)
		{
			if(t == 0)
			{
				loc = AverageLocation.HOME;
				teamShoots = shoots.get(POS_HOME);
				teamShooter = stadistics.getHome();
				teamKilled = stadistics.getAway();
			}
			else
			{
				loc = AverageLocation.AWAY;
				teamShoots = shoots.get(POS_AWAY);
				teamShooter = stadistics.getAway();
				teamKilled = stadistics.getHome();				
			}

			gs = new GoalsImpl(this, teamShooter, teamKilled, loc);
			
			for(int s = 0; s < teamShoots; s++)
			{	
				gs.calcGoal();
			}			
			
			if(teamShooter.isPlayerTeam())
			{
				//Asistencias
				as = new AssistancesImpl(teamShooter);
				
				for(Integer s = 0; s < getMyGoals(teamShooter.getTeam()); s++)
				{
					as.calcAssis(s);
				}
				
				//Lesiones
				ls = new InjuriesImpl(teamShooter);
				
				ls.calcInjuries();
			}
		}
	}
	
	private void calcShoots(){
		Averages avHome = stadistics.getHome().getAverage();
		Averages avAway = stadistics.getAway().getAverage();
		
		Double relShootsHome = avHome.findAverage(AverageTypes.A_PLUS).getValue() - avAway.findAverage(AverageTypes.D_PLUS).getValue();
		Double relShootsAway = avAway.findAverage(AverageTypes.A_PLUS).getValue() - avHome.findAverage(AverageTypes.D_PLUS).getValue();

				
		shoots.add(calcShoots(relShootsHome));	
		
		Integer shootsAway = calcShoots(relShootsAway);
		
		if(shootsAway > 1) /**< A los equipos en Away se les debe restar 1 tiro por estar jugando en Visitante :D */
			shootsAway--;
		
		shoots.add(shootsAway);
	}
	
	private Integer calcShoots(Double rel){
		Integer resultShoosCalculated = 0;
		
		Double MIN = -40.25;
		Double MAX;
		
		if(rel <= MIN)
			resultShoosCalculated = 1;
		
		for(int i = 2; i < 10; i++)
		{
			MAX = MIN + 5.0;
			
			if(rel > MIN && rel <= MAX)
				resultShoosCalculated = i;
			
			MIN = MAX;
		}
		
		if(rel > MIN)
			resultShoosCalculated = 10;
		
		return resultShoosCalculated;
	}

	@Override
	public StadisticsTeam getMyStatistics(Team t) {
		StadisticsTeam res;
		
		if(t.equals(stadistics.getHome().getTeam()))
		{
			res = stadistics.getHome();
		}
		else
		{
			res = stadistics.getAway();
		}
		
		return res;
	}

	@Override
	public Integer getMyGoals(Team t) {
		Integer res;
		
		if(t.equals(stadistics.getHome().getTeam()))
		{
			res = getGoalsHome();
		}
		else
		{
			res = getGoalsAway();
		}
		
		return res;
	}

	@Override
	public Integer getMyGoalsConceded(Team t) {
		Integer res;
		
		if(t.equals(stadistics.getHome().getTeam()))
		{
			res = getGoalsAway();
		}
		else
		{
			res = getGoalsHome();
		}
		
		return res;
	}

	@Override
	public AverageLocation getMyLocation(Team t) {
		AverageLocation res;
		
		if(t.equals(stadistics.getHome().getTeam()))
		{
			res = AverageLocation.HOME;
		}
		else
		{
			res = AverageLocation.AWAY;
		}
		
		return res;
	}

	@Override
	public boolean playingAtHome(Team t) {
		boolean res = false;
		
		if(t.equals(stadistics.getHome().getTeam()))
		{
			res = true;
		}
		
		return res;
	}

	@Override
	public List<Integer> getGoals() {
		return score;
	}
}
