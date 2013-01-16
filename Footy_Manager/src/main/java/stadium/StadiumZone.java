package stadium;

/**
 * @author  Jesus Manuel
 * @biref Zonas del estadio.
 */
public interface StadiumZone {

	
	
	public abstract SZMode getMode();

	public abstract SZLocation getLocation();

	public abstract SZTier getTier();

	public abstract Integer getCapacity();

	public abstract Double getCost();
	 

}