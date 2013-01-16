package stadium;

import show.Show;
/**
 * 
 * @author Jesus Manuel
 * @biref El estadio,
 */
public interface ShowStadium extends Show{

	void stadium(Stadium stadium);
	void buildPrices();
	void reforms();
	void seats();
	void confirm();
	void maxTier();
	void noMoney();
	void noTier();
	void equalMode();
	void finishBuild();
	void matchIncome();
}
