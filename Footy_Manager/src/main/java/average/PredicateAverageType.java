package average;


import com.google.common.base.Predicate;

/**
 * @author  Rafael 
 */
public class PredicateAverageType implements Predicate<Average>{

	private final AverageTypes at;
	
	public PredicateAverageType(AverageTypes avt)
	{
		at = avt;
	}
	
	@Override
	public boolean apply(Average ad) {
		return ad.getTypeAverage().equals(at);
	}

}