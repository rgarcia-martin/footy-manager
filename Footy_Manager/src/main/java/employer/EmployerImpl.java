package employer;

/**
 * @author  Gabriel
 * @class EmployerImpl
 * @brief tipos de empelados que hay, su salario
 */
public class EmployerImpl implements Employer{
	//TODO anyade enumerados en la interfaz y utilizalos para todas aquellas cosas que sepas que son fijas en el juego
	//como el tipo de empleados: EmployerType.HC (Para HeadCoach, por ejemplo)

	private final EmployerTypes type; //TODO Si pones los enumerados, cambia el tipo del atributo al enumerado
	
	private Integer wage;
		
	public EmployerImpl(EmployerTypes et)
	{
		this.type = et;
		
		if(type.equals(EmployerTypes.AM))			this.wage = 50000;
		else if(type.equals(EmployerTypes.CD))		this.wage = 80000;
		else if(type.equals(EmployerTypes.CS))		this.wage = 60000;
		else if(type.equals(EmployerTypes.HC))		this.wage = 20000;
		else if(type.equals(EmployerTypes.YTC))		this.wage = 50000;
		
		/**
		 *  tenemos los Distintos tipos de empelamos y sus Salarios que alcanzan.
		 */
	}

	public EmployerTypes getType(){
		return type;
	}

	public double getWage(){
		return wage;
	
	}
	public String toString(){
		return "["+getType()+"] Cost: "+getWage();
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployerImpl other = (EmployerImpl) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
