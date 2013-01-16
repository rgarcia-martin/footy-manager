package stadium;

import show.ShowImpl;
/**
 * 
 * @author Jesus Manuel
 *
 */
public class ShowStadiumImpl extends ShowImpl implements ShowStadium{

	public void stadium(Stadium stadium){
		
		System.out.println("El estadio cuenta con una capacidad de " +stadium.getCapacity());
		for(StadiumZone stand: stadium.getAreas())
			System.out.format("La zona %s del estadio, sector %s, de tipo %s, tiene %d personas de capacidad\n",stand.getLocation(),stand.getTier(),stand.getMode(),stand.getCapacity());
	
		blank();
	}
	
	public void buildPrices(){
		
		System.out.println("Los precios de remodelación son los siguientes, recuerde que la capacidad en asientos(Seated y Grass) debe ser superior a la de pie:\n");
		System.out.format("Stand: %-5s / %-5s  Type: %-8s   Tier Capacity:  %d   Tier 1 Price: %.0f   Tier 2 Price: %.0f   Tier 3 Price: %.0f\n", SZLocation.NORTH, SZLocation.SOUTH, SZMode.STANDING, StadiumZoneImpl.CAPACITIES[6], StadiumZoneImpl.NORTH_SOUTH_STANDING_PRICES[0], StadiumZoneImpl.NORTH_SOUTH_STANDING_PRICES[1], StadiumZoneImpl.NORTH_SOUTH_STANDING_PRICES[2]);
		System.out.format("Stand: %-5s / %-5s  Type: %-8s   Tier Capacity:  %d   Tier 1 Price: %.0f   Tier 2 Price: %.0f   Tier 3 Price: %.0f\n", SZLocation.NORTH, SZLocation.SOUTH, SZMode.SEATED, StadiumZoneImpl.CAPACITIES[5], StadiumZoneImpl.NORTH_SOUTH_SEATED_PRICES[0], StadiumZoneImpl.NORTH_SOUTH_SEATED_PRICES[1], StadiumZoneImpl.NORTH_SOUTH_SEATED_PRICES[2]);
		System.out.format("Stand: %-5s / %-5s  Type: %-8s   Tier Capacity:  %d   Tier 1 Price: %.0f   Tier 2 Price: %.0f   Tier 3 Price: %.0f\n", SZLocation.WEST, SZLocation.EAST, SZMode.STANDING, StadiumZoneImpl.CAPACITIES[4], StadiumZoneImpl.WEST_EAST_STANDING_PRICES[0], StadiumZoneImpl.WEST_EAST_STANDING_PRICES[1], StadiumZoneImpl.WEST_EAST_STANDING_PRICES[2]);
		System.out.format("Stand: %-5s / %-5s  Type: %-8s   Tier Capacity:  %d   Tier 1 Price: %.0f   Tier 2 Price: %.0f   Tier 3 Price: %.0f\n", SZLocation.WEST, SZLocation.EAST, SZMode.SEATED, StadiumZoneImpl.CAPACITIES[3], StadiumZoneImpl.WEST_EAST_SEATED_PRICES[0], StadiumZoneImpl.WEST_EAST_SEATED_PRICES[1], StadiumZoneImpl.WEST_EAST_SEATED_PRICES[2]);
		System.out.format("Stand: %-12s   Type: %-8s   Inner Capacity: %d   Inner Price : %.0f   Outer Capacity: %d   Outer Price : %.0f\n", SZLocation.CORNER, SZMode.STANDING, StadiumZoneImpl.CAPACITIES[3], StadiumZoneImpl.CORNER_STANDING_PRICES[0], StadiumZoneImpl.CAPACITIES[2], StadiumZoneImpl.CORNER_STANDING_PRICES[1]);
		System.out.format("Stand: %-12s   Type: %-8s   Inner Capacity: %d   Inner Price : %.0f   Outer Capacity: %d   Outer Price : %.0f\n", SZLocation.CORNER, SZMode.SEATED, StadiumZoneImpl.CAPACITIES[2], StadiumZoneImpl.CORNER_SEATED_PRICES[0], StadiumZoneImpl.CAPACITIES[1], StadiumZoneImpl.CORNER_SEATED_PRICES[1]);
		blank();
	}
	
	public void reforms() {
		System.out.println("El estadio ya está siendo remodelado");		
	}
	
	public void seats(){
		System.out.println("¿Desea que la grada tenga asientos?");		
	}
	
	public void confirm(){
		System.out.println("¿Desea confirmar las modificaciones al estadio?");		
	}
	
	public void maxTier(){
		System.out.println("La grada seleccionada ya está construida al máximo");		
	}
	
	public void noMoney(){
		System.out.println("No hay suficientes fondos para realizar este gasto\n");	
	}
	
	public void noTier(){
		System.out.println("El sector a modificar no está aún construido\n");	
	}
	
	public void equalMode(){
		System.out.println("El sector a modificar se mantendría igual\n");	
	}
	
	public void finishBuild(){
		System.out.println("Las obras del estadio han concluido\n");	
	}
	
	public void matchIncome(){
		System.out.format("Se han obtenido los beneficios de taquilla por el partido de la semana anterior\n");
	}
	
}