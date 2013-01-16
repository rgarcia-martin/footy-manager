package player;

import java.util.List;

import com.google.common.base.Function;
/**
 * @author Alberto
 * @brief De un jugador sacar su pocision
 * 	de un nombre de jugador que nos de el manager sacar su posicion en la que juega en el campo ( Defensa,Ataque, Portero...)
 */


public class FunctionPlayerToPositionType implements Function<Player,List<PositionTypes>>{

	public List<PositionTypes> apply(Player p){
		
		return p.getPositions();
	}
	
}