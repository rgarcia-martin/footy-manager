package sponsor;

import player.Player;
import player.PlayerImpl;
import dice.DiceImpl;
import division.DivisionType;
import team.PlayerControlledTeam;

public class SponsorValueImpl implements SponsorValue {

	private static final Integer[] VALUES= {1,2,3,6,8,26};
	private static final Integer[] AGES= {25,32};
	private static final Integer[] CAPACITIES= {40000,55000};
	
	@Override
	public Integer calcValue(PlayerControlledTeam t) {
		Integer value = 0;
		
		value += DiceImpl.rollDice();
		
		if(t.lastYearChampions()){
			value += VALUES[2];
		}	
		
		if(t.runnerUp()){
			value += VALUES[1];
		}	
		
		if(t.getDivision().getCategory().equals(DivisionType.PREMIER_DIVISION)){
			value += VALUES[4];
		}	
		
		if(t.getDivision().getCategory().equals(DivisionType.SECOND_DIVISION)){
			value += VALUES[3];
		}	
		
		if(t.getDivision().getCategory().equals(DivisionType.THIRD_DIVISION)){
			value += VALUES[2];
		}	
		
		boolean risingStar=false;
		
		boolean reliableBrand=false;
		
		for(Player player: t.getPlayersInTeam().getPlayers()){
			if(player.getAbility().equals(PlayerImpl.getABL_MAX())){
				if(player.getAge() < AGES[0])
					risingStar=true;
				
				if(player.getAge() > AGES[1]) 
					reliableBrand=true;
			}		
		}
		
		if(risingStar){
			value += VALUES[0];
		}
		
		if(reliableBrand){
			value += VALUES[0];
		}
		
		Integer stadiumCapacity= t.getStadium().getCapacity();

		if(stadiumCapacity >= CAPACITIES[0] && stadiumCapacity < CAPACITIES[1]){
			value += VALUES[0];
		}
		
		if(stadiumCapacity >= CAPACITIES[1]){
			value += VALUES[1];
		}
		
		if(t.getSponsorAgreement() != null)
		{
			if(t.getSponsorAgreement().getTemp() >= t.getSponsorAgreement().getDuration()){
				value += VALUES[1];
			}
		}
		
		if(value<VALUES[5]){
			
			Double conversion= new Double(value);
			conversion= conversion/2-0.5;
			value = (int)Math.floor(conversion);
		}
		
		return value;
	}
}
