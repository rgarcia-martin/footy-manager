package employer;

import java.util.List;
/**
 * 
 * @author Gabierl 
 * @class Employers 
 * @brief tendra los atributos: Buscar Empelado,Anadir Empeleado, Despedir Empleado, una lista de Empleados que tienes y si existe el empleado en tu lista.
 * 
 * 	FindeEmployer : Buscar empleado apartir de los distintos tipos de empleados que disponemos.
 * 	AddEmployer : Contratar a un Empelado.
 * 	RemoveEmployer : Despedir al empleado.
 * 	List<Empolyer> getEmployrs :  apartir de la Lista Employer obtener un empleado Deseado.
 *	existsEmployer : Comprar que disponemos de un empleado. que es un boolean que Solamente nos dira Si dispones o no disponemos.
 */
public interface Employers {

	public abstract Employer findEmployer(EmployerTypes et);

	public abstract void addEmployer(Employer e);

	public abstract void removeEmployer(Employer e);

	public abstract List<Employer> getEmployers();
	
	public abstract boolean existsEmployer(EmployerTypes et);

}