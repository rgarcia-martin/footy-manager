package division;

import java.util.List;

import team.Team;

/**
 * @author   Zouhair
 * @biref Inteferfaz Division, que la implementa DivisionImpl
 */
public interface Division {

	public abstract void fillDivision(List<Team> teams);

	
	public abstract DivisionType getCategory();

	
	public abstract List<ClassificationData> getTeams();

	
	public abstract void updateWeeklyDivision();


	public abstract List<ClassificationData> getTop();

	
	public abstract List<ClassificationData> getBottom();


	public abstract void setTop(List<ClassificationData> top);

	
	public abstract void setBottom(List<ClassificationData> bottom);
	
	

}