package team;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


import match.Match;
import match.StadisticsTeam;

import division.ClassificationData;
import division.DivisionType;

import employer.*;

import player.*;
import sponsor.SponsorAgreement;
import stadium.ShowStadiumImpl;
import stadium.Stadium;
import stadium.StadiumImpl;

//Imposible partir esta clase en dos, los metodos se necesitan entre ellos y dentro de la clase
//aun asi se ha intentado
/**
 * @author  Gabriel
 */
public class PlayerControlledTeamImpl extends TeamImpl implements PlayerControlledTeam {
	
	private Double finances;
	
	private final Players playersInTeam;
	
	private final Employers employers;
	
	private Stadium stadium;
	
	private SponsorAgreement sponsorAgreement;
	
	private FactoryPlayer fp = new FactoryPlayerImpl();
	
	private final ShowTeam showTeam;
	
	private static final Logger log= Logger.getLogger(PlayerControlledTeamImpl.class);

	
	public PlayerControlledTeamImpl(String name, ShowTeam showT){ 
		super(name);
		
		sponsorAgreement = null;
		finances=0.0;
		playersInTeam = new PlayersImpl();
		employers = new EmployersImpl();
		stadium= new StadiumImpl(this, new ShowStadiumImpl());

		createTeam();
		
		this.showTeam= showT;
		/**< los manager empiezan en la ultima division para ir ascendiendo, en la ultima posicion */
	}
	

	public PlayerControlledTeamImpl(String name, Integer rank, ShowTeam showT){ 
		super(name, rank);
		
		finances=0.0;
		playersInTeam = new PlayersImpl();
		employers = new EmployersImpl();
		stadium= new StadiumImpl(this, new ShowStadiumImpl());

		createTeam();
		
		this.showTeam= showT;	
	}
	
	private void createTeam(){			// refactorizacion codigo repetido en ambos constructores
		for(int i = 1; i <= 16; i++)
		{
			Player p = fp.getNewPlayer(this);
	
			List<PositionTypes> positionsPlayer = new LinkedList<PositionTypes>();
			
			if(i <= 2){
				positionsPlayer.add(PositionTypes.GK);
				p.setPositions(positionsPlayer);				
			}
			else	if(i > 2 && i <= 5){
				positionsPlayer.add(PositionTypes.CB);
				p.setPositions(positionsPlayer);				
			}
			else	if(i > 5 && i <= 8){
				positionsPlayer.add(PositionTypes.SB);
				p.setPositions(positionsPlayer);				
			}
			else	if(i > 8 && i <= 11){
				positionsPlayer.add(PositionTypes.CM);
				p.setPositions(positionsPlayer);				
			}
			else	if(i > 11 && i <= 13){
				positionsPlayer.add(PositionTypes.SM);
				p.setPositions(positionsPlayer);				
			}
			else	if(i > 13 && i <= 16){
				positionsPlayer.add(PositionTypes.CF);
				p.setPositions(positionsPlayer);				
			}
			
			p.setNumber(i);

			playersInTeam.addPlayer(p);		
		}
		
		//setClassificationData(new ClassificationDataImpl(this, new DivisionImpl(DivisionType.FOURTH_DIVISION)));
	}	

	public Double getFinances() {	
		return finances;
	}


	public void buyPlayer(Player player, Double price) {

		playersInTeam.addPlayer(player);
		addWaste(price);
	}

	public void sellPlayer(Player player, Double price) {
	
		if(!(playersInTeam.getPlayers().contains(player))){
			log.error("El jugador no pertece a este equipo");
		}
		
		playersInTeam.remove(player);
		addIncome(price);
	}


	public Players getPlayersInTeam(){
		return playersInTeam;
	}

	
	public SponsorAgreement getSponsorAgreement(){
		return sponsorAgreement;
	}
	
	
	public void setSponsorAgreement(SponsorAgreement spAgreement){
		this.sponsorAgreement= spAgreement;
	}
	
	public Stadium getStadium(){
		return stadium;
	}
	
	public void setStadium(Stadium st){
		this.stadium= st;
	}

	@Override
	public boolean getStreak() {
		List<Match> matchs=this.getMatchs();
		boolean streak=true;
		Map<Player, Integer> goals;
		Map<Player, Integer> goalsConceded;
		Integer g=0;
		Integer gc=0;
		for(int i=1;i<4;i++){
			StadisticsTeam myStatistics = matchs.get(matchs.size()-i).getMyStatistics((Team) this);
			
			goals=myStatistics.getGoals();
			goalsConceded=myStatistics.getGoalsConceded();
		
			for(Entry<Player,Integer> player: goals.entrySet()){	//refactorizacion eficencia mejor entryset a keyset
				g+=goals.get(player.getValue());
			}
			for(Entry<Player,Integer> player: goalsConceded.entrySet()){
				gc+=goalsConceded.get(player.getValue());
			}
			streak=streak && g>gc;
		}
		return streak;
	}

	@Override
	public boolean lastYearChampions() {
		List<ClassificationData> teams= getDivision().getTeams();
		return this.getName().equals(teams.get(0).getTeam().getName()) 
				&& getDivision().getCategory().equals(DivisionType.PREMIER_DIVISION);
	}

	@Override
	public boolean runnerUp() {
		return getDivision().getTop().contains(this);
	}

	@Override
	public boolean keepDivision() {
		return !(getDivision().getBottom().contains(this) && getDivision().getTop().contains(this));
	}

	@Override
	public boolean winLastSeason() {
		List<ClassificationData> teams= getDivision().getTeams();
		return this.getName().equals(teams.get(0).getTeam().getName());
	}

	@Override
	public boolean relegated() {
		List<ClassificationData> bottom =getDivision().getBottom();
		boolean relegated=false;
		for(ClassificationData data: bottom){
			
			if(this.equals(data.getTeam())){
				
				relegated=true;
				break;
			}
		}
		return relegated;
	}

	@Override
	public void hireEmployer(EmployerTypes typeEmployer) {
		employers.addEmployer(new EmployerImpl(typeEmployer));
	}

	@Override
	public void fireEmployer(EmployerTypes typeEmployer) {
		employers.removeEmployer(new EmployerImpl(typeEmployer));
	}

	@Override
	public Employers getEmployersOfTeam() {
		return employers;
	}
	
	//TODO Te he implementado el metodo con la variable que parece que es
	//la que hace referencia
	public void setFinancesCost(Double money){
		finances-=money;
	}
	
	public void addIncome(Double income){
		
		finances+= income;
		showTeam.income(income, finances);
	}
	
	public void addWaste(Double waste){
		
		if(finances>=waste){
			
			setFinancesCost(waste);
			showTeam.waste(waste,finances);
		}
		
		else
			showTeam.noMoney();
			
	}
	
}
