package match;

import player.Players;

public class SquadImpl implements Squad {
	private final Players players;
	private final Formation formation;
	
	public SquadImpl(Players ps, Formation f)
	{
		players = ps;
		formation = f;
	}

	@Override
	public Formation getFormation() {
		return formation;
	}

	@Override
	public Players getPlayers() {
		return players;
	}

}
