package player;

import com.google.common.base.Function;

/**
 * @author Alberto
 * @brief Apartir de un nombre de jugador Sacar su el numero del dorsal
 * 
 * 	que el manager nos de el nombre de un jugador y sacar el numero del Dorsal del jugador en concreto.
 */ 

public class FunctionPlayerToNumber implements Function<Player,Integer>{

	public Integer apply(Player p){
		
		return p.getNumber();
	}
	
}