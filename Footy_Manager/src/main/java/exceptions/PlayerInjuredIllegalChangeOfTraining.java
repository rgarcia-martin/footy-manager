package exceptions;

public class PlayerInjuredIllegalChangeOfTraining extends UnsupportedOperationException {
	/**
	 * @author Rafael
	 * @brief Controlar la exepcion: Cambiar un jugador lesionado de una formacion illegal.
	 */
	private static final long serialVersionUID = 1L;

	public PlayerInjuredIllegalChangeOfTraining(String s)
	{
		super(s);
	}
}
