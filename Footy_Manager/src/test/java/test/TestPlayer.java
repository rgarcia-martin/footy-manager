package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.LinkedList;
import java.util.List;


import exceptions.*;
import player.*;
import team.*;

/**
 * @author  Rafael
 */
public class TestPlayer {
	/**
	 * @uml.property  name="p1"
	 * @uml.associationEnd  
	 */
	private Player p1;
	/**
	 * @uml.property  name="pTeam"
	 * @uml.associationEnd  
	 */
	private Player pTeam;
	/**
	 * @uml.property  name="t1"
	 * @uml.associationEnd  
	 */
	private static PlayerControlledTeam t1;
	
	/**
	 * @class TestPlayer : realizar las pruebas de clase PlayerImpl 
	 * Pruebas a realizar:
	 * 
	 * Creacion de Jugador:
	 * 	-Por parametros:
	 * 		-Valores Erroneos
	 * 
	 * 	-Por String:
	 * 		-Valores Erroneos
	 * 
	 * Modificacion de Valores modificables:
	 * 	setNumber(Integer number)
	 * 	setInjured(Integer i)
	 * 	setActualTraining(PlayerImpl.TrainingTypes actualTraining)
	 *  setActualTraining(PlayerImpl.TrainingTypes actualTraining, boolean teamHaveHeadCoach)
	 *  addPosition(PlayerImpl.PositionTypes pos)
	 *  addPosition(Collection<PlayerImpl.PositionTypes> pos)
	 * 
	 * Funcionalidad Personalizada de la Clase:
	 * 	-Lesionar un Jugador durante 3 semanas y comprobar que una vez actualizadas el jugador vuelva a estar operativo
	 * 
	 * 	-Seleccionar los posibles entrenemientos y actualizar el jugador las semanas suficientes para aplicar la mejora de cada entrenamiento
	 * 
	 * 	-Comprobar ciertos entrenamientos variables dependiendo de los Empleados del Equipo
	 * 		Head Coach: Jugador puede entrenar posiciones nuevas, las cuales deberan aparecer en la lista de posiciones del jugador despues de las semanas necesarias.
	 * 		Assistan Manager: Jugador tarda una semana menos en entrenar ABL
	 * 
	 * 	-Comprobar las actualizaciones de Forma dependiendo de goles y asistencias en ultimos partidos.
	 */
	
	@Before
	public void setup()
	{
		t1 = new PlayerControlledTeamImpl("Prueba", new ShowTeamImpl());
		pTeam = t1.getPlayersInTeam().findPlayer(1);
		p1 = new PlayerImpl("Diligan",16, 5.3, FormTypes.PLUS_PLUS, PositionTypes.SB);
	}
	
	@After
	public void cleanup()
	{
		p1 = null;
		t1 = null;
		pTeam = null;
	}
	
	@Test(expected=PlayerIllegalAgeException.class)
	public void testAnyoErroneo()
	{
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl("Diligan",14, 5.3, FormTypes.PLUS_PLUS, PositionTypes.GK);
	}
	
	@Test(expected=PlayerIllegalAgeException.class)
	public void testAnyoErroneo_STRING()
	{
		String linea = "Diligan 14 5.3 PLUS_PLUS 43 GK";
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl(linea);
	}

	@Test(expected=PlayerIllegalAbilityException.class)
	public void testHabilidadMINErronea()
	{
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl("Diligan",16, 4.3, FormTypes.PLUS_PLUS, PositionTypes.GK);
	}
	
	@Test(expected=PlayerIllegalAbilityException.class)
	public void testHabilidadMINErronea_STRING()
	{
		String linea = "Diligan 16 4.3 PLUS_PLUS 43 GK";
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl(linea);
	}

	@Test(expected=PlayerIllegalAbilityException.class)
	public void testHabilidadMAXErronea()
	{
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl("Diligan",16, 10.3, FormTypes.PLUS_PLUS, PositionTypes.GK);
	}
	
