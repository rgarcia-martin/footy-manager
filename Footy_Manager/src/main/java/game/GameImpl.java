package game;

import interface_user.*;

import java.util.LinkedList;
import java.util.List;

import average.AverageLocation;

import match.Formation;
import match.Match;
import match.MatchImpl;
import match.Squad;
import match.SquadImpl;
import match.StadisticsTeam;
import player.Player;
import player.Players;
import player.PlayersImpl;
import player.TrainingTypes;

import division.*;
import employer.Employer;
import employer.EmployerTypes;

import selection.Selection;
import selection.SelectionImpl;
import sponsor.*;

import team.FactoryTeam;
import team.FactoryTeamImpl;
import team.PlayerControlledTeam;
import team.Team;

public class GameImpl implements Game{

	private final List<Division> divisions = new LinkedList<Division>();
	private final List<PlayerControlledTeam> players = new LinkedList<PlayerControlledTeam>();
	private final List<Sponsor> sponsors = new LinkedList<Sponsor>();
	private final InterfaceUser iu = new InterfaceUserImpl();

	private final FactoryTeam factoryT = new FactoryTeamImpl();
	private final FactoryDivision factoryD = new FactoryDivisionImpl();
	private final FactorySponsor factoryS = new FactorySponsorImpl();

	private static final Integer MAX_PLAYERS = 8;
	private static final Integer MAX_TEAMS = 32;
	private static final Integer NUM_DIVISIONS = 4;
	private static final Integer MAX_TEMP_SPONSOR = 2;
	private static final Integer MATCHS_FOR_SEASON = 14;

	private Integer week;
	private Integer season;
	private Fixtures fixture;
	
	public GameImpl(){
		week = -1;
		season = 1;
	}
	
	@Override
	public void start() {
		Integer numPlayers;
		boolean wantPlay = true;
		
		//1 Bienvenida
		welcome();
		
		//2 Eleccion del numero de Jugadores
		numPlayers = choosePlayers();
		fillPlayers(numPlayers);
		
		//3 Rellenar divisiones
		fillDivisions(numPlayers);
		
		//4 Sponsor por equipo
		fillSponsors();
		
		for(int i = 0; i < numPlayers; i++)
		{
			chooseSponsor(players.get(i));
		}
				
		//Rellenamos los emparejamientos para la temporada
		fillMatches();
		
		//Bucle Por temporada
		while(wantPlay)
		{
			//5 Pre temporada (Para mas alla de la primera temporada)
			if(season > 1)
				preSeason();
			
			for(int i = 0; i < MATCHS_FOR_SEASON; i++)
			{
				week++;		
			
				//6 Menu de Juego para cada Jugador
				for(int j = 0; j < numPlayers; j++)
				{
					menu(players.get(j));
				}
			
				//7 Jugar Partidos de cada Division
				playMatchs(week % MATCHS_FOR_SEASON);
				
				//8 Ordenar las Divisiones
				orderDivisions();
				
				//9 Actualizar cada Jugador
				for(int j = 0; j < numPlayers; j++)
				{
					updateTeam(players.get(j));
				}

				updateTransferList();
			}		
		
			//9 Fin de Temporada
			wantPlay = endSeason();
		}
	}

	private void welcome() {
		String msg = null;
		
		msg =  "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                           ++\n";
		msg += "++       Bienvenidos a FOOTY MANAGER         ++\n";
		msg += "++                                           ++\n";
		msg += "++              ISG2    Grupo 1              ++\n";
		msg += "++                                           ++\n";
		msg += "++                 I.T.I.G                   ++\n";
		msg += "++                                           ++\n";
		msg += "++    Creadores:                             ++\n";
		msg += "++       Zouhair Chaouni                     ++\n";
		msg += "++       Pedro Carlos Cantalapiedra          ++\n";
		msg += "++       Juan Garrido                        ++\n";
		msg += "++       Gabriel Hidalgo                     ++\n";
		msg += "++       Alberto Mallofret                   ++\n";
		msg += "++       Jesus Reyes                         ++\n";
		msg += "++       Rafael Garcia                       ++\n";
		msg += "++                                           ++\n";
		msg += "++    Grupo 2                                ++\n";
		msg += "++                                           ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		
		iu.write(msg);
		iu.pressEnter();
	}

	private Integer choosePlayers() {
		Integer res;
		
		res = iu.getPlayers(MAX_PLAYERS);
		
		iu.write("");
		
		return res;
	}

