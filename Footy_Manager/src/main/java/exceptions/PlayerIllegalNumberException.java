package exceptions;

public class PlayerIllegalNumberException extends IllegalArgumentException {
	/**
	 * @author Rafael
	 * @brief Controlar la Excepcion de Numero de jugador illegar.
	 */
	private static final long serialVersionUID = 1L;

	public PlayerIllegalNumberException(String s)
	{
		super(s);
	}
}
