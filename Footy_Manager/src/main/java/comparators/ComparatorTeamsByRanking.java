package comparators;

import java.util.Comparator;
import division.ClassificationData;

/**
 * @author Zouhair
 * @class ComparatorTeamsByRanking 
 * @brief Compara Equipos por la clasificacion 
 */

public class ComparatorTeamsByRanking implements Comparator<ClassificationData>{

	@Override
	public int compare(ClassificationData arg0, ClassificationData arg1) {

		return arg0.getTeam().getRanking().compareTo(arg1.getTeam().getRanking());
		/**
		 * @return Comparacion de Equipo con el Ranking
		 */
	}
}