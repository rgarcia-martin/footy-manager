package exceptions;

public class PlayerIllegalAgeException extends IllegalArgumentException {
	/**
	 * @author Rafael
	 * @brief Controlar la excepcion de que la edad del Jugador es Ilegal.
	 */
	private static final long serialVersionUID = 1L;

	public PlayerIllegalAgeException(String s)
	{
		super(s);
	}
}
