package average;

import java.util.LinkedList;


import java.util.List;


import com.google.common.collect.Iterables;



/**
 * @author Zouhair
 * @class AvergaresImpl
 * @brief Implementa la interfaz Averages.
 * 
 * Se crea una lista avergares que donde llevara todas las medias.
 * @author Zouhair
 *
 */

public class AveragesImpl implements Averages {
	private final List<Average> averages;
	
	public AveragesImpl(){
		this.averages = new LinkedList<Average>();
	}
	/**
	 * @return Se crea un lista Average
	 * @param averages
	 */
	
	public AveragesImpl(List<Average> ave){
		averages = new LinkedList<Average>();
		
		averages.addAll(ave);
	}
	

	@Override
	public void add(Average a){
		averages.add(a);
	}
	

	@Override
	public Average remove(Average a){
		return averages.remove(averages.indexOf(a));
	}

	@Override
	public Averages filterAverage(AverageMode mode){
		Averages res = new AveragesImpl();
		
		for(Average a: averages)
		{
			if(!a.getTypeAverage().equals(AverageTypes.GK))
			{
				if(a.getModeAverage().equals(mode))
				{
					res.add(a);
				}
			}
		}
		
		return res;
	}

	@Override
	public Averages filterAverage(AverageLocation loc){
		Averages res = new AveragesImpl();
		
		for(Average a: averages)
		{
			if(!a.getTypeAverage().equals(AverageTypes.GK))
			{
				if(a.getLocationAverage().equals(loc))
					res.add(a);
			}
		}
		
		return res;
	}

	@Override
	public Average findAverage(AverageTypes type) {
		return Iterables.find(averages, new PredicateAverageType(type));
	}
	
	public String toString(){
		return averages.toString();
	}
	
	@Override
	public void addAll(Averages a) {
		averages.addAll(a.getAverages());		
	}
	
	@Override
	public List<Average> getAverages() {
		return averages;
	}
}
