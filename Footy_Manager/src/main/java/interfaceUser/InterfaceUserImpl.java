package interfaceUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import match.*;
import player.*;
/**
 * 
 * @author Carlos
 * @brief Clase que implemeta InterfaceUser, que es la interfaz de Usuario, es decir lo mensajes que Saldran...
 * 
 * 	Recogera Numero maximo y minimo de managers que pueden jugar, maximo de caracteres para el nombre de un equipo, Minimo y maximo  de tiempo que se puede contratar a un sponsor........
 *
 *
 *
 */
public class InterfaceUserImpl implements InterfaceUser{
	
	public static final Integer SPONSOR_MIN_SEASONS=1;
	public static final Integer SPONSOR_MAX_SEASONS=2;
	public static Integer setFormation(List<Player> players){
		String value= "";
		boolean validation = false;
		String patron = "[0-9]";
		Integer number = 0;		
		while((number<1 || number>7) && validation==false){
			printFormations();
			value = readInput();
			if(value.matches(patron)){
				number=Integer.valueOf(value);
				if(number<1 || number>7){
					System.out.println("Error, ese número no es valido.");
				}else{
					validation=true;
				}
			}else{
				System.out.println("Error, no has introducido ningun numero.\n");
			}
			
		}
		
		return number;
	}
	
	public static void printFormations(){
		System.out.println("\t\t1: 4-4-2");
		System.out.println("\t\t2: 4-5-1");
		System.out.println("\t\t3: 5-4-1");
		System.out.println("\t\t4: 4-3-3");
		System.out.println("\t\t5: 4-2-4");
		System.out.println("\t\t6: 3-4-3");
		System.out.println("\t\t7: 3-5-2\n");
		System.out.println("\t\tFormacion:");
	}
	
	public static Formation checkFormation(Formation formation,List<Player> players){	
		boolean cont = true;
		int defense = 0;
		int middle = 0;
		int attack = 0;
		Formation available = new FormationImpl();
		String response = "";			
		for(Player p: players){
			if(p.getPositions().contains(PositionTypes.SB) || p.getPositions().contains(PositionTypes.CB)){							
				defense+=1;				
			}else if(p.getPositions().contains(PositionTypes.CM) || p.getPositions().contains(PositionTypes.SM)){
				middle +=1;
			}else if(p.getPositions().contains(PositionTypes.CF)){
				attack +=1;
			}				
		}
		//en esta lista se guarda el numero de defensas,centrocampistas y delanteros disponibles.
		available.setDefense(defense);
		available.setMiddle(middle);
		available.setAttack(attack);
		if(defense<formation.getDefense()){			
			System.out.println("No tienes suficientes defensas para esta formacion, ¿Quieres continuar? [s/n]");
			response=(String)readInput();
			if(response.equals("s")){
				    System.out.println("Se introduciran " +(formation.getDefense()-defense)+ " defensas de la cantera");						
			}else if(response.equals("n")){
					cont = false;
					System.out.println("Elige otra seleccion");					
			}else{
				System.out.println("Debes introducir alguno de los siguientes caracteres: [s/n]");
				response=(String)readInput();
			}
			
		}else if(middle<formation.getMiddle()){
			System.out.println("No tienes suficientes centrocampistas para esta formacion, ¿Quieres continuar? [s/n]");
			response=(String)readInput();
			if(response.equals("s")){
				    System.out.println("Se introduciran " +(formation.getMiddle()-middle)+ " centrocampistas de la cantera");				
			}else if(response.equals("n")){
					cont = false;
					System.out.println("Elige otra seleccion");					
			}else{
				System.out.println("Debes introducir alguno de los siguientes caracteres: [s/n]");
				response=(String)readInput();
			}
		}else if(attack<formation.getAttack()){
			System.out.println("No tienes suficientes delanteros para esta formacion, ¿Quieres continuar? [s/n]");
			response=(String)readInput();
			if(response.equals("s")){
				    System.out.println("Se introduciran " +(formation.getAttack()-attack)+ " delanteros de la cantera");					
			}else if(response.equals("n")){
					cont = false;
					System.out.println("Elige otra seleccion");					
			}else{
				System.out.println("Debes introducir alguno de los siguientes caracteres: [s/n]");
				response=(String)readInput();
			}
		}
		if(cont==false){
			available=null;
			return available;
		}else{
			return available;
		}
		
	}
	
