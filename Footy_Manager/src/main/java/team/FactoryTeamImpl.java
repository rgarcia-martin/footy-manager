package team;

import readers.TextToIterableImpl;
import interface_user.InterfaceUser;
import interface_user.InterfaceUserImpl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


public class FactoryTeamImpl implements FactoryTeam {
	
	private static final List<String> names = new LinkedList<String>();
	private static final InterfaceUser iu = new InterfaceUserImpl();
	
	private static final String dir_fich = "src/main/resources/NonPlayerTeams";		
	
	private static final Iterable<String> itNames = new TextToIterableImpl(dir_fich);
	private static Iterator<String> it = itNames.iterator();

	private static final Integer POS_RANK = 0;
	private static final Integer POS_NAME = 1;

	private static Integer NUM_TEAMS = 1;

	@Override
	public PlayerControlledTeam createNewPCT() {
		
		String name = iu.getNameTeam(NUM_TEAMS);
		
		while(names.contains(name)){
			iu.write("Nombre erroneo, ya existe otro equipo con el mismo nombre");
			
			name = iu.getNameTeam(NUM_TEAMS);
		}
		
		names.add(name);	
		NUM_TEAMS++;
		
		return new PlayerControlledTeamImpl(name, new ShowTeamImpl());
	}
	
	@Override
	public PlayerControlledTeam createNewPCT(String name) {
		
		return new PlayerControlledTeamImpl(name, new ShowTeamImpl());
	}

	@Override
	public NonPlayerControlledTeam createNewNCT() {
		String line = getNewLine();

		List<String> l= new LinkedList<String>();
		
        StringTokenizer st=new StringTokenizer(line,";");
        while(st.hasMoreTokens()){
        	l.add(st.nextToken());
        }

        Integer ranking = Integer.parseInt(l.get(POS_RANK));
        String name = l.get(POS_NAME);
		String l2;
		
		StringBuffer buf = new StringBuffer();
		
        for(int i = POS_NAME + 1; i < l.size(); i++)
        {
        	buf.append(l.get(i));
        	buf.append(" ");
        }
           
        l2 = buf.toString();
        
		return new NonPlayerControlledTeamImpl(ranking, name, l2);
	}

	private String getNewLine() {
		String res = null;
		
		if(it.hasNext())
		{
			res = it.next();
		}
		else
		{
			it = itNames.iterator();
			res = getNewLine();
		}
		
		return res;
	}
}