	private void fillPlayers(Integer numPlayers) {
		for(int i = 0; i < numPlayers; i++)
		{
			players.add(factoryT.createNewPCT());
	
			iu.write("");
		}
	}

	private void fillDivisions(Integer numPlayers) {
		for(int i = 1; i <= NUM_DIVISIONS; i++)
		{
			DivisionType dt = null;
			
					if(i == 1){	dt = DivisionType.PREMIER_DIVISION;	}
			else	if(i == 2){	dt = DivisionType.SECOND_DIVISION;	}
			else	if(i == 3){	dt = DivisionType.THIRD_DIVISION;	}
			else	if(i == 4){	dt = DivisionType.FOURTH_DIVISION;	}
			
			Integer numNPCT = MAX_TEAMS - numPlayers;
			Double num = ((double) numNPCT / (double) DivisionImpl.TEAMS_PER_DIVISION);
			boolean preg = i <= (num);
			
			if(preg)
			{
				divisions.add(factoryD.createNewDivisionWithoutPlayers(dt));
			}
			else
			{
				divisions.add(factoryD.createNewDivisionWithPlayers(dt,players));
			}
		}
	}

	private void fillSponsors() {
		Sponsor s;
		
		while((s = factoryS.getSponsor()) != null)
		{
			sponsors.add(s);
		}
	}

	private void chooseSponsor(PlayerControlledTeam playerControlledTeam) {
		iu.turn(playerControlledTeam);
		iu.write("Eleccion de Sponsor");
		
		SponsorValue sv = new SponsorValueImpl();
		
		Integer value = sv.calcValue(playerControlledTeam);
		
		Sponsor s = sponsors.get(value);
		
		iu.write("Le ha tocado el siguiente sponsor " + s.getName());
		iu.write("Dinero:");
		iu.write("1 Año:  " + s.getOneSeasonCash());
		iu.write("2 Años: " + s.getTwoSeasonsCash());
		
		Integer temp = iu.getNumAgesForSponsor(MAX_TEMP_SPONSOR);
		
		SponsorAgreement sa = new SponsorAgreementImpl(s, temp);
		
		playerControlledTeam.setSponsorAgreement(sa);
	
		iu.write("");
		
		//Cobrar sponsor
		Double inc;
		
		if(playerControlledTeam.getSponsorAgreement().getDuration() == 1)
		{
			inc = playerControlledTeam.getSponsorAgreement().getSponsor().getOneSeasonCash();
		}
		else
		{
			inc = playerControlledTeam.getSponsorAgreement().getSponsor().getTwoSeasonsCash();
		}
		
		playerControlledTeam.addIncome(inc);
	}

	private void fillMatches() {
		fixture = new FixturesImpl(divisions);
	}

	private void preSeason() {
		//Mover equipos en las divisiones
		List<Team> lt1 = getNewTeams(divisions.get(0), 				null, divisions.get(1));
		List<Team> lt2 = getNewTeams(divisions.get(1), 	divisions.get(0), divisions.get(2));
		List<Team> lt3 = getNewTeams(divisions.get(2), 	divisions.get(1), divisions.get(3));
		List<Team> lt4 = getNewTeams(divisions.get(3), 	divisions.get(2), null);

		divisions.set(0, new DivisionImpl(DivisionType.PREMIER_DIVISION));
		divisions.set(1, new DivisionImpl(DivisionType.SECOND_DIVISION));
		divisions.set(2, new DivisionImpl(DivisionType.THIRD_DIVISION));
		divisions.set(3, new DivisionImpl(DivisionType.FOURTH_DIVISION));
		
		divisions.get(0).fillDivision(lt1);
		divisions.get(1).fillDivision(lt2);
		divisions.get(2).fillDivision(lt3);
		divisions.get(3).fillDivision(lt4);

		iu.showDivisions(divisions.get(0));
		iu.showDivisions(divisions.get(1));
		iu.showDivisions(divisions.get(2));
		iu.showDivisions(divisions.get(3));
				
		//Rellenar los nuevos matches para esta temporada		
		fillMatches();
		
		//Comprobar Sponsor
		for(int i = 0; i < players.size(); i++)
		{
			PlayerControlledTeam playerControlledTeam = players.get(i);
			
			Integer tempAgreement = playerControlledTeam.getSponsorAgreement().getTemp();
			Integer duraAgreement = playerControlledTeam.getSponsorAgreement().getDuration();
			
			if(tempAgreement >= duraAgreement)
			{
				iu.write("Agotado el Contrato con el Sponsor, volviendo a Seleccionar uno");
				chooseSponsor(playerControlledTeam);
				iu.write("");
			}
		}
	}
	
