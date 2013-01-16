package division;

import match.Match;
import team.Team;

/**
 * @author Zouhair
 * @class ClassificationDataImpl
 * @brief Datos de la Clsificacion
 */
public class ClassificationDataImpl implements ClassificationData {

	/**
	 * @uml.property  name="team"
	 * @uml.associationEnd  
	 */
	private final Team team;
	
	private Integer win;
	private Integer drw;
	private Integer lose;
	private Integer For;
	private Integer ag;
	/**
	 * @uml.property  name="points"
	 */
	private Integer points;
	
	/**
	 * @uml.property  name="division"
	 * @uml.associationEnd  
	 */
	private final Division division;
	
	public ClassificationDataImpl(Team t, Division d)
	{
		team = t;
		division = d;
		
		win = 0;
		drw = 0;
		lose = 0;
		For = 0;
		ag = 0;
		points = 0;
	}
	

	@Override
	public Team getTeam(){
		return team;
		/**
		 * @return equipo
		 */
	}

	@Override
	public Integer getPoints(){
		return points;
		/**
		 * @return Puntos.
		 */
	}
	
	
	@Override
	public Division getDivision(){
		return division;
		/**
		 * @return la division en la se esta.
		 */
	}

	@Override
	public int compareTo(ClassificationData arg0) {
		return getPoints().compareTo(arg0.getPoints());
		/**
		 * @return la compracion de puntos en una division.
		 */
	}

	@Override
	public Integer getWins() {
		return win;
	
	}

	@Override
	public Integer getDraws() {
		return drw;
	}

	@Override
	public Integer getLoses() {
		return lose;
		/**
		 * @return Perdedor.
		 */
	}

	@Override
	public Integer getFors() {
		return For;
	}

	@Override
	public Integer getAgs() {
		return ag;
	}

	@Override
	public void updateStats() 
		/**
		 * @brief Actualiza el estado 
		 * 
		 */
		{
		Match lastMatch = team.getLastMatch();

		Integer goalsFor = lastMatch.getMyGoals(team);
		Integer goalsAg = lastMatch.getMyGoalsConceded(team);
		
		if(goalsFor > goalsAg){				win++; 	points += 3;}
		else if(goalsFor.equals(goalsAg)){		drw++; 	points += 1;}
		else if(goalsFor < goalsAg)			lose++;
		
		For += goalsFor;
		ag += goalsAg;
	}
	
	public void resetStats(){		
		win = 0;
		drw = 0;
		lose = 0;
		For = 0;
		ag = 0;
		points = 0;		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((division == null) ? 0 : division.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
		ClassificationDataImpl other = (ClassificationDataImpl) obj;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	public String toString(){
		return team.getRanking() + " " + team.getName();
	}
}
