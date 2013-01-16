package team;


public interface FactoryTeam {
	PlayerControlledTeam createNewPCT();
	PlayerControlledTeam createNewPCT(String n);
	NonPlayerControlledTeam createNewNCT();
}
