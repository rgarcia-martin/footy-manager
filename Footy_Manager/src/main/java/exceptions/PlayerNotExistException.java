package exceptions;

public class PlayerNotExistException extends IllegalArgumentException{
	
	/**
	 * @author Rafael
	 * @brief Controlar la exeption
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotExistException(String s){
		super(s);
	}
}
