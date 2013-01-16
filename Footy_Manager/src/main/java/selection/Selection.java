package selection;

import player.Players;
import match.Formation;

/**
 * @author   Pedro Carlos
 */
public interface Selection {
	
	public void setFormation();
	public Formation getFormation();
	public void setSelection(Players playersNeeded);
	public Players getSelection();
	public Players continueBuilding(Formation available);
	public Formation playersNeeded(Formation available);
	void show();
}
