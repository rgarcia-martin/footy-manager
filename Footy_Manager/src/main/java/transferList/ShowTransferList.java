package transferList;

import player.Player;
import show.Show;
import team.PlayerControlledTeam;
import team.Team;
/**
 * 
 * @author Gabriel
 * @brief muestra la Lista de transefencias por pantalla
 * 	
 * 	
 *
 */
public interface ShowTransferList extends Show{

	void addPlayer(Player player, Double price);
	void emptyList();
	void introList();
	void players(String name, Team team, Double price);
	void bidPlayer(Player player);
	void nonPlayerBid();
	void teamBid(PlayerControlledTeam team);
	void noBids();
	void playerSold(Player player, Double price);
	void playerBought(Player player, PlayerControlledTeam team);
}
