package exceptions;

public class PlayerUnspecifiedHeadCoach extends UnsupportedOperationException{
	/**
	 * @author Rafael
	 * @brief Controlar la excepcion de que el entrenador no sea especificado.
	 */
	private static final long serialVersionUID = 1L;

	public PlayerUnspecifiedHeadCoach(String s)
	{
		super(s);
	}
}
