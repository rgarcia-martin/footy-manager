package sponsor;

public class SponsorImpl implements Sponsor{
	private String company;
	
	private Double oneSeasonCash;
	
	private Double twoSeasonsCash;

	public SponsorImpl(String name, Double oneCash, Double twoCash) {
		company= name;
		oneSeasonCash= oneCash;
		twoSeasonsCash= twoCash;
	}
	
	public String getName(){
		return company;
	}
	
	
	public Double getOneSeasonCash(){
		return oneSeasonCash;
	}
	
	public Double getTwoSeasonsCash(){
		return twoSeasonsCash;
	}
}
