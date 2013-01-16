package player;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Iterables;

import dice.DiceImpl;

public class PreSeasonUpdatesSquadImpl implements PreSeasonUpdatesSquad{
	
	private List<Player> neverPotentialAgain;
	private List<Player> declined;
	private List<Player> retired;
	private static final Double ABILITY_CHANGE= 0.5;
	private static final Double[] abilities= {7.0,8.0,9.0};
	private static final Integer[] rolls= {1,2,3,4,5,6};
	private static final Integer[] ages= {31,32,33,34,35,36,37,38,39,40};
	
	public PreSeasonUpdatesSquadImpl(){
		
		neverPotentialAgain= new LinkedList<Player>();
		declined= new LinkedList<Player>();
		retired= new LinkedList<Player>();	
	}
	
	public void setPotential(Players players) {
		
		Iterable<Player> potentialPlayers= Iterables.filter(players.getPlayers(), new PredicatePotentialPlayers()); 
		for(Player player: potentialPlayers){
			
			Integer roll = DiceImpl.rollDice();
			boolean condition1= player.getAbility()<abilities[0] && roll.equals(rolls[0]);
			boolean condition2= player.getAbility()>=abilities[0] && player.getAbility()<abilities[1] && roll<rolls[2];
			boolean condition3= player.getAbility()>=abilities[1] && player.getAbility()<abilities[2] && roll<rolls[3];
			boolean condition4= player.getAbility().equals(abilities[2]) && roll<rolls[4];
			boolean condition5= player.getAbility()>abilities[2] && roll<rolls[5];
	
			if(condition1 || condition2 || condition3 || condition4 || condition5){
				player.setPotential(false);
				neverPotentialAgain.add(player);
			}	
		}	
	}

	public void setDecline(Players players){
		
		Iterable<Player> declinePlayers= Iterables.filter(players.getPlayers(), new PredicateDeclinePlayers()); 
		for(Player player: declinePlayers){
			
			Integer roll = DiceImpl.rollDice();
			if(player.getPositions().contains(PositionTypes.GK)){
				
				boolean condition1= player.getAge().equals(ages[3]) && roll.equals(rolls[0]);
				boolean condition2= player.getAge().equals(ages[4]) && roll<rolls[2];
				boolean condition3= player.getAge().equals(ages[5]) && roll<rolls[3];
				boolean condition4= player.getAge().equals(ages[6]) && roll<rolls[4];
				boolean condition5= player.getAge()>ages[6] && roll<rolls[5];
				
				if(condition1 || condition2 || condition3 || condition4 || condition5){
					player.updateAbility(-ABILITY_CHANGE);
					declined.add(player);
				}	
			}
			
			else{
				
				boolean condition1= player.getAge().equals(ages[0]) && roll.equals(rolls[0]);
				boolean condition2= player.getAge()>ages[0] && player.getAge()<ages[3] && roll<rolls[2];
				boolean condition3= player.getAge()>ages[2] && player.getAge()<ages[5] && roll<rolls[3];
				boolean condition4= player.getAge().equals(ages[5]) && roll<rolls[4];
				boolean condition5= player.getAge()>ages[5] && roll<rolls[5];
					
				if(condition1 || condition2 || condition3 || condition4 || condition5){
					player.updateAbility(-ABILITY_CHANGE);
					declined.add(player);
				}
			}
		}
	}
	
	public void setRetirement(Players players){
		
		Iterable<Player> retirementPlayers= Iterables.filter(players.getPlayers(), new PredicateRetirementPlayers()); 
		for(Player player: retirementPlayers){
			
			Integer roll = DiceImpl.rollDice();
			if(player.getPositions().contains(PositionTypes.GK)){
				
				boolean condition1= player.getAge().equals(ages[4]) && roll.equals(rolls[0]);
				boolean condition2= player.getAge().equals(ages[5]) && roll<rolls[2];
				boolean condition3= player.getAge().equals(ages[6]) && roll<rolls[3];
				boolean condition4= player.getAge().equals(ages[7]) && roll<rolls[4];
				boolean condition5= player.getAge()>ages[7] && roll<rolls[5];
				
				if(condition1 || condition2 || condition3 || condition4 || condition5){
					retired.add(player);
				}	
			}
			
			else{
				
				boolean condition1= player.getAge().equals(ages[2]) && roll.equals(rolls[0]);
				boolean condition2= player.getAge()>ages[2] && player.getAge()<ages[5] && roll<rolls[2];
				boolean condition3= player.getAge().equals(ages[5]) && roll<rolls[3];
				boolean condition4= player.getAge().equals(ages[6]) && roll<rolls[4];
				boolean condition5= player.getAge()>ages[6] && player.getAge()<ages[9] && roll<rolls[5];
				boolean condition6= player.getAge().equals(ages[9]);
				
				if(condition1 || condition2 || condition3 || condition4 || condition5 || condition6){
					retired.add(player);
				}
			}
		}
	}
	
	public List<Player> getNeverPotentialAgain() {
		return neverPotentialAgain;
	}

	public List<Player> getDeclined() {
		return declined;
	}

	public List<Player> getRetired() {
		return retired;
	}
}