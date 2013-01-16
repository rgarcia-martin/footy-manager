package interface_user;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import match.Match;

import org.apache.log4j.Logger;

import player.Player;
import player.TrainingTypes;

import division.ClassificationData;
import division.Division;
import employer.EmployerTypes;

import team.PlayerControlledTeam;
import team.Team;

public class InterfaceUserImpl implements InterfaceUser {
	private static final PrintStream output = System.out;
	
	private static final InputStream input = System.in;
	private static final InputStreamReader in_reader = new InputStreamReader(input);
	private static final BufferedReader reader = new BufferedReader(in_reader);
	
	private static final Logger log= Logger.getLogger(InterfaceUserImpl.class);
	
	public void pressEnter(){
		write("Presione Enter para continuar");
		readKeyboard();
	}
	
	public String readKeyboard(){		
		boolean rightKey = false;
		String read = null;
		
		while (!rightKey) {
			try {
				read = reader.readLine();
				if(read==null)
				{
					log.warn("Fallo al leer en el teclado");
				/**< Excepción contrlada por Apache, que cuando un Usuario Escriba un comando que no se la pedido que salte el mensaje de  : "Fallo al leer en el teclado"  */
				}
				else
				{
					rightKey=true;
				}
			} 
			catch (Exception e){
				e.printStackTrace();
			} 
		}
		
		return read;
	}
	
	public void write(Object o)
	{
		output.println(o.toString());
	}

	@Override
	public Integer getPlayers(Integer max) {
		Integer res = 0;
		boolean noNum = false;
		
		write("Introduzca numero de Jugadores [1-" + max + "]");
		
		while(!noNum)
		{
			try{
				res = Integer.parseInt(readKeyboard());
				
				if(res > 0 && res <= max)
				{
					noNum = true;
				}
				else
				{
					write("Numero Incorrecto. Vuelva a introducirlo [1-" + max + "]");					
				}
			}
			catch(Exception e)
			{
				write("Numero Incorrecto. Vuelva a introducirlo [1-" + max + "]");
			}
		}
		
		return res;
	}

	@Override
	public Integer getNumAgesForSponsor(Integer max) {
		Integer res = 0;
		boolean noNum = false;
		
		write("Introduzca numero de años de contrato para el Sponsor [1-" + max + "]");
		
		while(!noNum)
		{
			try{
				res = Integer.parseInt(readKeyboard());
				
				if(res > 0 && res <= max)
				{
					noNum = true;
				}
				else
				{
					write("Numero Incorrecto. Vuelva a introducirlo [1-" + max + "]");					
				}
			}
			catch(Exception e)
			{
				write("Numero Incorrecto. Vuelva a introducirlo [1-" + max + "]");
			}
		}
		
		return res;
	}

	@Override
	public String getNameTeam(Integer i) {	
		write("Introduzca el nombre del equipo del Jugador " + i);
		return readKeyboard();
	}

	@Override
	public boolean yesOrNot() {
		write("Para seleccionar Si o No pulse [S/N]:");
		String choice = readKeyboard();
		choice= choice.toUpperCase();
		
		while(!choice.equals("S") && !choice.equals("N"))
		{
			write("Para seleccionar Si o No pulse [S/N]:");
			choice = readKeyboard();
			choice= choice.toUpperCase();
		}

		boolean res = false;
		
		if(choice.equals("S"))
		{
			res = true;
		}
		
		return res;
	}

	@Override
	public void turn(PlayerControlledTeam t) {
		write("Turno de " + t.getName());
	}

	@Override
	public void endSeason() {
		write("Llegamos al final de la temporada!");
		write("Desea jugar otra temporada?");
	}

