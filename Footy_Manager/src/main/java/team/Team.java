package team;

import java.util.List;

import division.ClassificationData;
import division.Division;
import match.Match;
/**
 * @author Zouhair 
 * @brief la clase que Equipo con los nombre...
 *
 *	la clase equipo, que recojera el nombre del equipo, el Ranking, los partidos que juega, ultimo partido que ha perdido.......
 * 
 */
public interface Team {

	public abstract String getName();

	public abstract Integer getRanking();
	public abstract void setRanking(Integer r);
	
	public abstract List<Match> getMatchs();

	public abstract void addMatch(Match m);
	
	public abstract Match getLastMatch();
	
	Division getDivision();
	
	void setClassificationData(ClassificationData cd);
}	