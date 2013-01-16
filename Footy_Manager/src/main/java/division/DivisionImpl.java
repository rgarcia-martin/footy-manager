package division;

import team.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import comparators.ComparatorTeamsByRanking;


/**
 * @author  Zouhair
 * @brief implementa Division donde aparece las primeras posiciones de cada division
 * 
 * 	Saca en cada de cada division el 1º y 2º, el ultimo y el penultimo.
 */
public class DivisionImpl implements Division{

	private final DivisionType category;

	private final List<ClassificationData> teams;
	public static final Integer TEAMS_PER_DIVISION = 8;
	
	private static final Integer POS_FIRST = 0;
	private static final Integer POS_SECOND = 1;
	private static final Integer POS_PRELAST = TEAMS_PER_DIVISION - 2;
	private static final Integer POS_LAST = TEAMS_PER_DIVISION - 1;
	private final int prime = 31;

	public DivisionImpl(DivisionType cat){
		category = cat;
		teams = new LinkedList<ClassificationData>();
		/**
		 *Se crea una lista que tendra la clasificacion de los equipos
		 */
	}
	

	@Override
	public void fillDivision(List<Team> team){
		if(team.size() != TEAMS_PER_DIVISION)
			throw new IllegalArgumentException("Numero Incorrecto de Equipos");
			/**
			 *Excepcion que saltara si el numero de equipos de la division es incorrecto
			 */
		
		for(Team t: team){
			ClassificationData cd = new ClassificationDataImpl(t, this);
			
			teams.add(cd);
			t.setClassificationData(cd);
		}
			
		Comparator<ClassificationData> c1 = new ComparatorTeamsByRanking();
		
		Collections.sort(teams, c1);
	}
	
	
	@Override
	public DivisionType getCategory() {
		return category;
	}
	
	
	@Override
	public List<ClassificationData> getTeams(){
		return teams;
	}

	
	@Override
	public void updateWeeklyDivision(){
		for(ClassificationData cd: this.teams)
			cd.updateStats();
		
		Collections.sort(this.teams);
		Collections.reverse(this.teams);
		/**
		 * método para Actualizar la division cada semana con la puntuacion que ha obtenido cada equipo.
		 */
	}
	
	@Override
	public List<ClassificationData> getTop(){
		List<ClassificationData> ret = new LinkedList<ClassificationData>();
		
		ret.add(this.teams.get(POS_FIRST));
		ret.add(this.teams.get(POS_SECOND));
		
		return ret;
		
		/**
		 *En la Lista ClasificationData se saca el 1º y 2º elemento.
		 * 
		 *  Serian el 1º y 2º para ver quien Asciende de Division en el caso de la 2º, 3º y 4º division y quien queda Campeon de la Liga en el caso de la 1º division.
		 * @return Devuelve el 1º y 2º elemento de la Lista.
		 */
	}
	
	@Override
	public List<ClassificationData> getBottom(){
		List<ClassificationData> ret = new LinkedList<ClassificationData>();
		
		ret.add(this.teams.get(POS_PRELAST));
		ret.add(this.teams.get(POS_LAST));
		
		return ret;
		/**
		 *Se saca de la lista ClassifacationData las 2 ultimas posiciones
		 * 
		 *  serian el 7º y 8º para ver quien Desciende en el caso de la division 1º, 2º y 3º.
		 * @return Devuelve las 2 ultimas posiciones de la lista. 
		 */
	}
	
	
	@Override
	public void setTop(List<ClassificationData> top){
		getTeams().set(POS_FIRST, top.get(POS_FIRST));
		getTeams().set(POS_SECOND, top.get(POS_SECOND));
	}
	
	@Override
	public void setBottom(List<ClassificationData> bottom){
		getTeams().set(POS_PRELAST, bottom.get(POS_FIRST));
		getTeams().set(POS_LAST, bottom.get(POS_SECOND));
	}

	@Override
	public int hashCode() {
		
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
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
		DivisionImpl other = (DivisionImpl) obj;
		if (category != other.category)
			return false;
		return true;
	}
	
	public String toString(){
		return category.toString() + " " + teams.toString();
	}
}