package team;

import java.util.List;

import employer.Employer;
import show.Show;
/**
 * 
 * @author Alberto
 * @brief 
 *
 */
public interface ShowTeam extends Show{

	void income(Double income, Double finances);
	void waste(Double waste, Double finances);
	void noMoney();
	void noEmployers();
	void employers(List<Employer> employers);
	void assistant();
	void chooseTraining();
}
