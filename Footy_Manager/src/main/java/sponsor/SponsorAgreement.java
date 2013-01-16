package sponsor;


public interface SponsorAgreement {
	Integer getTemp();
	Integer getDuration();
	Sponsor getSponsor();
	
	void updateAgreement();
}
