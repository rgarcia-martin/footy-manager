package vaadin;

import java.util.List;

import employer.EmployerTypes;

import player.Player;
import player.PositionTypes;
import player.TrainingTypes;
import team.PlayerControlledTeam;

public class BeanPlayer {
	private Player p;
	private Integer num;
	
	public BeanPlayer(Integer i, PlayerControlledTeam t){
		p = t.getPlayersInTeam().getPlayers().get(i);
		num = i;
	}

	public Integer getNum() {
		return num;
	}
	
	public void setName(String n){
		p.setName(n);
	}

	public String getName() {
		return p.getName();
	}
	
	public void setNumber(Integer n){
		p.setNumber(n);
	}

	public Integer getNumber() {
		return p.getNumber();
	}
	
	public Integer getAge(){
		return p.getAge();
	}
	
	public Double getAbility(){
		return p.getAbility();
	}
	
	public String getForm(){
		return p.getForm().toString();
	}
	
	public String getPositions(){
		String res = "";
		
		List<PositionTypes> pos = p.getPositions();
		
		for(PositionTypes pt: pos)
		{
			if(pt.equals(PositionTypes.GK))
				res += "Portero\n";
			if(pt.equals(PositionTypes.CB))
				res += "Defensa Central\n";
			if(pt.equals(PositionTypes.SB))
				res += "Defensa Lateral\n";
			if(pt.equals(PositionTypes.CM))
				res += "Centrocampista Central\n";
			if(pt.equals(PositionTypes.SM))
				res += "Centrocampista Lateral\n";
			if(pt.equals(PositionTypes.CF))
				res += "Delantero\n";
		}
		
		return res;
	}
	
	public boolean getTrainerHC(){
		return p.getTeam().getEmployersOfTeam().existsEmployer(EmployerTypes.HC);
	}
	
	public void setActualTraining(String n){
		TrainingTypes res = TrainingTypes.valueOf(n);
		
		if (p.isInjured())
		{
			VaadinMain.launchMen("Operacion no Permitida. El jugador esta lesionado");
		}else if((!getTrainerHC()) && (res.equals(TrainingTypes.CB) || res.equals(TrainingTypes.SB) || 
				res.equals(TrainingTypes.CM) || res.equals(TrainingTypes.SM) || res.equals(TrainingTypes.CF)))
		{
			VaadinMain.launchMen("Operacion no Permitida. El equipo no dispone de Head Coach");
		}
		else
		{
			p.setActualTraining(TrainingTypes.valueOf(n));
		}
	}

	public String getActualTraining() {
		return p.getActualTraining().toString();
	}	

	public String getInjured() {
		String res = "No";
		
		if(p.isInjured())
			res = "Si";
		
		return res;
	}	
}
