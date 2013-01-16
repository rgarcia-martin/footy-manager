package exceptions;

public class PlayerIllegalAbilityException extends IllegalArgumentException {
	/**
	 * @author Rafael 
	 * @brief Controlar la excepcion que la habilidad del Jugador sea illegal.
	 */
	private static final long serialVersionUID = 1L;

	public PlayerIllegalAbilityException(String s)
	{
		super(s);
	}
}
