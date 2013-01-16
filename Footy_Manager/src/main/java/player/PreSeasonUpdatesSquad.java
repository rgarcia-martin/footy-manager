package player;

import java.util.List;

/**
 * @author Alberto
 * @brief Actualizar la preptemporada.
 *
 */
public interface PreSeasonUpdatesSquad {

	void setPotential(Players squad);
	void setDecline(Players squad);
	void setRetirement(Players squad);
	List<Player> getNeverPotentialAgain();
	List<Player> getDeclined();
	List<Player> getRetired();
}