	private List<Team> getNewTeams(Division d, Division sup, Division inf)
	{
		List<Team> l = new LinkedList<Team>();
		
		//Descendientes de divisiones superiores
		if(sup != null)
		{
			l.add(sup.getBottom().get(0).getTeam());
			l.add(sup.getBottom().get(1).getTeam());
		}
		else
		{
			l.add(d.getTeams().get(0).getTeam());
			l.add(d.getTeams().get(1).getTeam());
		}

		l.add(d.getTeams().get(2).getTeam());
		l.add(d.getTeams().get(3).getTeam());
		l.add(d.getTeams().get(4).getTeam());
		l.add(d.getTeams().get(5).getTeam());
		
		if(inf != null)
		{
			l.add(inf.getTop().get(0).getTeam());
			l.add(inf.getTop().get(1).getTeam());
		}
		else
		{
			l.add(d.getTeams().get(6).getTeam());
			l.add(d.getTeams().get(7).getTeam());
		}
		
		return l;
	}

	private void menu(PlayerControlledTeam playerControlledTeam) {
		iu.menu(playerControlledTeam, season, (week % MATCHS_FOR_SEASON) + 1);
		Integer choice = iu.getChoice(1, 5, 5);
		
		while(choice != -1)
		{
					if(choice > 0 && choice <= 1){	training(playerControlledTeam); }//Opcion 1
			else 	if(choice > 1 && choice <= 2){	trainers(playerControlledTeam); }//Opcion 2
			else 	if(choice > 2 && choice <= 3){	transferList(playerControlledTeam); }//Opcion 3
			else 	if(choice > 3 && choice <= 4){	otherOptions(playerControlledTeam); }//Opcion 4
					
			iu.menu(playerControlledTeam, season, (week % MATCHS_FOR_SEASON) + 1);
			choice = iu.getChoice(1, 5, 5);
		}
	}

	private void playMatchs(Integer w) {		
		for(Division d: divisions)
		{
			iu.write("Division " + d.getCategory());
			
			MatchesInSeason mIs = fixture.getMatchesSeasonForDivision(d.getCategory());
			
			List<Team> m = mIs.getMatchesForWeek(w);
			
			for(int i = 0; i < 4; i++)
			{
				Integer posHome = i * 2;
				Integer posAway = posHome + 1;

				Team home = m.get(posHome);
				Team away = m.get(posAway);
				
				Match mat = null;
				
				if(home instanceof PlayerControlledTeam && away instanceof PlayerControlledTeam){
					//PvP
					Players playersHome = new PlayersImpl();
					Players playersAway = new PlayersImpl();

					Formation formHome;
					Formation formAway;

					Selection selHome = new SelectionImpl((PlayerControlledTeam) home);
					Selection selAway = new SelectionImpl((PlayerControlledTeam) away);

					iu.write("\tProximo partido de " + home.getName());
					selHome.setFormation();
					
					iu.write("\tProximo partido de " + away.getName());
					selAway.setFormation();
					
					playersHome.addAll(selHome.getSelection().getPlayers());
					playersAway.addAll(selAway.getSelection().getPlayers());

					formHome = selHome.getFormation();
					formAway = selAway.getFormation();
					
					Squad s1 = new SquadImpl(playersHome, formHome);
					Squad s2 = new SquadImpl(playersAway, formAway);
					
					mat = new MatchImpl(home, s1, away, s2);
				}
				else if(home instanceof PlayerControlledTeam || away instanceof PlayerControlledTeam){
					//MvP
					Team machine;
					Team player;
					AverageLocation loc;
					
					if(home instanceof PlayerControlledTeam){
						player = home;
						machine = away;
						loc = AverageLocation.AWAY;
					}
					else
					{
						player = away;
						machine = home;
						loc = AverageLocation.HOME;						
					}
					
					Players playersP = new PlayersImpl();

					Formation formP;
					
					Selection selP = new SelectionImpl((PlayerControlledTeam) player);
					
					iu.write("\tProximo partido de " + player.getName());
					selP.setFormation();
					
					playersP.addAll(selP.getSelection().getPlayers());

					formP = selP.getFormation();					

					Squad sP = new SquadImpl(playersP, formP);
					
					mat = new MatchImpl(machine, loc, player, sP);
				}
				else{
					//MvM
					mat = new MatchImpl(home, away);
				}

				home.addMatch(mat);
				away.addMatch(mat);
				
				mat.playMatch();
				iu.write("");
				
				iu.showMatch(mat);
				iu.write("");
			}
		}
		
		iu.pressEnter();
	}
	
