package test;

import static org.junit.Assert.*;

import org.junit.Test;

import comparators.ComparatorTeamsByRanking;

import division.ClassificationData;
import division.ClassificationDataImpl;
import division.Division;
import division.DivisionImpl;
import division.DivisionType;

import team.PlayerControlledTeam;
import team.PlayerControlledTeamImpl;
import team.ShowTeam;
import team.ShowTeamImpl;

public class TestComparator {

	@Test
	public void testPlayerToNumber(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team1= new PlayerControlledTeamImpl("Test1", 1, showTeam);
		PlayerControlledTeam team2= new PlayerControlledTeamImpl("Test2", 2, showTeam);
		Division division= new DivisionImpl(DivisionType.SECOND_DIVISION);
		ClassificationData data1= new ClassificationDataImpl(team1, division);
		ClassificationData data2= new ClassificationDataImpl(team2, division);
		ComparatorTeamsByRanking comparator= new ComparatorTeamsByRanking();
		Integer comp= comparator.compare(data1, data2);

		assertTrue(comp<0);
	}
}
