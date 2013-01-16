package match;


import org.apache.log4j.Logger;

/**
 * @author  Juan
 * @brief la clase que va a implemetar la clase Formation
 * 
 * 	 
 */
public class FormationImpl implements Formation{
	
	private Integer defense;
	
	private Integer middle;
	
	private Integer attack;
	
	private static final Logger log= Logger.getLogger(FormationImpl.class);
	
	public FormationImpl (Integer parameterDefense, Integer parameterMiddle, Integer parameterAttack){
		if (parameterDefense + parameterMiddle + parameterAttack != 10) 		
			log.error("Formación incorrecta, deben ser 10 jugadores");
		this.defense = parameterDefense;
		this.middle = parameterMiddle;
		this.attack = parameterAttack;		
	}
	
	public FormationImpl (){		
		this.defense = 0;
		this.middle = 0;
		this.attack = 0;		
	}


	public Integer getDefense() {
		return defense;
	}

	
	public Integer getMiddle() {
		return middle;
	}
	
	
	public Integer getAttack() {
		return attack;
	}

	public void setDefense(Integer parameterDefenseSetDefense) {
		this.defense=parameterDefenseSetDefense;		
	}

	
	public void setMiddle(Integer parameterMiddleSetMiddle) {
		this.middle=parameterMiddleSetMiddle;		
	}

	
	public void setAttack(Integer parameterAttacksetAttack) {
		this.attack=parameterAttacksetAttack;
		
	}

	@Override
	public boolean isEspecialFormation() {
		return (defense==4 && middle==5 && attack==1) || (defense == 5 && middle == 4 && attack == 1);
	}
	
	public String toString(){
		return getDefense() + "-" + getMiddle() + "-" + getAttack();
	}
}
