package division;

import java.util.List;
import java.util.Set;

import show.ShowImpl;
import team.Team;
/**
 * @author Alberto
 * @class ShowClassifitionImpl
 * @brief la clase que se encarga de gestionar todos los metodos System.out.
 * 
 *  que es donde aisla y gestion las salidas por pantalla.
 * 
 *
 */
public class ShowClassificationImpl extends ShowImpl implements ShowClassification{
	
	public void classifications(List<Division> divisions) {
		
		for(Division division: divisions){
			
			System.out.format("%s\n\n",division.getCategory());
			List<ClassificationData> divisionTeams= division.getTeams();
			
			for(int i=0;i<DivisionImpl.TEAMS_PER_DIVISION;i++){
				Team team= divisionTeams.get(i).getTeam();
				System.out.format("%d  %-20s Ranking: %d\n",i+1, team.getName(), team.getRanking());
			}
			blank();	
		}	
	}

	public void weekClassifications(Set<Division> divisions) {

		for(Division division: divisions){
			
			System.out.format("%s\n\n",division.getCategory());
			List<ClassificationData> divisionTeams= division.getTeams();
		
			for(int i=0;i<DivisionImpl.TEAMS_PER_DIVISION;i++){
				Team team= divisionTeams.get(i).getTeam();
				System.out.format("%d  %-20s Puntos: %d\n",i+1, team.getName(), divisionTeams.get(i).getPoints());
			}
			blank();	
		}
	}

	public void fixtures(List<Team> matches, Integer week) {

		System.out.format("Los emparejamientos de la jornada %d son:\n\n", week);
		
		for(int i=0; i<DivisionImpl.TEAMS_PER_DIVISION/2;i++){
		
			Team home=matches.get(i*2);
			Team away=matches.get(i*2+1);
			System.out.format("%s - %s\n", home.getName(), away.getName());
		}
	}
}