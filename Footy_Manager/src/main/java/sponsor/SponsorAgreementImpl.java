package sponsor;

public class SponsorAgreementImpl implements SponsorAgreement{

	private final Sponsor sponsor;

	private Integer temp;
	private final Integer duration;

	
	public SponsorAgreementImpl(Sponsor s, Integer d){
		sponsor = s;
		temp = 0;
		duration = d;
	}

	public Integer getTemp(){
		return temp;
	}

	public Integer getDuration(){
		return duration;
	}
	
	public Sponsor getSponsor(){
		return sponsor;
	}
	
	public void updateAgreement() {
		temp++;		
	}
}