package player;

import java.util.Collection;
import java.util.List;
/**
 * @author  Rafael
 */
public interface Players {

	List<Player> getPlayers();

	void addPlayer(Player p);
	void addAll(Collection<Player> p);

	Player remove(Player p);

	Player findPlayer(Integer number);

	List<Player> findPlayer(PositionTypes pt);
}