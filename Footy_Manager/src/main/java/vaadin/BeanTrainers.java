package vaadin;

import team.PlayerControlledTeam;
import employer.Employer;

public class BeanTrainers{
	private Employer e;
	private PlayerControlledTeam t;
	private Integer num;
	
	public BeanTrainers(Employer emp, PlayerControlledTeam team, Integer n){
		e = emp;
		t = team;
		num = n;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public String getName(){
		return e.getType().toString();
	}
	
	public String getHired(){
		String res = "No";
		
		if(t.getEmployersOfTeam().existsEmployer(e.getType())){
			res = "Si";
		}
		
		return res;
	}
	
	public void setHired(String s){
		if(s.equals("Si"))
		{
			if(t.getFinances() > e.getWage())
			{
				t.hireEmployer(e.getType());
				t.addWaste(e.getWage());
				VaadinMain.refresh();
			}
			else
				VaadinMain.launchMen("El equipo no dispone de dinero para contratar este empleado");
		}
		else if(s.equals("No"))
		{
			VaadinMain.yesOrNoTrainer("¿Desea despedir el empleado?", this);
		}
		else
		{
			VaadinMain.launchMen("Empleado Despedido");
			t.fireEmployer(e.getType());		
		}
	}
}
