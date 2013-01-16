package transferList;

import java.util.Set;

import player.Player;
import team.PlayerControlledTeam;
/**
 * 
 * @author Gabriel
 * @brief la lista de taransefencias de los jugadores
 * 
 * 	Donde se pude anadir jugadores a la lista, quitar jugadore, fijar un precio por el Jugador... 
 *
 */
public interface TransferList {

	Set<Player> getPlayers();
	boolean containsPlayer(Player player);
	void removeTransferList(Player player);
	void addTransferList(Player player);
	void show();
	Double getPrice(Player player);
	void sellPlayer(Player player, Double price);
	void buyPlayer(Player player, PlayerControlledTeam team, Double price);
}
