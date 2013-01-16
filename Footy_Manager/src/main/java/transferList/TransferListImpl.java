package transferList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import player.Player;
import player.PositionTypes;
import team.PlayerControlledTeam;
/**
 * 
 * @author Gabriel
 * @brief Lista de transeferencias que recoje la Edad, habilidad, 1º precios para los jugadores..
 *
 */
public class TransferListImpl implements TransferList{
	
	private final Map<Player,Double> listPlayers;
	private final ShowTransferList showT;
	public static final Double PRICE= 100000.0;
	private static final Integer[] AGES= {16,21,25,29,33};
	private static final Double[] ABILITIES= {6.0,6.5,7.0,7.5,8.0,8.5,9.0,9.5,10.0};
	private static final Double[] FIRST_PRICES= {500000.0,1000000.0,2000000.0,3500000.0,5000000.0,7000000.0,10000000.0,15000000.0,20000000.0};
	private static final Double[] SECOND_PRICES= {400000.0,750000.0,1500000.0,2500000.0,3500000.0,5000000.0,8000000.0,12000000.0,15000000.0};
	private static final Double[] THIRD_PRICES= {250000.0,500000.0,1000000.0,1500000.0,3000000.0,4000000.0,6000000.0,10000000.0,13000000.0};
	private static final Double[] FOURTH_PRICES= {150000.0,250000.0,500000.0,1000000.0,2000000.0,3000000.0,5000000.0,8000000.0,10000000.0};
	private static final Double[] FIFTH_PRICES= {100000.0,150000.0,250000.0,750000.0,1000000.0,2000000.0,3500000.0,5000000.0,8000000.0};
	private static final Double[] SIXTH_PRICES= {150000.0,200000.0,400000.0,1000000.0,1500000.0,2500000.0,4500000.0,7000000.0,9000000.0};
	
	public TransferListImpl(ShowTransferList showTransferList){
		
		listPlayers= new HashMap<Player, Double>();
		showT= showTransferList;
	}
	
	public Set<Player> getPlayers(){
	
		return listPlayers.keySet();
	}
	
	public boolean containsPlayer(Player player){
		return listPlayers.containsKey(player);
	}
	
	public void removeTransferList(Player player){
			listPlayers.remove(player);
	}
	
	public void addTransferList(Player player){
		
		listPlayers.put(player, getPrice(player));
		showT.addPlayer(player, getPrice(player));
	}
	
	public void show(){
		
		if(listPlayers.isEmpty())
			showT.emptyList();
		
		else{
		
			showT.introList();
			for(Player player: getPlayers())
				showT.players(player.getName(), player.getTeam(), getPrice(player));
		}	
	}
	
	public void sellPlayer(Player player, Double price){
			
		showT.playerSold(player, price);
		player.getTeam().sellPlayer(player, price);
	}
	
	public void buyPlayer(Player player, PlayerControlledTeam team, Double price){
		
		showT.playerBought(player, team);
		team.buyPlayer(player, price);
	}
	
	public Double getPrice(Player player){
		
		Double price= 0.0;
		
		Integer age= player.getAge();
		Double ability= player.getAbility();
		
		if(age>= AGES[0] && age<AGES[1]){
			
			for(int i=0; i<ABILITIES.length;i++){
				
				if(ability.equals(ABILITIES[i])){
					
					price= FIRST_PRICES[i];
					break;
				}
			}
		}
				 
		else if(age>= AGES[1] && age<AGES[2]){
			
			for(int i=0; i<ABILITIES.length;i++){
				
				if(ability.equals(ABILITIES[i])){
					
					price= SECOND_PRICES[i];
					break;
				}
			}
		}
		
		else if(age>= AGES[2] && age<AGES[3]){
			
			for(int i=0; i<ABILITIES.length;i++){
				
				if(ability.equals(ABILITIES[i])){
					
					price= THIRD_PRICES[i];
					break;
				}
			}
		}
		
		else if(age>= AGES[3] && age<AGES[4]){
			
			for(int i=0; i<ABILITIES.length;i++){
				
				if(ability.equals(ABILITIES[i])){
					
					price= FOURTH_PRICES[i];
					break;
				}
			}
		}
		
		else if(age>= AGES[4] && !player.getPositions().contains(PositionTypes.GK)){
			
			for(int i=0; i<ABILITIES.length;i++){
				
				if(ability.equals(ABILITIES[i])){
					
					price= FIFTH_PRICES[i];
					break;
				}
			}
		}
		
		else if(age>= AGES[4] && player.getPositions().contains(PositionTypes.GK)){
			
			for(int i=0; i<ABILITIES.length;i++){
				
				if(ability.equals(ABILITIES[i])){
					
					price= SIXTH_PRICES[i];
					break;
				}
			}
		}
		
		return price;
	}
}