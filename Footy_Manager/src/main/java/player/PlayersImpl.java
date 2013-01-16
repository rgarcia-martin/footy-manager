package player;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Iterables;
/**
 * @author  Rafael
 */
public class PlayersImpl implements Players {

	private final List<Player> players;
	
	public PlayersImpl(List<Player> playersIncoming){
		players = new LinkedList<Player>();
		
		players.addAll(playersIncoming);
	}
	
	public PlayersImpl(){
		players = new LinkedList<Player>();
	}
	
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public void addPlayer(Player p){
		this.players.add(p);
	}
	
	public Player remove(Player p)
	{
		return players.remove(players.indexOf(p));
		
	}
	
	public Player findPlayer(Integer number)
	{
		return Iterables.find(this.players, new PredicatePlayerWithNumber(number));
	}
	
	public List<Player> findPlayer(PositionTypes pt)
	{
		List<Player> res = new LinkedList<Player>();
		
		for(Player p: Iterables.filter(this.players, new PredicatePlayerWithPosition(pt))){
			res.add(p);
		}
		
		return res;
	}

	@Override
	public void addAll(Collection<Player> p) {
		players.addAll(p);
	}
	
	public String toString(){
		return players.toString();
	}
}