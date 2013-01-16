package match;

/**
 * @author   Juan
 * @brief la interfaz para la Formaci�n que tiene un equipo,
 * 
 * 	Es decir, la defensa, Ataque, medio.
 */
public interface Formation {

	public Integer getDefense();

	public Integer getMiddle();

	public Integer getAttack();

	public void setDefense(Integer defense);

	public void setMiddle(Integer middle);

	public void setAttack(Integer attack);
	
	boolean isEspecialFormation();	
}
