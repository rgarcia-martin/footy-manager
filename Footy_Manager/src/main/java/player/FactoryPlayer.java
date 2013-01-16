package player;

import team.PlayerControlledTeam;

public interface FactoryPlayer{
	Player getNewPlayer(PlayerControlledTeam t);
	Player getNewYoungPlayer(PlayerControlledTeam t);
}
