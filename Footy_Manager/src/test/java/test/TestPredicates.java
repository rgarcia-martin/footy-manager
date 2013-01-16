package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import employer.Employer;
import employer.EmployerImpl;
import employer.EmployerTypes;
import employer.PredicateEmployerWithType;

import average.Average;
import average.AverageImpl;
import average.AverageTypes;
import average.PredicateAverageType;

import player.FormTypes;
import player.Player;
import player.PlayerImpl;
import player.PositionTypes;
import player.PredicateDeclinePlayers;
import player.PredicatePlayerWithNumber;
import player.PredicatePlayerWithPosition;
import player.PredicatePotentialPlayers;
import player.PredicateRetirementPlayers;
import player.PredicateRightNonPlayerTeamFile;
import player.PredicateRightPlayerNamesFile;
import sponsor.PredicateRightSponsorFile;
import team.PlayerControlledTeam;
import team.PlayerControlledTeamImpl;
import team.ShowTeam;
import team.ShowTeamImpl;

public class TestPredicates {

	@Test
	public void testAverageType(){
		
		AverageTypes ave= AverageTypes.GK;
		Average av= new AverageImpl(AverageTypes.D, 1.0);
		PredicateAverageType pred= new PredicateAverageType(ave);
		
		assertFalse(pred.apply(av));
	}

	@Test
	public void testDeclinePlayers(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,7.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		PredicateDeclinePlayers pred= new PredicateDeclinePlayers();
		
		assertFalse(pred.apply(player));
	}
	
	@Test
	public void testEmployer(){
		
		EmployerTypes type= EmployerTypes.CS;
		Employer emp= new EmployerImpl(EmployerTypes.CS);
		PredicateEmployerWithType pred= new PredicateEmployerWithType(type);
		
		assertTrue(pred.apply(emp));
	}
	
	@Test
	public void testPlayerContainsPosition(){
		
		PositionTypes pos= PositionTypes.GK;
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,7.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		PredicatePlayerWithPosition pred= new PredicatePlayerWithPosition(pos);
		
		assertFalse(pred.apply(player));
	}
	
	@Test
	public void testPlayerNumber(){
		
		Integer number= 0;
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,7.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		PredicatePlayerWithNumber pred= new PredicatePlayerWithNumber(number);
		
		assertFalse(pred.apply(player));
	}
	
	@Test
	public void testPotentialPlayers(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,5.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		PredicatePotentialPlayers pred= new PredicatePotentialPlayers();
		
		assertFalse(pred.apply(player));
	}
	
	@Test
	public void testRetirementPlayer(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",43,5.0,FormTypes.MINUS,PositionTypes.GK,9,team);
		PredicateRetirementPlayers pred= new PredicateRetirementPlayers();
		
		assertTrue(pred.apply(player));
	}
	
	@Test
	public void testNonPlayerFile(){
		
		SortedSet<Integer> ranks= new TreeSet<Integer>();
		ranks.add(1);
		Set<String> names= new HashSet<String>();
		names.add("Test");
		PredicateRightNonPlayerTeamFile pred= new PredicateRightNonPlayerTeamFile(ranks, names);
		String team= "NO";
		
		assertFalse(pred.apply(team));
	}
	
	@Test
	public void testPlayerNameFile(){
		
		String name= "Test";
		PredicateRightPlayerNamesFile pred= new PredicateRightPlayerNamesFile();
		
		assertTrue(pred.apply(name));
	}
	
	@Test
	public void testSponsorFile(){
		
		Set<String> names= new HashSet<String>();
		names.add("Test");
		String sponsor= "Alberto;10;20";
		PredicateRightSponsorFile pred= new PredicateRightSponsorFile(names);
		
		assertTrue(pred.apply(sponsor));
	}
}