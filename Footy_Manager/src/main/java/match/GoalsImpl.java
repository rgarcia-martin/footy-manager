package match;

import player.Player;
import player.Players;
import player.PlayersImpl;
import player.PositionTypes;
import dice.DiceImpl;
import division.DivisionType;
import average.AverageLocation;
import average.AverageTypes;

public class GoalsImpl implements Goals{

	private Match match;
	private StadisticsTeam teamShooter;
	private StadisticsTeam teamKilled;
	private AverageLocation loc;

	private static final Integer POS_HOME = 0;
	private static final Integer POS_AWAY = 1;
	
	public GoalsImpl(Match m, StadisticsTeam tS, StadisticsTeam tK, AverageLocation l){
		match = m;
		teamShooter = tS;
		teamKilled = tK;
		loc = l;
	}
	
	@Override
	public void calcGoal() {
		Double pointsOfGK = teamKilled.getAverage().findAverage(AverageTypes.GK).getValue();
		Double pointsOfShooter;
		
		Players shooters = new PlayersImpl();
		Player shooter;
		
		Integer resultDice;
		
		if(teamShooter.isPlayerTeam())
		{
			//El equipo es de un Jugador, hay que mirar quien efectua el tiro y contabilizarlo
			
			//1º Buscar el Tirador!
			if(teamShooter.getFormation().isEspecialFormation())
			{
				//El equipo eligio una formacion especial!!!
				resultDice = DiceImpl.rollDice();
				
				if(resultDice <= 2)
				{
					//Tira un delantero
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CF));							
				}
				else if (resultDice>=3 && resultDice <=5)
				{
					//Tira un centrocampista
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CM));
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.SM));
				}
				else
				{
					//Tira un defensa
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CB));
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.SB));
				}
			}
			else
			{
				//El equipo va con una formacion normal
				resultDice = DiceImpl.rollDice();
				
				if(resultDice <= 3)
				{
					//Tira un delantero
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CF));							
				}
				else if (resultDice >= 6)
				{
					//Tira un defensa
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CB));
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.SB));
				}
				else
				{
					//Tira un centrocampista
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CM));
					shooters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.SM));
				}
			}
			
			resultDice = DiceImpl.rollDice(shooters.getPlayers().size());
			
			shooter = shooters.getPlayers().get(resultDice - 1);
			
			//2º Restar habilidad de tiro dependiendo de la posicion del jugador
			
			pointsOfShooter = shooter.getAbility();
			
			if(shooter.getPositions().contains(PositionTypes.CM) || shooter.getPositions().contains(PositionTypes.SM))
			{
				pointsOfShooter -= 1;
			}
			else if(shooter.getPositions().contains(PositionTypes.CB) || shooter.getPositions().contains(PositionTypes.SB))
			{
				pointsOfShooter -= 2;
			}
			
			//3º Mirar si es goooool!
			pointsOfShooter += DiceImpl.rollDice();
			Double PG = pointsOfGK + DiceImpl.rollDice();
			
			if(pointsOfShooter > PG)
			{
				//GOOOOOOOL
				if(loc.equals(AverageLocation.HOME))
				{
					match.getGoals().set(POS_HOME, match.getGoals().get(POS_HOME) + 1);
				}
				else
				{
					match.getGoals().set(POS_AWAY, match.getGoals().get(POS_AWAY) + 1);							
				}

				if(teamShooter.getGoals().containsKey(shooter))
				{
					Integer goalsPlayer = teamShooter.getGoals().get(shooter);
					teamShooter.getGoals().put(shooter, goalsPlayer + 1);
				}
				else
				{
					teamShooter.getGoals().put(shooter, 1);
				}
				
				teamShooter.getListGoals().add(shooter);
				
				//Anotamos a quien se le metio el gol si se puede
				if(teamKilled.isPlayerTeam())
				{
					Player GK = teamKilled.getPlayersInMatch().findPlayer(PositionTypes.GK).get(0);
					
					if(teamKilled.getGoalsConceded().containsKey(GK))
					{
						Integer goalsConcededPlayer = teamKilled.getGoalsConceded().get(GK);
						teamKilled.getGoalsConceded().put(GK, goalsConcededPlayer + 1);
					}
					else
					{
						teamKilled.getGoalsConceded().put(GK, 1);
					}
				}
			}
		}
		else
		{
			//El equipo es de la Maquina, solo calcular cuantos de los tiros son goles
			
			resultDice = DiceImpl.rollDice();
			
			pointsOfShooter = 0.0;
			
			if(teamShooter.getTeam().getDivision().getCategory().equals(DivisionType.PREMIER_DIVISION))
			{
				pointsOfShooter = 10.0;
			}
			else if(teamShooter.getTeam().getDivision().getCategory().equals(DivisionType.SECOND_DIVISION))
			{
				pointsOfShooter = 9.0;
			}
			else if(teamShooter.getTeam().getDivision().getCategory().equals(DivisionType.THIRD_DIVISION))
			{
				pointsOfShooter = 8.0;
			}
			else if(teamShooter.getTeam().getDivision().getCategory().equals(DivisionType.FOURTH_DIVISION))
			{
				pointsOfShooter = 7.0;
			}
			
			if(loc.equals(AverageLocation.HOME))
			{
				//A 1, 2 or 3 means that a Forward player is having a shot. 
				//A 4 or 5 is a Midfielder. A 6 is a Defender.
				if(resultDice >= 1 && resultDice <= 3)
				{
					//Fue un delantero
					pointsOfShooter -= 0;
				}
				else if(resultDice >= 4 && resultDice <= 5)
				{
					//Fue un centrocampista
					pointsOfShooter -= 1;
				}
				else
				{
					//Fue un defensa
					pointsOfShooter -= 2;
				}
			}
			else
			{
				//A 1 or 2 means that a Forward player is having a shot. A 3, 4, 
				//or 5 is a Midfielder. A 6 is a Defender
				if(resultDice >= 1 && resultDice <= 2)
				{
					//Fue un delantero
					pointsOfShooter -= 0;
				}
				else if(resultDice >= 3 && resultDice <= 5)
				{
					//Fue un centrocampista
					pointsOfShooter -= 1;
				}
				else
				{
					//Fue un defensa
					pointsOfShooter -= 2;
				}
			}
			
			pointsOfShooter += DiceImpl.rollDice();
			Double PG = pointsOfGK + DiceImpl.rollDice();
			
			if(pointsOfShooter > PG)
			{
				//GOOOOOOOL						
				if(loc.equals(AverageLocation.HOME))
				{
					match.getGoals().set(POS_HOME, match.getGoals().get(POS_HOME) + 1);
				}
				else
				{
					match.getGoals().set(POS_AWAY, match.getGoals().get(POS_AWAY) + 1);							
				}

				if(teamKilled.isPlayerTeam())
				{
					Player GK = teamKilled.getPlayersInMatch().findPlayer(PositionTypes.GK).get(0);
					
					if(teamKilled.getGoalsConceded().containsKey(GK))
					{
						Integer goalsConcededPlayer = teamKilled.getGoalsConceded().get(GK);
						teamKilled.getGoalsConceded().put(GK, goalsConcededPlayer + 1);
					}
					else
					{
						teamKilled.getGoalsConceded().put(GK, 1);
					}
				}
			}
		}
	}
}
