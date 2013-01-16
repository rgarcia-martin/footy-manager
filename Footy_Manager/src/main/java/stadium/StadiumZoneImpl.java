package stadium;


/**
 * @author  Jesus Manuel
 * @brief Zonas del estadio: Localizacion, Tipo, Tiro,Nivel y Capacidad del estadio.
 */
public class StadiumZoneImpl implements StadiumZone{
	

	private final SZMode mode;
	
	private final SZLocation location;

	private final SZTier tier;

	private Integer capacity;

	private Double cost;
	private static final int prime = 31;
	
	protected static final Double[] NORTH_SOUTH_STANDING_PRICES= {200000.0,400000.0,600000.0};
	protected static final Double[] WEST_EAST_STANDING_PRICES= {100000.0,300000.0,500000.0};
	protected static final Double[] CORNER_STANDING_PRICES= {200000.0,550000.0};
	protected static final Double[] NORTH_SOUTH_SEATED_PRICES= {250000.0,500000.0,750000.0};
	protected static final Double[] WEST_EAST_SEATED_PRICES= {150000.0,400000.0,600000.0};
	protected static final Double[] CORNER_SEATED_PRICES= {250000.0,700000.0};
	
	public static final Integer[] CAPACITIES= {1000,2000,2500,3000,3500,5000,6000};

	
	public StadiumZoneImpl(SZLocation loc, SZMode mod, SZTier tie)
	{
		mode = mod;
		location = loc;
		tier = tie;
		
		calcCapacityANDCost();
	}

	public SZMode getMode() {
		return mode;
	}
	
	
	public SZLocation getLocation() {
		return location;
	}

	public SZTier getTier() {
		return tier;
	}


	public Integer getCapacity() {
		return capacity;
	}

	public Double getCost() {
		return cost;
	}

/**< Con este metodo calculamos la nueva capacidad de la zona el coste de cambiarla */ 
	private void calcCapacityANDCost(){		
		if(location.equals(SZLocation.NORTH) || location.equals(SZLocation.SOUTH)){
			CalcCapCostNS();
		}
		else	if(location.equals(SZLocation.EAST) || location.equals(SZLocation.WEST)){
			CalcCapCostEW();
		}
		else	if(location.equals(SZLocation.CORNER)){
			CalcCapCostC();
		}			
	}
	
	private void CalcCapCostNS(){

		if(mode.equals(SZMode.STANDING)){
				this.capacity = CAPACITIES[6];
				if(tier.equals(SZTier.FIRST))		this.cost = NORTH_SOUTH_STANDING_PRICES[0];
				else if(tier.equals(SZTier.SECOND))		this.cost = NORTH_SOUTH_STANDING_PRICES[1];
				else if(tier.equals(SZTier.THIRD))		this.cost = NORTH_SOUTH_STANDING_PRICES[2];
		}
		else	if(mode.equals(SZMode.SEATED)){
			this.capacity = CAPACITIES[5];
			if(tier.equals(SZTier.FIRST))		this.cost = NORTH_SOUTH_SEATED_PRICES[0];
			else if(tier.equals(SZTier.SECOND))		this.cost = NORTH_SOUTH_SEATED_PRICES[1];
			else if(tier.equals(SZTier.THIRD))		this.cost = NORTH_SOUTH_SEATED_PRICES[2];	
		}
		else if(mode.equals(SZMode.GRASS)){
			this.capacity = CAPACITIES[0];
		}
	}
	
	private void CalcCapCostEW(){
		if(mode.equals(SZMode.STANDING)){
			this.capacity = CAPACITIES[4];
			if(tier.equals(SZTier.FIRST))		this.cost = WEST_EAST_STANDING_PRICES[0];
			else if(tier.equals(SZTier.SECOND))		this.cost = WEST_EAST_STANDING_PRICES[1];
			else if(tier.equals(SZTier.THIRD))		this.cost = WEST_EAST_STANDING_PRICES[2];
		}	
		else if(mode.equals(SZMode.SEATED)){
			this.capacity = CAPACITIES[3];
			
			if(tier.equals(SZTier.FIRST))		this.cost = WEST_EAST_SEATED_PRICES[0];
			else if(tier.equals(SZTier.SECOND))		this.cost = WEST_EAST_SEATED_PRICES[1];
			else if(tier.equals(SZTier.THIRD))		this.cost = WEST_EAST_SEATED_PRICES[2];	
		}
		else if(mode.equals(SZMode.GRASS)){
			this.capacity = CAPACITIES[0];
		}	
	}
	
	private void CalcCapCostC(){

		if(mode.equals(SZMode.STANDING)){				
			if(tier.equals(SZTier.INNER_CORNER)){		this.capacity = CAPACITIES[3];	this.cost = CORNER_STANDING_PRICES[0];}
			else if(tier.equals(SZTier.OUTER_CONNER)){		this.capacity = CAPACITIES[2];	this.cost = CORNER_STANDING_PRICES[0];}
		}
		else if(mode.equals(SZMode.SEATED)){				
			if(tier.equals(SZTier.INNER_CORNER)){		this.capacity = CAPACITIES[2];	this.cost = CORNER_SEATED_PRICES[0];}
			else if(tier.equals(SZTier.OUTER_CONNER)){		this.capacity = CAPACITIES[1];	this.cost = CORNER_SEATED_PRICES[1];}	
		}
		else if(mode.equals(SZMode.GRASS)){
			this.capacity = CAPACITIES[0];
		}			
	}


	public int hashCode() {
		
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((tier == null) ? 0 : tier.hashCode());
		return result;
	}

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StadiumZoneImpl other = (StadiumZoneImpl) obj;
		if (location != other.location)
			return false;
		if (mode != other.mode)
			return false;
		if (tier != other.tier)
			return false;
		return true;
	}

	public String toString() {
		return "StadiumZoneImpl [Type=" + mode + ", Location=" + location
				+ ", Tier=" + tier + ", Capacity=" + capacity + ", Cost="
				+ cost + "]";
	}
	
}
