package team;

import java.util.List;

import employer.Employer;
import show.ShowImpl;
/**
 * 
 * @author Gabriel
 * @brief la clase que recojera todos los System Out
 * 
 * 	Todo lo que sea sacado por pantalla,  lo recoje esta clase.
 *
 */
public class ShowTeamImpl extends ShowImpl implements ShowTeam{
	
	public void income(Double income, Double finances){
		System.out.format("Tras recibir el ingreso de %.0f, el dinero en caja actual es de %.0f\n\n",income,finances);
	}
	
	public void waste(Double waste, Double finances){
		System.out.format("Tras pagar %.0f, el dinero en caja actual es de %.0f\n\n",waste,finances);
	}
	
	public void noMoney(){
		System.out.println("No hay suficientes fondos para realizar este gasto\n");	
	}
	
	public void noEmployers(){
		System.out.println("\nEl equipo no dispone de empleados actualmente");
	}
	
	public void employers(List<Employer> employers){
		
		System.out.println("\nEl staff actual del equipo es:");

		for(Employer employee: employers)
			System.out.println(employee.toString());

		System.out.println("A continuación, se les pagará su salario semanal");
	}
	
	public void assistant(){
		System.out.println("El empleado Assistant Manager sólo puede contratarse en la primera semana");
	}
	
	public void chooseTraining(){
		System.out.println("\nSe procede a elegir el entrenamiento semanal de los jugadores de la plantilla\n");
	}
}
