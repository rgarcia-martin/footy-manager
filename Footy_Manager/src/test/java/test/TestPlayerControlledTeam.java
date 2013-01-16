package test;



import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import player.FormTypes;
import player.Player;
import player.PlayerImpl;
import player.Players;
import player.PositionTypes;

import employer.EmployerTypes;
import employer.Employers;

import sponsor.SponsorAgreement;
import team.PlayerControlledTeam;
import team.PlayerControlledTeamImpl;
import team.ShowTeam;
import team.ShowTeamImpl;




public class TestPlayerControlledTeam {

	private PlayerControlledTeam pCT;
	private ShowTeam showTeam;
	private Player testPlayer;

	@Before
	public void setUp(){

		testPlayer=new PlayerImpl("gaby",21,7.0,FormTypes.PLUS,PositionTypes.SB);
		pCT = new PlayerControlledTeamImpl("testTeam",new ShowTeamImpl());
		showTeam=new ShowTeamImpl();
		
	}
	@After
	public void cleanup(){
		testPlayer=null;
		pCT=null;
		showTeam=null;
	
	}
	@Test
	public void testConstructor(){
		@SuppressWarnings("unused")
		PlayerControlledTeam  proof =new PlayerControlledTeamImpl("TestTeam",showTeam);
	}
	@Test
	public void testConstructor2(){
		@SuppressWarnings("unused")
		PlayerControlledTeam  proof =new PlayerControlledTeamImpl("TestTeam",4,showTeam);
	}
	@Test
	public void testGetFinances(){
		Double anterior=pCT.getFinances();
		assertTrue(anterior==pCT.getFinances());
	}
	
	@Test
	public void testBuyPlayer(){
		pCT.addIncome(10000.0);
		List<Player> panterior= pCT.getPlayersInTeam().getPlayers();
		panterior.remove(testPlayer);
		Double anterior=pCT.getFinances();
		pCT.buyPlayer(testPlayer, 10000.0);
		assertTrue(pCT.getFinances()==anterior-10000.0 
				&& pCT.getPlayersInTeam().getPlayers()==panterior);
		
	}
	@Test
	public void testGetPlayersInTeam(){
		Players p=pCT.getPlayersInTeam();
		assertTrue(p==pCT.getPlayersInTeam());
	}
	@Test
	public void testGetSponsorAgreement(){
		SponsorAgreement sp=pCT.getSponsorAgreement();
		assertTrue(sp==pCT.getSponsorAgreement());
	}
	@Test
	public void testHireEmployer(){
		pCT.hireEmployer(EmployerTypes.CD);
		assertTrue(pCT.getEmployersOfTeam().existsEmployer(EmployerTypes.CD));
	}
	@Test
	public void tesFireEmployer(){
		pCT.hireEmployer(EmployerTypes.AM);
		pCT.fireEmployer(EmployerTypes.AM);
		assertTrue(!pCT.getEmployersOfTeam().existsEmployer(EmployerTypes.CD));
	}
	@Test
	public void testGetEmployersOfTeam(){
		Employers eOT=pCT.getEmployersOfTeam();
		assertTrue(pCT.getEmployersOfTeam()==eOT);
	}
	@Test
	public void testSetFinancesCost(){
		Double anterior=pCT.getFinances();
		pCT.setFinancesCost(100.0);
		assertTrue(pCT.getFinances()==anterior-100.0);
	}
	@Test
	public void testAddIncome(){
		Double anterior=pCT.getFinances();
		pCT.addIncome(20.0);
		assertTrue(pCT.getFinances()==anterior+20.0);
	}
	@Test
	public void testAddWaste(){
		pCT.addIncome(100.0);
		Double anterior=pCT.getFinances();
		pCT.addWaste(20.0);
		assertTrue(pCT.getFinances()==anterior-20.0);
	}

}
