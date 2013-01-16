package match;

import java.util.List;

import player.Player;
import player.Players;
import player.PlayersImpl;
import player.PositionTypes;
import dice.DiceImpl;

public class AssistancesImpl implements Assistances {

	private StadisticsTeam teamShooter;
	
	public AssistancesImpl(StadisticsTeam tS){
		teamShooter = tS;
	}

	@Override
	public void calcAssis(Integer n) {
		/*To see who made the assist To see who made the assist To see who made the assist roll a dice. 
		    6 means that there was no assist (it was a solo goal).  
			A 1 means that the assist was made by a Forward (roll as before to see who). 
			If the only Forward just scored then it becomes “no assist”. 
			A 2-4 means that the assist was made by a Midfielder (roll as before to see 
			who). 
			A 5 means that the assist was made by a Defender (roll as before to see who). 
			For each assist, make a “a” in the current column of the player’s “st” ro
		*/
		Integer resultDice = DiceImpl.rollDice();
		
		if(resultDice < 6)
		{
			Players assisters = new PlayersImpl();
			Player assister;
			
			List<Player> listGoalers = teamShooter.getListGoals();
			Player shooter = listGoalers.get(n);
			
			if(resultDice > 0 && resultDice <= 1)
			{
				//Si el numero de Delanteros es 1 no es asistencia, sino, tirar el dado para saber quien de los
				//otros delanteros asistio.
				assisters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CF));
				
				if(shooter.getPositions().contains(PositionTypes.CF))
				{
					assisters.remove(shooter);
				}
			}
			else if(resultDice > 1 && resultDice <= 4)
			{
				//Centrocampista
				assisters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CM));
				assisters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.SM));
				
				if(shooter.getPositions().contains(PositionTypes.CM) || shooter.getPositions().contains(PositionTypes.SM))
				{
					assisters.remove(shooter);
				}
				
			}
			else if(resultDice > 4 && resultDice <= 5)
			{
				//Defensa
				assisters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.CB));
				assisters.addAll(teamShooter.getPlayersInMatch().findPlayer(PositionTypes.SB));
				
				if(shooter.getPositions().contains(PositionTypes.CB) || shooter.getPositions().contains(PositionTypes.SB))
				{
					assisters.remove(shooter);
				}
			}
			
			resultDice = DiceImpl.rollDice(assisters.getPlayers().size());
			
			assister = assisters.getPlayers().get(resultDice - 1);
			
			if(teamShooter.getAssistances().containsKey(assister))
			{
				Integer assisPlayer = teamShooter.getAssistances().get(assister);
				teamShooter.getAssistances().put(assister, assisPlayer + 1);
			}
			else
			{
				teamShooter.getAssistances().put(assister, 1);
			}
		}
	}
}