	@Test(expected=PlayerIllegalAbilityException.class)
	public void testHabilidadMAXErronea_STRING()
	{
		String linea = "Diligan 16 10.3 PLUS_PLUS 43 GK";
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl(linea);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFormErronea_STRING()
	{
		String linea = "Diligan 16 5.0 MAS_MAS 43 GK";
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl(linea);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPosErronea_STRING()
	{
		String linea = "Diligan 16 5.0 PLUS_PLUS 43 PORTERO";
		@SuppressWarnings("unused")
		Player playerPrueba = new PlayerImpl(linea);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testSetNumberWithoutTeam()
	{
		p1.setNumber(2);
	}
	
	@Test
	public void testSetSameNumber()
	{
		Integer newNumber = 100; //No llegaremos a tener 100 jugadores :D 
		
		pTeam.setNumber(newNumber);
		
		assertTrue(pTeam.getNumber() == newNumber);
	}
	
	@Test
	public void testSetDifferentNumber()
	{
		pTeam.setNumber(2);
		
		assertTrue(pTeam.getNumber() != 2);
	}
	
	@Test
	public void testSetInjured()
	{
		boolean preInjured = pTeam.isInjured();

		pTeam.setActualTraining(TrainingTypes.ABL);
		pTeam.setInjured(3);
		
		assertTrue(!preInjured && pTeam.isInjured());
	}
	
	@Test(expected=PlayerInjuredIllegalChangeOfTraining.class)
	public void testSetTrainingInjured()
	{
		pTeam.setActualTraining(TrainingTypes.ABL);
		pTeam.setInjured(3);
		pTeam.setActualTraining(TrainingTypes.ABL);
	}
	
	@Test(expected=PlayerTeamNotHaveHeadCoach.class) //Se podria quitar la excepcion devuelta
	public void testSetTrainingPositionWithoutHeadCoach()
	{
		pTeam.setInjured(3);
		pTeam.setActualTraining(TrainingTypes.CB);
	}	
	
	@Test
	public void testAddPosition1()
	{		
		List<PositionTypes> prePositions = new LinkedList<PositionTypes>();
		prePositions.addAll(pTeam.getPositions());
			
		pTeam.addPosition(PositionTypes.SM);
		
		assertTrue(pTeam.getPositions().contains(PositionTypes.SM) && !prePositions.contains(PositionTypes.SM));
	}
	
	@Test
	public void testAddPosition2()
	{
		List<PositionTypes> prePositions = new LinkedList<PositionTypes>();
		prePositions.addAll(pTeam.getPositions());
		
		List<PositionTypes> positions = new LinkedList<PositionTypes>();
		
		positions.add(PositionTypes.SB);
		positions.add(PositionTypes.CB);
		positions.add(PositionTypes.CF);
		positions.add(PositionTypes.CM);
		
		pTeam.addPosition(positions);

		boolean cond1 = pTeam.getPositions().containsAll(positions);
		boolean cond2 = !prePositions.containsAll(positions);
		
		assertTrue(cond1 && cond2);
	}
	
	@Test
	public void testInjured()
	{
		pTeam.setActualTraining(TrainingTypes.ABL);
		pTeam.setInjured(4);
		
		for(int i = 1; i <= 3; i++)
			pTeam.updateWeeklyPlayer(0, 0, 0);
		
		assertTrue(pTeam.isInjured());
	}
	
	@Test
	public void testTrainingRSTAndNotInjured()
	{		
		pTeam.setActualTraining(TrainingTypes.RST);
		
		pTeam.setInjured(4);
		
		assertTrue(!pTeam.isInjured());
	}
	
	
	
	
	@Test
	public void testForm_CF_NoGoalsIn3Games()
	{		
		Player playerTest = t1.getPlayersInTeam().findPlayer(PositionTypes.CF).get(0);
		
		FormTypes preForm = playerTest.getForm();
		
		for(int i = 1; i <= 3; i++)
			playerTest.updateWeeklyPlayer(0, 0, 0);
		
		playerTest.updateForm(1);
		
		assertTrue(playerTest.getForm().equals(preForm));		
	}
	
	@Test
	public void testFormHattrick()
	{
		Player playerTest = t1.getPlayersInTeam().findPlayer(PositionTypes.CF).get(0);
		
		FormTypes preForm = playerTest.getForm();
		
		playerTest.updateWeeklyPlayer(3, 0, 0);
		
		playerTest.updateForm(-1);
		
		assertTrue(playerTest.getForm().equals(preForm));
	}
	
	@Test
	public void testFormScoreAtLeast1GoalIn3Games()
	{
		Player playerTest = t1.getPlayersInTeam().findPlayer(PositionTypes.CF).get(0);
		
		FormTypes preForm = playerTest.getForm();
		
		for(int i = 1; i <= 3; i++)
			playerTest.updateWeeklyPlayer(1, 0, 0);
		
		playerTest.updateForm(-1);
		
		assertTrue(playerTest.getForm().equals(preForm));		
	}
	
	@Test
	public void testForm_GK_CB_SB_Concede4GoalsIn1Game()
	{		
		Player playerTest = t1.getPlayersInTeam().findPlayer(PositionTypes.GK).get(0);
		Player playerTest2 = t1.getPlayersInTeam().findPlayer(PositionTypes.CB).get(0);
		Player playerTest3 = t1.getPlayersInTeam().findPlayer(PositionTypes.SB).get(0);
		
		FormTypes preForm = playerTest.getForm();
		FormTypes preForm2 = playerTest2.getForm();
		FormTypes preForm3 = playerTest3.getForm();
		
		playerTest.updateWeeklyPlayer(0, 4, 0);
		playerTest2.updateWeeklyPlayer(0, 4, 0);
		playerTest3.updateWeeklyPlayer(0, 4, 0);
		
		playerTest.updateForm(1);
		playerTest2.updateForm(1);
		playerTest3.updateForm(1);
		
		assertTrue(playerTest.getForm().equals(preForm) && playerTest2.getForm().equals(preForm2) && playerTest3.getForm().equals(preForm3));
	}
	
	@Test
	public void testForm_GK_CB_SB_ConcedeFewerThan4in3()
	{
		Player playerTest = t1.getPlayersInTeam().findPlayer(PositionTypes.GK).get(0);
		Player playerTest2 = t1.getPlayersInTeam().findPlayer(PositionTypes.CB).get(0);
		Player playerTest3 = t1.getPlayersInTeam().findPlayer(PositionTypes.SB).get(0);
	
		FormTypes preForm = playerTest.getForm();
		FormTypes preForm2 = playerTest2.getForm();
		FormTypes preForm3 = playerTest3.getForm();

		playerTest.updateWeeklyPlayer(0, 2, 0);
		playerTest.updateWeeklyPlayer(0, 1, 0);
		playerTest.updateWeeklyPlayer(0, 0, 0);
		
		playerTest2.updateWeeklyPlayer(0, 2, 0);
		playerTest2.updateWeeklyPlayer(0, 1, 0);
		playerTest2.updateWeeklyPlayer(0, 0, 0);
		
		playerTest3.updateWeeklyPlayer(0, 0, 0);
		playerTest3.updateWeeklyPlayer(0, 1, 0);
		playerTest3.updateWeeklyPlayer(0, 2, 0);
		
		playerTest.updateForm(-1);
		playerTest2.updateForm(-1);
		playerTest3.updateForm(-1);
		
		assertTrue(playerTest.getForm().equals(preForm) && playerTest2.getForm().equals(preForm2) && playerTest3.getForm().equals(preForm3));
	}
	
	@Test
	public void testForm_CM_SM_NoAssistsOrGoalsIn3Games()
	{
		
	}
	
	@Test
	public void testFormMake3AssistsIn3Games()
	{
		
	}
}