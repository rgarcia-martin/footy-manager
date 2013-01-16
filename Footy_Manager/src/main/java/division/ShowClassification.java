package division;

import java.util.List;
import java.util.Set;

import show.Show;
import team.Team;


/**
 * @author Alberto
 * @class ShowClassification
 * @brief Interface con los atributos que gestionan los System.out
 *  
 */
public interface ShowClassification extends Show{

	void classifications(List<Division> divisions);
	void weekClassifications(Set<Division> divisions);
	void fixtures(List<Team> matches, Integer week);
}
