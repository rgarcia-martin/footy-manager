package match;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dice.DiceImpl;

import player.*;

import team.*;

import average.*;

public class StadisticsTeamImpl implements StadisticsTeam {
	
	private final Team team;

	private final Map<Player, Integer> goals;
	private final Map<Player, Integer> goalsConceded;
	private final Map<Player, Integer> assistances;

	private final List<Player> listGoals;
	
	private final Players playersInMatch;
	
	private final Formation formation;
	
	private Averages averagesForMatch;
	
	//Constructor para Equipos Manejados
	public StadisticsTeamImpl(Team t, Players ps, Formation f){
		goals = new HashMap<Player, Integer>();
		goalsConceded = new HashMap<Player, Integer>();
		assistances = new HashMap<Player, Integer>();

		listGoals = new LinkedList<Player>();
		
		team = t;
		playersInMatch = ps;
		formation = f;
		
		averagesForMatch = calcAveragesForPlayer();
	}
	
	//Constructor para Equipos de Maquina
	public StadisticsTeamImpl(Team t, AverageLocation l){
		goals = null;
		goalsConceded = null;
		assistances = null;
		listGoals = null;
		
		playersInMatch = null;
		
		formation = null;
		
		team = t;
		
		averagesForMatch = calcAveragesForMachine(l);
	}

	@Override
	public Team getTeam() {
		return team;
	}

	@Override
	public Map<Player, Integer> getGoals() {
		return goals;
	}

	@Override
	public Map<Player, Integer> getGoalsConceded() {
		return goalsConceded;
	}

	@Override
	public Map<Player, Integer> getAssistances() {
		return assistances;
	}

	@Override
	public Players getPlayersInMatch() {
		return playersInMatch;
	}

	@Override
	public Formation getFormation() {
		return formation;
	}

	@Override
	public Averages getAverage() {
		return averagesForMatch;
	}

	@Override
	public boolean isPlayerTeam() {
		return team instanceof PlayerControlledTeam;
	}


	private Averages calcAveragesForPlayer(){
		Averages res = new AveragesImpl();
		
		Double averageGK = 0.0;
		
		Double averageD = 0.0;
		Double averageM = 0.0;
		Double averageA = 0.0;
		
		for (Player e : playersInMatch.getPlayers()){
			if (e.getPositions().contains(PositionTypes.GK))
			{
				averageGK = e.getAbility();	
			}
			
			if(e.getPositions().contains(PositionTypes.CB) || e.getPositions().contains(PositionTypes.SB))
			{
				averageD += e.getAbility();
			}
				
			if(e.getPositions().contains(PositionTypes.CM) || e.getPositions().contains(PositionTypes.SM))
			{
				averageM += e.getAbility();
			}
			
			if(e.getPositions().contains(PositionTypes.CF))
			{
				averageA += e.getAbility();
			}
		}		
		
		Average aGK = new AverageImpl(AverageTypes.GK, averageGK);
		
		Average aD = new AverageImpl(AverageTypes.D, averageD);
		Average aM = new AverageImpl(AverageTypes.M, averageM);
		Average aA = new AverageImpl(AverageTypes.A, averageA);
		
		Average aDP = new AverageImpl(AverageTypes.D_PLUS, (aM.getValue() / 2) + aD.getValue());
		Average aAP = new AverageImpl(AverageTypes.A_PLUS, (aM.getValue() / 2) + aA.getValue());
		
		res.add(aGK);
		
		res.add(aD);
		res.add(aM);
		res.add(aA);
		
		res.add(aDP);
		res.add(aAP);
		
		return res;
	}
	
	private Averages calcAveragesForMachine(AverageLocation l){
		Averages res = new AveragesImpl();
		
		NonPlayerControlledTeam t = (NonPlayerControlledTeam) team;
		
		Averages averageFiltered = t.getAverage().filterAverage(l);
		averageFiltered = averageFiltered.filterAverage(calculateNonPlayerControlledTeamMode());
		
		res.add(t.getAverage().findAverage(AverageTypes.GK));
		res.addAll(averageFiltered);
		
		return res;
	}
	
	private AverageMode calculateNonPlayerControlledTeamMode(){
		AverageMode res = AverageMode.DEPLETED;
		
		Integer resultDice = DiceImpl.rollDice();
		
		if (resultDice < 5){
			res = AverageMode.FULL;
		}
		
		return res;
	}

	@Override
	public List<Player> getListGoals() {
		return listGoals;
	}
}
