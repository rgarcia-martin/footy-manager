package player;

/**
 * @author   Rafael
 * @biref Forma fisica de los Jugadores.
 */
public enum FormTypes {
	
	PLUS_PLUS, 	PLUS, 
	
	MINUS, 

	MINUS_MINUS; //++, +, -, --
	
	private static final String[] STRINGS= {"--", "-", "+", "++"};
	
	public String toString(){
		
		String string= "";
		
		if(this.equals(MINUS_MINUS))
			string= STRINGS[0];
		
		if(this.equals(MINUS))
			string= STRINGS[1];
		
		if(this.equals(PLUS))
			string= STRINGS[2];
		
		if(this.equals(PLUS_PLUS))
			string= STRINGS[3];
		
		return string;
	}
}