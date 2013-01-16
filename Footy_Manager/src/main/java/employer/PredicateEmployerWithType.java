package employer;

import com.google.common.base.Predicate;


/**
 * @author  Rafel
 */
public class PredicateEmployerWithType implements Predicate<Employer>{

	private final EmployerTypes employerType;
	
	public PredicateEmployerWithType(EmployerTypes et)
	{
		employerType = et;
	}
	
	public boolean apply(Employer em) {
		return em.getType().equals(employerType);
	}
}