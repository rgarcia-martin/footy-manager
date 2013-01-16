package selection;

import java.util.LinkedList;
import match.*;
import java.util.List;

import player.*;
import team.*;
import interfaceUser.*;

/**
 * @author  Pedro Carlos
 */
public class SelectionImpl implements Selection{

	private final PlayerControlledTeam team;
	private final Players players;
	private Formation formation;
	private Players selection;
	private FactoryPlayer fp = new FactoryPlayerImpl();
	
	public SelectionImpl(PlayerControlledTeam t){
		team = t;
		formation = new FormationImpl();
		players = new PlayersImpl();
		for(Player p : team.getPlayersInTeam().getPlayers()){
			if(!p.isInjured()){
				players.addPlayer(p);
			}			
		}		
	}

	public PlayerControlledTeam getTeam(){
		return team;
	}
	
	public void setFormation() {		
		System.out.println("\t\tElija una formacion");
		Integer numFormation;		
		Formation available;
		available = null;
		while(available==null){
			numFormation= InterfaceUserImpl.setFormation(players.getPlayers());		
			formation = setFormation(numFormation,players.getPlayers());		
			available = InterfaceUserImpl.checkFormation(formation, players.getPlayers());		
		}
		Players newPlayers=continueBuilding(available);
		setSelection(newPlayers);
	}
	
	public Formation setFormation(Integer num, List<Player> players){	
		Formation formation = new FormationImpl();
		if(num==1){
			formation.setDefense(4);
			formation.setMiddle(4);
			formation.setAttack(2);			
		}else if(num==2){
			formation.setDefense(4);
			formation.setMiddle(5);
			formation.setAttack(1);
		}else if(num==3){
			formation.setDefense(5);
			formation.setMiddle(4);
			formation.setAttack(1);
		}else if(num==4){
			formation.setDefense(4);
			formation.setMiddle(3);
			formation.setAttack(3);
		}else if(num==5){
			formation.setDefense(4);
			formation.setMiddle(2);
			formation.setAttack(4);
		}else if(num==6){
			formation.setDefense(3);
			formation.setMiddle(4);
			formation.setAttack(3);
		}else if(num==7){
			formation.setDefense(3);
			formation.setMiddle(5);
			formation.setAttack(2);
		}		
		return formation;
	}


	public Formation getFormation(){
		return formation;
	}	
	

	public void setSelection(Players newPlayers){
		selection=InterfaceUserImpl.setSelection(newPlayers,players,formation);
		show();
	}

	public Players getSelection(){
		return selection;
	}
	
	public Players continueBuilding(Formation available){
		Players newPlayers = new PlayersImpl();
		List<PositionTypes> positions = new LinkedList<PositionTypes>();
		Formation difference = playersNeeded(available);
		Integer defense = difference.getDefense();
		Integer middle = difference.getMiddle();
		Integer attack = difference.getAttack();		
		if(defense>0){	
			positions.add(PositionTypes.CB);
			positions.add(PositionTypes.SB);			
			for(int i =0;i<defense;i++){
				/**< Defensa anadido */
				Player p = fp.getNewYoungPlayer(team);
				p.setPositions(positions);
				newPlayers.addPlayer(p);
			}
		}
		if(middle>0){	
			positions.add(PositionTypes.CM);
			positions.add(PositionTypes.SM);			
			for(int i =0;i<middle;i++){
				/**< Centrocampista anadido */
				Player p = fp.getNewYoungPlayer(team);
				p.setPositions(positions);
				newPlayers.addPlayer(p);
			}
		}
		if(attack>0){			
			positions.add(PositionTypes.CF);						
			for(int i =0;i<attack;i++){
				/**< Delantero anadido */
				Player p = fp.getNewYoungPlayer(team);
				p.setPositions(positions);
				newPlayers.addPlayer(p);
			}
		}		
		return newPlayers;		
	}
	
	public Formation playersNeeded(Formation available){
		Formation playersNeeded = new FormationImpl();
		Integer defense = formation.getDefense()-available.getDefense();
		Integer middle = formation.getMiddle()-available.getMiddle();		
		Integer attack = formation.getAttack()-available.getAttack();
		playersNeeded.setDefense(defense);
		playersNeeded.setMiddle(middle);
		playersNeeded.setAttack(attack);		
		return playersNeeded;
	}
	/**< Este metodo es para comprobar que todo ha ido bien, se borrara en un futuro. */
	public void show(){
		System.out.println("\tMostrando la seleccion para el próximo partido.......");
		for(Player p : selection.getPlayers()){
			System.out.println("\t\t["+p.getNumber()+"] " + p.getPositions()+ "----->" +p.getName());
		}
	}
}
