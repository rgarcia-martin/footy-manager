package average;

/**
 * @author Zouhair
 * @class AverageImpl
 * @brief Distintos tipos de medias
 * 
 * Tenemos los distintos tipos de medias:
 * TypeAverage: Tipos de media: GK, A, M, D, D_PLUS, A_PLUS.
 * modeAverage: Media de tipo de la forma fisica del Jugador.
 * valueAverage:
 * LocationAverage: Ubicacion 
 */
public class AverageImpl implements Average {
	
	
		private final AverageTypes typeAverage;
		
		private AverageMode modeAverage;
		
		private AverageLocation locationAverage;
		
		private final Double valueAverage;
	
	public AverageImpl (AverageTypes typeAverageIncoming, AverageLocation locationAverageIncoming, AverageMode modeAverageIncoming, Double valueAverageIncoming)
	{
		valueAverage = valueAverageIncoming;
		typeAverage = typeAverageIncoming;
		modeAverage = modeAverageIncoming;
		locationAverage = locationAverageIncoming;	
	}


	public AverageImpl(AverageTypes typeAverageIncoming, Double valueAverageIncoming)
	{
		valueAverage = valueAverageIncoming;
		typeAverage =typeAverageIncoming;
	}	


	public Double getValue() {
	
		return valueAverage;
	}


	
	public AverageTypes getTypeAverage() {
		
		return typeAverage;
	}

	
	public AverageLocation getLocationAverage() {
		
		return locationAverage;
	}

	public AverageMode getModeAverage() {
	
		return modeAverage;
	}
	
	public String toString(){
		String res = "";
		
		if(typeAverage.equals(AverageTypes.GK))
			res = "[" + typeAverage.toString() + " = " + valueAverage + "]";
		else
			res = "[ " + typeAverage.toString() + " = " + valueAverage + " [mode: "+ modeAverage.toString() +"][location: "+ locationAverage.toString() +"]";
		
		return res;
	}
}

