package team;

import average.Averages;
/**
 * 
 * @author Zouhair
 * @brief Juador No Controlado por la maquina
 * 
 * 	Solo llevara los tipos de medias, que es lo que le vale para que el Jugador no controlado por la maquina que  juege al partido. 
 */
public interface NonPlayerControlledTeam extends Team {
	
	public abstract Averages getAverage(); 
}