	@Override
	public void menu(PlayerControlledTeam playerControlledTeam, Integer s, Integer w) {
		String msg = null;

		msg =  "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                           ++\n";
		msg += "++    Turno de " + playerControlledTeam.getName() + "\n";
		msg += "++\n";
		msg += "++    Temporada " + s + "\n";
		msg += "++    Semana " + w + "\n";
		msg += "++\n";
		msg += "++    Finanzas " + playerControlledTeam.getFinances() + "\n";
		msg += "++                                           ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                           ++\n";
		msg += "++           Menu de FOOTY MANAGER           ++\n";
		msg += "++                                           ++\n";
		msg += "++    Opciones:                              ++\n";
		msg += "++                                           ++\n";
		msg += "++    1.- Entrenamiento de Jugadores         ++\n";
		msg += "++    2.- Contrato/Despido de Entrenadores   ++\n";
		msg += "++    3.- Lista de Transferencias            ++\n";
		msg += "++                                           ++\n";
		msg += "++    4.- Otras Opciones (Salvar Juego)      ++\n";
		msg += "++                                           ++\n";
		msg += "++    5.- Fin del Turno                      ++\n";
		msg += "++                                           ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++\n";
		
		write(msg);		
	}

	@Override
	public void showMatch(Match mat) {
		Team home = mat.getStatistics().getHome().getTeam();
		Team away = mat.getStatistics().getAway().getTeam();
		
		write("\t" + home.getName() + " - " + away.getName());	
		write("\tResultado [" + mat.getMyGoals(home) + " - " + mat.getMyGoals(away) + "]");
	}

	@Override
	public void showDivisions(Division d) {
		write("Clasificaciones de la division " + d.getCategory());
		
		for(ClassificationData cd: d.getTeams())
		{
			System.out.format("\t[%d] %-20s %d\n",cd.getTeam().getRanking(), cd.getTeam().getName(), cd.getPoints());
		}
		write("");
	}

	@Override
	public Integer getChoice(Integer min, Integer max, Integer quit) {
		Integer res = 0;
		boolean noNum = false;
		
		write("Introduzca opcion ["+min+"-"+max+"]");
		
		while(!noNum)
		{
			try{
				res = Integer.parseInt(readKeyboard());
				
				if(res >= min && res <= max)
				{
					noNum = true;
				}
				else
				{
					write("Numero Incorrecto. Vuelva a introducirlo ["+min+"-"+max+"]");					
				}
			}
			catch(Exception e)
			{
				write("Numero Incorrecto. Vuelva a introducirlo ["+min+"-"+max+"]");
			}
		}
		
		if(res == quit)
			res = -1;
		
		return res;
	}

	@Override
	public void menuTraining(PlayerControlledTeam playerControlledTeam) {
		String msg = null;

		msg =  "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++    Plantilla de Jugadores de " + playerControlledTeam.getName() + "\n";
		msg += "++                                                                                                                   ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                                                                                                   ++\n";
		for(Player p: playerControlledTeam.getPlayersInTeam().getPlayers())
		{
			msg += "++ " + p.toString() + "\n";
		}
		msg += "++                                                                                                                   ++\n";
		msg += "++ <Num_Jugador>.- Seleccionar Jugador                                                                               ++\n";
		msg += "++             0.- Volver al Menu                                                                                    ++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		
		write(msg);	
	}

	@Override
	public void menuTrainingPlayer(Player p) {
		String msg = null;

		msg =  "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++    Estado Actual del Jugador ["+p.getNumber()+"] " + p.getName() + "\n";
		msg += "++    \tHabilidad: " + p.getAbility() + "\n";
		msg += "++    \tEdad: " + p.getAge() + "\n";
		msg += "++    \tForma Fisica: " + p.getForm() + "\n";
		msg += "++    \tPos.: " + p.getPositions().toString() + "\n";
		msg += "++    \tEntrenamiento Actual: " + p.getActualTraining().toString() + "\n";
		msg += "++                                                                                                                   ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++    Semanas Realizadas para los diferentes entrenamientos                                                          ++\n";
		msg += "++                                                                                                                   ++\n";
		for(TrainingTypes t: TrainingTypes.values())
		{
			Integer n = p.getStatsTraining().get(t);
			
			if(n == null)
				n = 0;
			
			msg += "++\t\t" + t.toString() + ":   " + n;
			
			if((t.equals(TrainingTypes.CB) || t.equals(TrainingTypes.SB) 
					|| t.equals(TrainingTypes.CM) || t.equals(TrainingTypes.SM)
					|| t.equals(TrainingTypes.CF)) && (!p.getTeam().getEmployersOfTeam().existsEmployer(EmployerTypes.HC)))
			{
				msg += " [No Disponible, el equipo no dispone de HC]";
			}
			
			msg += "\n";
		}
		msg += "++                                                                                                                   ++\n";
		msg += "++    Seleccione El entrenamiento nuevo:                                                                             ++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++                       ABL.- Habilidad                                                                             ++\n";
		msg += "++                       FIT.- Fitness (Recuperarse de Lesiones)                                                     ++\n";
		msg += "++                       FRM.- Forma Fisica del Jugador                                                              ++\n";
		msg += "++                       RST.- Reposo, El jugador no se puede Lesionar                                               ++\n";
		msg += "++ [CB | SB | CM | SB | CF ].- Posiciones nuevas para el jugador                                                     ++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++                         Q.- Volver al Menu                                                                        ++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		
		write(msg);	
	}

