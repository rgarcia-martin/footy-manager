package player;

import show.ShowImpl;
/**
 * @author  Rafael
 */
public class ShowSquadImpl extends ShowImpl implements ShowSquad{
		
	public void squad(Players squad){

		for(Player player: squad.getPlayers())								       
			System.out.println(player);							       
			//System.out.format("%-3d %-15s Edad: %-5d Habilidad: %-6.1f Forma: %-5s Posiciones: %s\n",player.getNumber(),player.getName(),player.getAge(),player.getAbility(),player.getForm(),player.getPositions());	
	}
	
	public void potentialDeclineRetirementPlayers(PreSeasonUpdatesSquad preSeasonUpdatesSquad){

		if(!preSeasonUpdatesSquad.getNeverPotentialAgain().isEmpty()){

			System.out.println("Estos jugadores no podrán mejorar su habilidad actual nunca más:");
			for(Player player: preSeasonUpdatesSquad.getNeverPotentialAgain()){
				System.out.format("%-15s %.1f\n",player.getName(),player.getAbility());
			}
			blank();
		}
		if(!preSeasonUpdatesSquad.getDeclined().isEmpty()){

			System.out.println("Estos jugadores han sufrido una bajada en su habilidad. Se muestra su habilidad actualizada:");
			for(Player player: preSeasonUpdatesSquad.getDeclined()){
				System.out.format("%-15s %.1f\n",player.getName(),player.getAbility());
			}
			blank();
		}
		if(!preSeasonUpdatesSquad.getRetired().isEmpty()){
			System.out.println("Estos jugadores han anunciado su retirada al finalizar la temporada:");
			for(Player player: preSeasonUpdatesSquad.getRetired()){
				System.out.println(player.getName());
			}
			blank();
		}
	}
}