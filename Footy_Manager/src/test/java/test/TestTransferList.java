package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import player.FormTypes;
import player.Player;
import player.PlayerImpl;
import player.PositionTypes;


import team.PlayerControlledTeam;
import team.PlayerControlledTeamImpl;
import team.ShowTeam;
import team.ShowTeamImpl;
import transferList.ShowTransferList;
import transferList.ShowTransferListImpl;
import transferList.TransferList;
import transferList.TransferListImpl;

public class TestTransferList {
	
	ShowTransferList show;
	TransferList transferList;
	
	@Before
	public void setUp(){
		
		show= new ShowTransferListImpl();
		transferList= new TransferListImpl(show);
	}
	
	@After
	public void tearDown(){
		
		show = null;
		transferList = null;
	}
	
	@Test
	public void testContainsPlayer(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,7.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		
		boolean test= transferList.containsPlayer(player);
		assertFalse(test);
	}
	
	@Test
	public void testAddTransferList(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,7.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		
		transferList.addTransferList(player);
		assertTrue(transferList.containsPlayer(player));
	}
	
	@Test
	public void testSellPlayer(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,7.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		team.getPlayersInTeam().addPlayer(player);
		transferList.addTransferList(player);
		transferList.sellPlayer(player, 200000.0);
		assertTrue(transferList.containsPlayer(player));
	}
	
	@Test
	public void testGetPrice1(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",20,7.0,FormTypes.MINUS,PositionTypes.CF,9,team);
		
		assertTrue(transferList.getPrice(player)== 2000000.0);
	}
	
	@Test
	public void testGetPrice2(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",35,9.5,FormTypes.MINUS,PositionTypes.CF,9,team);
		
		assertTrue(transferList.getPrice(player)== 5000000.0);
	}
	
	@Test
	public void testGetPrice3(){
		
		ShowTeam showTeam= new ShowTeamImpl();
		PlayerControlledTeam team= new PlayerControlledTeamImpl("Test", 1, showTeam);
		Player player= new PlayerImpl("Test",25,5.5,FormTypes.MINUS,PositionTypes.GK,1,team);
		
		assertTrue(transferList.getPrice(player)== 0.0);
	}
}