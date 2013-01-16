package transferList;

import player.Player;
import show.ShowImpl;
import team.PlayerControlledTeam;
import team.Team;
/**
 * 
 * @author Gabriel
 * @brief la clase que recoje todo lo que sale por pantalla y relacionado con La lista de transefencias.
 *
 */
public class ShowTransferListImpl extends ShowImpl implements ShowTransferList{

	public void addPlayer(Player player, Double price) {
		System.out.format("Se ha añadido a la lista %s, con un precio de %.0f\n", player.getName(), price);
	}

	public void emptyList() {
		System.out.format("La lista de transferencias actual está vacía\n");
	}
	
	public void introList(){
	
		System.out.format("\nLa lista de transferencias actual se compone de estos jugadores:\n");
		System.out.format("Nombre      -      Equipo Actual      -      Precio Inicial\n");
	}

	public void players(String name, Team team, Double price) {
		System.out.format("%-18s %-30s %-10.0f\n", name, team.getName(), price);
	}

	public void bidPlayer(Player player) {
		System.out.format("Se escucharán ofertas por el jugador %s\n", player.getName());
	}

	public void nonPlayerBid() {
		System.out.format("Un equipo del sistema está interasado en pujar\n");		
	}

	public void teamBid(PlayerControlledTeam team) {
		System.out.format("¿El equipo %s quiere participar en esta puja?\n", team.getName());		
	}

	public void noBids() {
		System.out.format("El jugador no ha recibido pujas\n");			
	}

	public void playerSold(Player player, Double price) {
		System.out.format("El jugador %s del equipo %s ha sido vendido por %.0f\n", player.getName(), player.getTeam().getName(), price);					
	}

	public void playerBought(Player player, PlayerControlledTeam team) {
		System.out.format("El jugador %s ha sido adquirido por el equipo %s\n", player.getName(), team.getName());					
	}
}