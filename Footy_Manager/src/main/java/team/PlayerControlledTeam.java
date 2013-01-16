package team;

import division.ClassificationData;
import division.Division;
import employer.EmployerTypes;
import employer.Employers;

import player.Player;
import player.Players;
import sponsor.SponsorAgreement;
import stadium.Stadium;
/**
 * 
 * @author Gabirel
 * @brief Jugador Controlado por el manager.
 * 
 * 	El jugador que va a jugar el Juego.
 *
 */

public interface PlayerControlledTeam extends Team {
	
	Double getFinances();
	void buyPlayer(Player player, Double price);
	void sellPlayer(Player player, Double price);
	void hireEmployer(EmployerTypes typeEmployer);
	void fireEmployer(EmployerTypes typeEmployer);
	Players getPlayersInTeam();
	Employers getEmployersOfTeam();
	SponsorAgreement getSponsorAgreement();
	void setSponsorAgreement(SponsorAgreement sponsorAgreement);
	Stadium getStadium();
	void setStadium(Stadium stadium);
	boolean getStreak();
	boolean lastYearChampions();
	boolean runnerUp();
	boolean keepDivision();
	boolean winLastSeason();
	boolean relegated();
	void setFinancesCost(Double money);
	void addIncome(Double income);
	void addWaste(Double waste);
	Division getDivision();
	void setClassificationData(ClassificationData cd);
	 
}
