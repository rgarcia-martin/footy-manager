package player;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import team.PlayerControlledTeam;

/**
 * @author   Rafael
 * @brief Interfaz Jugador, que llevara todo lo relacionado con el Jugador (del juego ) 
 */
public interface Player {

	Integer getNumber();

	void setNumber(Integer number);
	
	void setTeam(PlayerControlledTeam t);
	
	PlayerControlledTeam getTeam();

	boolean isInjured();
	
	
	String getName();
	
	
	void setName(String name);

	void setInjured(Integer i); /**< Para lesionar a un jugador hay que especificar cuantas semanas tiene que estar lesionado.*/
	
	Integer getTimeLimitInjured(); /**< Tiempo que el jugador debe estar lesionado */


	TrainingTypes getActualTraining();


	void setActualTraining(TrainingTypes actualTraining);

	Map<TrainingTypes, Integer> getStatsTraining();

	//public abstract void setStatsTraining(Map<PlayerImpl.TrainingTypes, Integer> statsTraining);

	Integer getAge();

	Double getAbility();

	FormTypes getForm();
	
	void activateUpdatesForm();
	
	void desactivateUpdatesForm();

	void updateForm(Integer i);/**< Si es positivo aumenta un punto de Forma, negativo rebaja. */

	void updateAbility(Double abi);/**< Actualiza la Habilidad si el resultado esta en [5.0, 10.0] */
	
	void addPosition(PositionTypes pos);
	
	void addPosition(Collection<PositionTypes> pos);

	List<PositionTypes> getPositions();

	void setPositions(List<PositionTypes> positions);

	Map<StatisticsTypes, List<Integer>> getStatistics();

	void updateWeeklyPlayer(Integer goals, Integer goalsConcededs, Integer assistans); /**< Actualizar al jugador semanalmente */
	
	void increaseAge();

	boolean getPotential();
	
	void setPotential(boolean p);
}
	  //tio rafa necesito que tu clase me de el precio del jugador para la hora de comprarlo o venderlo
	  //y si esta o no en la transferList ;) para la clase PlayerControlledTeam
	  // tio y cuando puedas pasame como conyo es el string del constructor de player que lo e estado probando y no m sale XD