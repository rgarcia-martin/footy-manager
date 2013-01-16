package team;

import java.util.LinkedList;
import java.util.List;

import division.ClassificationData;
import division.Division;

import match.Match;


/**
 * @Class  clase Team: Team 
 * @author  Zouhair
 * @brief Clase equipo,
 */
public abstract class TeamImpl implements Team{

	private final String name;
	private Integer ranking;
	private final List<Match> matchs;
	private ClassificationData classificationData;
	
	private final int prime = 31;
	
	public TeamImpl(String nam, Integer rank){
		name = nam;
		ranking = rank;
		matchs = new LinkedList<Match>();
	}
	
	public TeamImpl(String nam){
		name = nam;
		matchs = new LinkedList<Match>();
	}
	
	public void addMatch(Match m){
		
		matchs.add(m);
		
	}
	public List<Match> getMatchs(){
		return matchs;
	}
	
	public Match getLastMatch(){
		Match res = null;
		
		if(matchs.size() > 0)
			res = matchs.get(matchs.size() - 1);
		
		return res;
	}
	
	public String getName(){
		return name;
	}
	
	public Integer getRanking(){
		return ranking;
	}
	
	public void setRanking(Integer rank){
		ranking = rank;
	}

	@Override
	public int hashCode() {
		
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ranking == null) ? 0 : ranking.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamImpl other = (TeamImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ranking == null) {
			if (other.ranking != null)
				return false;
		} else if (!ranking.equals(other.ranking))
			return false;
		return true;
	}
	
	public void setClassificationData(ClassificationData cd){
		this.classificationData = cd;
	}
	
	public Division getDivision(){
		return classificationData.getDivision();
	}	
}
