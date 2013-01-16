package employer;

/**
 *  @author Gabriel
 * @class Employer
 * @brief tendra los distintos tipos de Empleados que hay y su salario.
 * 
 *  los Empleados son : AM, CD, CS, HC, YTC

 */
public interface Employer {
	public abstract EmployerTypes getType();

	public abstract double getWage();
}