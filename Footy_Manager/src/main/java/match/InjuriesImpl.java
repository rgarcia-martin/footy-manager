package match;

import player.Player;
import team.PlayerControlledTeam;
import dice.DiceImpl;

public class InjuriesImpl implements Injuries {

	private final StadisticsTeam team;
	
	public InjuriesImpl(StadisticsTeam t)
	{
		team = t;
	}
	
	@Override
	public void calcInjuries() {
		Integer resultDice;
		
		for(int s = 0; s < team.getPlayersInMatch().getPlayers().size(); s++)
		{
			resultDice = DiceImpl.roll2Dice();
			
			if(resultDice > 10 && resultDice <= 12)
			{
				//Jugador Lesionado
				//2...................12
				Integer[] timeInjured = {7, 5, 3, 2, 1, 1, 2, 3, 4, 5, 6};
				
				resultDice = DiceImpl.roll2Dice();
				resultDice -= 2;
				
				Integer site = ((PlayerControlledTeam) team.getTeam()).getPlayersInTeam().getPlayers().indexOf(team.getPlayersInMatch().getPlayers().get(s));
				Player p = ((PlayerControlledTeam) team.getTeam()).getPlayersInTeam().getPlayers().get(site);
				
				p.setInjured(timeInjured[resultDice]);
			}
		}
	}

}
