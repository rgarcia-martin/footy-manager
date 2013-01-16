package show;

/**
 * 
 * @author Alberto
 *
 */
public class ShowImpl implements Show{

	public void blank() {
		System.out.println();		
	}
	
	public void roll(Integer roll) {
		System.out.println("El dado muestra un " +roll);		
	}
}