	public static Players setSelection(Players newPlayers,Players players,Formation formation){
		Players finalSelection = new PlayersImpl();	
		Integer defendersAdded = 0;
		Integer middleAdded = 0;
		Integer attackersAdded = 0;
		if(newPlayers.getPlayers().size()>0){
			System.out.println("\nJugadores de la cantera incluidos en la seleccion:");
			for(int i=0;i<newPlayers.getPlayers().size();i++){			
				Iterator<Player> it = newPlayers.getPlayers().iterator();
				Player p = it.next();
				finalSelection.addPlayer(p);
				System.out.println("Jugador " +(i+1)+ ":");
				System.out.println("Abilidad:" +p.getAbility());
				System.out.println("Posiciones:" +p.getPositions().get(0));
				if(p.getPositions().contains(PositionTypes.CB) || p.getPositions().contains(PositionTypes.SB)){
					defendersAdded+=1;
				}
				else if(p.getPositions().contains(PositionTypes.CM) || p.getPositions().contains(PositionTypes.SM)){
					middleAdded+=1;
				}else{
					attackersAdded+=1;
				}
				
			}
		}
		
		System.out.println("\nSelecciona los jugadores que jugaran este partido\n");

		Integer defense = formation.getDefense()-defendersAdded;
		Integer middle = formation.getMiddle()-middleAdded;
		Integer attack = formation.getAttack()-attackersAdded;
		Integer gatekeeper;
		
		System.out.println("Necesitas " +defense+ " defensas, " +middle+ " centrocampistas y " +attack+ " delanteros\n");
		//El manager selecciona los jugadores que quiere para el partido
				
		Players playersInPosition;
			
		//Eleccion de los defensas
		if(defense>0){
			List<PositionTypes> positions = new LinkedList<PositionTypes>();
			positions.add(PositionTypes.CB);
			positions.add(PositionTypes.SB);
			playersInPosition = getPlayersInPosition(players,positions);
			finalSelection = setFinalSelection(defense,playersInPosition,finalSelection);
		}
		//Eleccion de los centrocampistas
		if(middle>0){
			List<PositionTypes> positions = new LinkedList<PositionTypes>();
			positions.add(PositionTypes.CM);
			positions.add(PositionTypes.SM);
			playersInPosition = getPlayersInPosition(players,positions);
			finalSelection = setFinalSelection(middle,playersInPosition,finalSelection);
		}
		//Eleccion de los delanteros
		if(attack>0){
			List<PositionTypes> positions = new LinkedList<PositionTypes>();
			positions.add(PositionTypes.CF);
			playersInPosition = getPlayersInPosition(players,positions);
			finalSelection = setFinalSelection(attack,playersInPosition,finalSelection);
		}		
		//Eleccion de portero	
		List<PositionTypes> positions;
		positions = new LinkedList<PositionTypes>();
		positions.add(PositionTypes.GK);
		playersInPosition = getPlayersInPosition(players,positions);
		gatekeeper = playersInPosition.getPlayers().size();		
		if(gatekeeper>1){					
			finalSelection = setFinalSelection(gatekeeper,playersInPosition,finalSelection);
		}else{
			//Si solo hay un portero en el equipo, se asigna automaticamente.			
			Player p = playersInPosition.getPlayers().get(0);			
			finalSelection.addPlayer(p);
			System.out.println("Solo tienes un portero, por lo que se ha añadido automaticamente a la seleccion\n");
			
		}
		return finalSelection;		
	}
	
	public static Players setFinalSelection(Integer number, Players playersInPosition,Players finalSelection){
		String response="";
		while(number>0){
			if(playersInPosition.getPlayers().get(0).getPositions().contains(PositionTypes.CB) ||
			(playersInPosition.getPlayers().get(0).getPositions().contains(PositionTypes.SB))){
				System.out.println("Selecciona " +number+ " defensas\n");
			}
			if(playersInPosition.getPlayers().get(0).getPositions().contains(PositionTypes.CM) ||
					(playersInPosition.getPlayers().get(0).getPositions().contains(PositionTypes.SM))){
						System.out.println("Selecciona " +number+ " centrocampistas\n");
			}
			if(playersInPosition.getPlayers().get(0).getPositions().contains(PositionTypes.CF)){
						System.out.println("Selecciona " +number+ " delanteros\n");
			}
			if(playersInPosition.getPlayers().get(0).getPositions().contains(PositionTypes.GK)){
				System.out.println("Selecciona a tu portero\n");
				number=1;
			}			
			for(Player p: playersInPosition.getPlayers()){
				//Si el jugador ya esta en nuestra seleccion, no se vuelve a mostrar
				if(!finalSelection.getPlayers().contains(p)){				
					System.out.println("nombre: " +p.getName());
					System.out.println("Habilidad: " +p.getAbility());
					System.out.println("Posiciones: ");
					for(PositionTypes pt : p.getPositions()){
						System.out.println(pt);
					}
					System.out.println("¿Quieres seleccionar a este jugador para el partido?[s /otra tecla]");
					response = readInput();
					if(response.equals("s")){
						finalSelection.addPlayer(p);						
						number--;
					}
					if(number<=0)
						break;
				}
			}
		}
		return finalSelection;	
	}

	public static Players getPlayersInPosition(Players players,List<PositionTypes> positions){
		Players playersInPosition = new PlayersImpl();
		for(Player p : players.getPlayers()){
			for(PositionTypes pt : p.getPositions()){
				if(positions.contains(pt) && !p.isInjured()){
					playersInPosition.addPlayer(p);
				}
			}
		}
		return playersInPosition;
	}

	public static String readInput(){
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));	
		String response = "";
		try{
			response = (String)input.readLine();
		}catch(IOException e){
			System.out.println("Error en la lectura por teclado");
		}
		return response;
	}	
}