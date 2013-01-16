package division;

import team.Team;
/**
 * @class Interface ClasificationData
 * @author Zouhair
 * @brief Atributos: getTema get Wins, get Draw, getLoses, get Fors, getAgs, getPoint, getDivision, updateStats, resetStats.
 * 
 * getWins: Atributo para los Ganadores.
 * getDraws:
 * getLoses: Atributo para los perdedores.
 * getFors:
 * getAgs:
 * getPoints: Atributo para los puntos
 * getDivision: Atributo para la Division
 * updateStats: Actualizar Estado
 * resetStats : volver al principio del estado.
 */

public interface ClassificationData extends Comparable<ClassificationData>{

	public abstract Team getTeam();
	
	public abstract Integer getWins();
	
	public abstract Integer getDraws();
	
	public abstract Integer getLoses();
	
	public abstract Integer getFors();
	
	public abstract Integer getAgs();
	
	public abstract Integer getPoints();

	public abstract Division getDivision();

	public abstract void updateStats();

	public abstract void resetStats();

}