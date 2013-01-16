package exceptions;

public class PlayerTeamNotHaveHeadCoach extends UnsupportedOperationException{
	/**
	 * @author Rafael
	 * @brief Controlar la excepcion: no hay Entrenador para el equipo
	 */
	private static final long serialVersionUID = 1L;

	public PlayerTeamNotHaveHeadCoach(String s)
	{
		super(s);
	}
}