	private void updateTransferList() {
		// TODO Auto-generated method stub
		
	}

	private void orderDivisions() {
		for(Division d: divisions)
		{
			d.updateWeeklyDivision();
			iu.showDivisions(d);
			iu.write("");
		}
		
		iu.pressEnter();		
	}

	private void updateTeam(PlayerControlledTeam playerControlledTeam) {
		updatePlayers(playerControlledTeam);
		
		getNewFinances(playerControlledTeam);
	}

	private void updatePlayers(PlayerControlledTeam playerControlledTeam) {
		Match m = playerControlledTeam.getLastMatch();
		
		List<Player> lp = playerControlledTeam.getPlayersInTeam().getPlayers();
		
		StadisticsTeam tm = m.getMyStatistics(playerControlledTeam);
		
		for(Player p : lp)
		{			
			Integer g = tm.getGoals().get(p);
			Integer gc = tm.getGoalsConceded().get(p);
			Integer a = tm.getAssistances().get(p);
			
			if(g == null)
			{
				g = 0;
			}
			
			if(gc == null)
			{
				gc = 0;
			}
			
			if(a == null)
			{
				a = 0;
			}
			
			p.updateWeeklyPlayer(g, gc, a);
		}		
	}

	private void getNewFinances(PlayerControlledTeam playerControlledTeam) {
		//Mirar si el ultimo partido se jugo en Home
		Match m = playerControlledTeam.getLastMatch();
		if(m.playingAtHome(playerControlledTeam))
		{
			playerControlledTeam.addIncome(playerControlledTeam.getStadium().getBenifit());
		}
		
		//Cobrar los empleados
		Double cost = 0.0;
		
		for(Employer e : playerControlledTeam.getEmployersOfTeam().getEmployers())
		{
			cost += e.getWage();
		}
		
		playerControlledTeam.addWaste(cost);
	}

	private boolean endSeason() {
		boolean res = false;
		
		iu.endSeason();
		
		res = iu.yesOrNot();
		
		if(res)
			season++;
		
		return res;		
	}

	private void training(PlayerControlledTeam playerControlledTeam) {
		iu.menuTraining(playerControlledTeam);
		Integer choice = iu.getChoice(0, playerControlledTeam.getPlayersInTeam().getPlayers().size(), 0);
		
		while(choice != -1)
		{
			trainingPlayerMenu(playerControlledTeam, choice);
					
			iu.menuTraining(playerControlledTeam);
			choice = iu.getChoice(0, playerControlledTeam.getPlayersInTeam().getPlayers().size(), 0);
		}
	}

	private void trainingPlayerMenu(PlayerControlledTeam playerControlledTeam, Integer num_player) {
		Player p = playerControlledTeam.getPlayersInTeam().findPlayer(num_player);
		
		iu.menuTrainingPlayer(p);
		TrainingTypes t = iu.getTrainingType(p);
		
		if(t != null)
			p.setActualTraining(t);	
	}

	private void trainers(PlayerControlledTeam playerControlledTeam) {
		iu.menuTrainers(playerControlledTeam);
		EmployerTypes t = iu.getEmployerType();
		
		if(t != null)
		{
			if(playerControlledTeam.getEmployersOfTeam().existsEmployer(t))
			{
				playerControlledTeam.fireEmployer(t);
			}
			else
			{
				playerControlledTeam.hireEmployer(t);
				playerControlledTeam.addWaste(playerControlledTeam.getEmployersOfTeam().findEmployer(t).getWage());
			}
		}
	}

	private void transferList(PlayerControlledTeam playerControlledTeam) {
		// TODO Auto-generated method stub
		
	}

	private void otherOptions(PlayerControlledTeam playerControlledTeam) {
		// TODO Auto-generated method stub
		
	}

	public static Integer getNumWeeks() {
		return MATCHS_FOR_SEASON;
	}

}
