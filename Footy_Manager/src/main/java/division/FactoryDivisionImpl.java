package division;

import java.util.LinkedList;
import java.util.List;

import team.FactoryTeam;
import team.FactoryTeamImpl;
import team.PlayerControlledTeam;
import team.Team;


public class FactoryDivisionImpl implements FactoryDivision {
	
	private FactoryTeam ft = new FactoryTeamImpl();

	@Override
	public Division createNewDivisionWithoutPlayers(DivisionType dt) {
		Division d = new DivisionImpl(dt);
		
		List<Team> lt = new LinkedList<Team>();
		
		for(int i = 0; i < DivisionImpl.TEAMS_PER_DIVISION; i++)
		{
			lt.add(ft.createNewNCT());
		}
		
		d.fillDivision(lt);
		
		return d;
	}

	@Override
	public Division createNewDivisionWithPlayers(DivisionType dt, List<PlayerControlledTeam> lt) {
		Division d = new DivisionImpl(dt);
		
		List<Team> lt2 = new LinkedList<Team>();
		int i;
		
		for(i = 0; i < (DivisionImpl.TEAMS_PER_DIVISION - lt.size()); i++)
		{
			lt2.add(ft.createNewNCT());
		}
		
		int num_rank = 0;
		
		if(dt.equals(DivisionType.PREMIER_DIVISION))
			num_rank = 0;
		else if(dt.equals(DivisionType.SECOND_DIVISION))
			num_rank = 1;
		else if(dt.equals(DivisionType.THIRD_DIVISION))
			num_rank = 2;
		else if(dt.equals(DivisionType.FOURTH_DIVISION))
			num_rank = 3;
		
		if(i == 0)
		{
			i = (num_rank * DivisionImpl.TEAMS_PER_DIVISION);
		}
		else
		{
			i = lt2.get(i-1).getRanking();
		}
		
		for(int j = 0; j < lt.size(); j++){
			i += 1;
			lt.get(j).setRanking(i);
		}
		
		lt2.addAll(lt);
		
		d.fillDivision(lt2);
		
		return d;
	}

}
