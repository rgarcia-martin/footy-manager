package interface_user;

import player.Player;
import player.TrainingTypes;
import division.Division;
import employer.EmployerTypes;
import match.Match;
import team.PlayerControlledTeam;

public interface InterfaceUser {

	public String readKeyboard();

	public void write(Object o);

	public void pressEnter();

	public Integer getPlayers(Integer max);

	public Integer getNumAgesForSponsor(Integer max);

	public String getNameTeam(Integer i);

	public void turn(PlayerControlledTeam t);
	
	public void endSeason();
	
	public boolean yesOrNot();

	public void menu(PlayerControlledTeam playerControlledTeam, Integer s, Integer w);

	public Integer getChoice(Integer min, Integer max, Integer quit);

	public void showMatch(Match mat);

	public void showDivisions(Division divisions);

	public void menuTraining(PlayerControlledTeam playerControlledTeam);

	public void menuTrainingPlayer(Player p);

	public TrainingTypes getTrainingType(Player p);

	public void menuTrainers(PlayerControlledTeam playerControlledTeam);

	public EmployerTypes getEmployerType();
}