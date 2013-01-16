package employer;

import java.util.*;


import com.google.common.collect.Iterables;

/**
 * @author Gabriel
 * @class EmployersImpl
 * @brief Esta clase implementa los atributos: Buscar Empelado,Anadir Empeleado, Despedir Empleado, una lista de Empleados que tienes y si existe el empleado en tu lista.
 * 
 */
public class EmployersImpl implements Employers {

	private final List<Employer> employers;
	
	public EmployersImpl(){
		this.employers = new LinkedList<Employer>();
	}
	
	public EmployersImpl(List<Employer> employersIncoming){
		this.employers = new LinkedList<Employer>();
		
		employers.addAll(employersIncoming);
		/**
		 * Añadir todos los empleados  a la lista que hemos creado antes de Employer.
		 */
	}
	

	@Override
	public Employer findEmployer(EmployerTypes et)
	{
		return Iterables.find(employers, new PredicateEmployerWithType(et));
	}

	@Override
	public void addEmployer(Employer e){
		employers.add(e);
	}
	

	@Override
	public void removeEmployer(Employer e){
		employers.remove(employers.indexOf(e));
		/**
		 * metodo para Despedir al empleado/s deseado.
		 */
	}
	

	public List<Employer> getEmployers(){
		return employers;
	}
	
	public boolean existsEmployer(EmployerTypes et)
	{
		return Iterables.any(employers, new PredicateEmployerWithType(et));
	}
}