	@Override
	public TrainingTypes getTrainingType(Player p) {
		TrainingTypes res = null;
		String r;
		
		List<String> l = new LinkedList<String>();
		List<String> l2 = new LinkedList<String>();

		l.add(TrainingTypes.ABL.toString());
		l.add(TrainingTypes.FIT.toString());
		l.add(TrainingTypes.FRM.toString());
		l.add(TrainingTypes.RST.toString());

		l2.add(TrainingTypes.CB.toString());
		l2.add(TrainingTypes.SB.toString());
		l2.add(TrainingTypes.CM.toString());
		l2.add(TrainingTypes.SM.toString());
		l2.add(TrainingTypes.CF.toString());

		l.add("Q");
		
		write("Introduzca una opcion");
		r = readKeyboard();
		r = r.toUpperCase();
		
		while(! l.contains(r) && ! l2.contains(r))
		{
			write("Introduzca una opcion");
			r = readKeyboard();
			r = r.toUpperCase();
		}
		
		if((! r.equals("Q")) && (l.contains(r) || (l2.contains(r) && p.getTeam().getEmployersOfTeam().existsEmployer(EmployerTypes.HC))))
		{
			res = TrainingTypes.valueOf(r);
		}
		
		return res;
	}

	@Override
	public void menuTrainers(PlayerControlledTeam playerControlledTeam) {
		String msg = null;

		msg =  "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++    Plantilla de Entrenadores de " + playerControlledTeam.getName() + "\n";
		msg += "++                                                                                                                   ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "++                                                                                                                   ++\n";
		Integer i = 0;
		for(EmployerTypes t: EmployerTypes.values())
		{
			i++;
			msg += "++ " + i + ".- " + t.toString();
			
			if(playerControlledTeam.getEmployersOfTeam().existsEmployer(t))
			{
				msg += " [Actualmente en Plantilla]";
			}
			
			msg += "\n";
		}
		msg += "++                                                                                                                   ++\n";
		msg += "++    Seleccione el entrenador:                                                                                      ++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++                       AM.- Asistant Manager                                                                       ++\n";
		msg += "++                       CD.- Club Doctor                                                                            ++\n";
		msg += "++                       CS.-                                                                                        ++\n";
		msg += "++                       HC.- Head Coach                                                                             ++\n";
		msg += "++                      YTC.- Young Team Coach                                                                       ++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "++                         Q.- Volver al Menu                                                                        ++\n";
		msg += "++                                                                                                                   ++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		msg += "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
		
		write(msg);	
	}

	@Override
	public EmployerTypes getEmployerType() {
		EmployerTypes res = null;
		String r;
		
		List<String> l = new LinkedList<String>();
		List<String> l2 = new LinkedList<String>();

		l.add(EmployerTypes.AM.toString());
		l.add(EmployerTypes.CD.toString());
		l.add(EmployerTypes.CS.toString());
		l.add(EmployerTypes.HC.toString());
		l.add(EmployerTypes.YTC.toString());

		l.add("Q");
		
		write("Introduzca una opcion");
		r = readKeyboard();
		r = r.toUpperCase();
		
		while(! l.contains(r) && ! l2.contains(r))
		{
			write("Introduzca una opcion");
			r = readKeyboard();
			r = r.toUpperCase();
		}
		
		if((! r.equals("Q")))
		{
			res = EmployerTypes.valueOf(r);
		}
		
		return res;
	}	
}
