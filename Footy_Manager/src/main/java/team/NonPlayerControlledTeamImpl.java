package team;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import average.*;

/**
 * @author Zouhair
 * @brief Jugador no forma parte del equipo de jugadores del juego. 
 * 
 */
public class NonPlayerControlledTeamImpl extends TeamImpl implements NonPlayerControlledTeam {

	private final Averages averages;
		
	private static final Integer POS_STRING_GK = 0;
	private static final Integer POS_STRING_DHF = 1;
	private static final Integer POS_STRING_DHD = 2;
	private static final Integer POS_STRING_DAF = 3;
	private static final Integer POS_STRING_DAD = 4;
	private static final Integer POS_STRING_AHF = 5;
	private static final Integer POS_STRING_AHD = 6;
	private static final Integer POS_STRING_AAF = 7;
	private static final Integer POS_STRING_AAD = 8;
	/**
	 * para Evitar los numeros magicos.
	 * @param ranking
	 * @param name
	 * @param linea
	 */
	
	public NonPlayerControlledTeamImpl(Integer ranking,String name, String linea){
		super(name,ranking);
		averages = new AveragesImpl();
		/*	
		 * La linea seria del estilo:
		 * "<AVERAGE GK> <AVERAGE D_PLUS HOME FULL> <AVERAGE D_PLUS HOME DEPLETED> <AVERAGE D_PLUS AWAY FULL> <AVERAGE D_PLUS AWAY DEPLETED> <AVERAGE A_PLUS HOME FULL> <AVERAGE A_PLUS HOME DEPLETED> <AVERAGE A_PLUS AWAY FULL> <AVERAGE A_PLUS AWAY DEPLETED>"
		 * 
		 */
		List<String> lista= new LinkedList<String>(); /**<  constructor en linea */ 
        StringTokenizer st=new StringTokenizer(linea," ");
        while(st.hasMoreTokens()){
            lista.add(st.nextToken());
        }
        
        averages.add(new AverageImpl(AverageTypes.GK, Double.valueOf(lista.get(POS_STRING_GK))));
        averages.add(new AverageImpl(AverageTypes.D_PLUS, AverageLocation.HOME, AverageMode.FULL, Double.valueOf(lista.get(POS_STRING_DHF))));
        averages.add(new AverageImpl(AverageTypes.D_PLUS, AverageLocation.HOME, AverageMode.DEPLETED, Double.valueOf(lista.get(POS_STRING_DHD))));
        averages.add(new AverageImpl(AverageTypes.D_PLUS, AverageLocation.AWAY, AverageMode.FULL, Double.valueOf(lista.get(POS_STRING_DAF))));
        averages.add(new AverageImpl(AverageTypes.D_PLUS, AverageLocation.AWAY, AverageMode.DEPLETED, Double.valueOf(lista.get(POS_STRING_DAD))));
        averages.add(new AverageImpl(AverageTypes.A_PLUS, AverageLocation.HOME, AverageMode.FULL, Double.valueOf(lista.get(POS_STRING_AHF))));
        averages.add(new AverageImpl(AverageTypes.A_PLUS, AverageLocation.HOME, AverageMode.DEPLETED, Double.valueOf(lista.get(POS_STRING_AHD))));
        averages.add(new AverageImpl(AverageTypes.A_PLUS, AverageLocation.AWAY, AverageMode.FULL, Double.valueOf(lista.get(POS_STRING_AAF))));
        averages.add(new AverageImpl(AverageTypes.A_PLUS, AverageLocation.AWAY, AverageMode.DEPLETED, Double.valueOf(lista.get(POS_STRING_AAD))));
	}
	
	public Averages getAverage() {
		return averages;
	}
}
