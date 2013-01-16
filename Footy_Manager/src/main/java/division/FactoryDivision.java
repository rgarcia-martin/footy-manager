package division;

import java.util.List;

import team.PlayerControlledTeam;


public interface FactoryDivision{
	Division createNewDivisionWithoutPlayers(DivisionType dt);
	Division createNewDivisionWithPlayers(DivisionType dt, List<PlayerControlledTeam> lt);
}
