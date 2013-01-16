package average;

import java.util.List;

public interface Averages {

	public abstract void add(Average a);
	
	public abstract void addAll(Averages a);

	public abstract Average remove(Average a);

	public abstract Averages filterAverage(AverageMode mode);

	public abstract Averages filterAverage(AverageLocation loc);

	public abstract Average findAverage(AverageTypes type);
	
	public abstract List<Average> getAverages();
	
}