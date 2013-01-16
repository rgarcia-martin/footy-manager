package player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import team.PlayerControlledTeam;

import employer.EmployerTypes;
import exceptions.*;

/**
 * @author  Rafael
 */
public class PlayerImpl implements Player {
	

	private String name;
	
	private Integer age;
	
	private Double ability;
	
	private FormTypes form;
	
	private Integer number;
	
	private boolean injured;
	
	private Integer timeLimitInjured;
	
	private List<PositionTypes> positions;
	
	private boolean potential;
	
	private Map<StatisticsTypes, List<Integer>> statistics;

	private TrainingTypes actualTraining;
	private TrainingTypes preActualTraining;
	
	private Map<TrainingTypes, Integer> statsTraining;
	
	private PlayerControlledTeam myTeam = null;
	private boolean desactivatedUpdatesForm = false;
	
	private static final Integer WEEK_FRM = 3; /**< Semanas necesarias para completar entrenamiento FRM */
	private static final Integer WEEK_ABL = 10; /**^< Idem para ABL*/
	private static final Integer WEEK_FIT = 1; /**< Idem para FIT*/
	private static final Integer WEEK_RST = 1; /**< Idem para RST*/
	private static final Integer WEEK_POS_NEAR = 8; /**< Idem para entramiento de Posiciones Cercanas a las actuales*/
	private static final Integer WEEK_POS_AWAY = 12; /**< Idem para entrenamiento de Posiciones Lejanas.*/
	
	private static final Double ABL_MIN = 5.0; /**< Habilidad Minima */
	private static final Double ABL_MAX = 10.0; /**< Habilidad Maxima */
	
	private static final Integer AGE_MIN = 16; /**< A�os Minimos */
	
	private static final Integer POS_STRING_NAME = 0;
	private static final Integer POS_STRING_AGE = 1; /**< Posicion de recepcion en el Constructor de String para A�o */
	private static final Integer POS_STRING_ABI = 2; /**< Idem para Habilidad */
	private static final Integer POS_STRING_FORM = 3; /**<Idem para Forma fisica Inicial */
	private static final Integer POS_STRING_NUM = 4; /**<  Idem para Numero */
	private static final Integer POS_STRING_POS = 5; /**< Idem para Posicion Inicial */
	//private static final Integer POS_STRING_PRICE = 5;

	private static final Integer UPD_IMPROVE_FORM = 1; /**< Actualizacion para mejorar FORM */
	private static final Integer UPD_WORSEN_FORM = -1; /**< Actualizacion para empeorar FORM */
	private static final Integer UPD_INJURED_2WEEK = -1; /**< Idem para cada 2 semanas de estar Lesionado (FIT)*/
	private static final Double UPD_ABI = 0.5; /**< Idem para cada entrenamiento de Habilidad */

	private static final Integer MIN_MATCHES_PLAYED = 3; /**< Idem para cada entrenamiento de Habilidad*/
	private static final Integer NUM_GOALS_HATTRICK = 3; /**<Idem para cada entrenamiento de Habilidad */
	private static final Integer MIN_NUM_GOALS_CONCEDED = 4; /**<Idem para cada entrenamiento de Habilidad */
	private static final Integer MIN_SUM_GOALS_CONCEDED = 4; /**<Idem para cada entrenamiento de Habilidad */
	
	//private Integer price;			//rafa estasdos lineas nuevas y los metodos marcados abajo
	//private boolean isOnTransferList;	//
	
	public PlayerImpl(String nameIncoming, Integer ageIncoming, Double abilityIncoming, FormTypes formIncoming, 
			PositionTypes positionInitialIncoming, Integer numberIncoming, PlayerControlledTeam teamIncoming)
	{
		createPlayer(nameIncoming, ageIncoming, abilityIncoming, formIncoming, positionInitialIncoming);
		myTeam = teamIncoming;
		setNumber(numberIncoming);
	}
	
	public PlayerImpl(String nameIncoming, Integer ageIncoming, Double abilityIncoming, 
			FormTypes formIncoming, PositionTypes positionInitialIncoming)
	{
		createPlayer(nameIncoming, ageIncoming, abilityIncoming, formIncoming, positionInitialIncoming);
	}
	
	public PlayerImpl(String linea, PlayerControlledTeam teamIncoming)
	{
		List<String> lista= new LinkedList<String>();
        StringTokenizer st=new StringTokenizer(linea," ");
        while(st.hasMoreTokens()){
        	lista.add(st.nextToken());
        }

        String nameIncoming = lista.get(POS_STRING_NAME);
        Integer ageIncoming = Integer.valueOf(lista.get(POS_STRING_AGE));
        Double abiIncoming = Double.valueOf(lista.get(POS_STRING_ABI));
        FormTypes formIncoming = FormTypes.valueOf(lista.get(POS_STRING_FORM));
        PositionTypes posIncoming = PositionTypes.valueOf(lista.get(POS_STRING_POS));
        Integer numberIncoming = Integer.valueOf(lista.get(POS_STRING_NUM));

		createPlayer(nameIncoming, ageIncoming, abiIncoming, formIncoming, posIncoming);
		myTeam = teamIncoming;
		setNumber(numberIncoming);
	}
	
	public PlayerImpl(String linea)
	{
		List<String> lista= new LinkedList<String>();
	    StringTokenizer st=new StringTokenizer(linea," ");
	    while(st.hasMoreTokens()){
	    	lista.add(st.nextToken());
	    }

        String nameIncoming = lista.get(POS_STRING_NAME);
        Integer ageIncoming = new Integer(lista.get(POS_STRING_AGE));
        Double abiIncoming = Double.valueOf(lista.get(POS_STRING_ABI));
        FormTypes formIncoming = FormTypes.valueOf(lista.get(POS_STRING_FORM));
        PositionTypes posIncoming = PositionTypes.valueOf(lista.get(POS_STRING_POS));

		createPlayer(nameIncoming, ageIncoming, abiIncoming, formIncoming, posIncoming);
	}
	
	private void createPlayer(String nameIncoming, Integer ageIncoming, Double abilityIncoming, 
			FormTypes formIncoming, PositionTypes positionInitialIncoming)
	{
		if(ageIncoming < AGE_MIN){
			throw new PlayerIllegalAgeException("Anyo del jugador incorrecto");
		}
		
		if(abilityIncoming < ABL_MIN || abilityIncoming > ABL_MAX){
			throw new PlayerIllegalAbilityException("Habilidad incorrecta");
		}
		
		positions = new LinkedList<PositionTypes>();
		statistics = new HashMap<StatisticsTypes, List<Integer>>();
		statsTraining = new HashMap<TrainingTypes, Integer>();

		statistics.put(StatisticsTypes.G, new LinkedList<Integer>());
		statistics.put(StatisticsTypes.GC, new LinkedList<Integer>());
		statistics.put(StatisticsTypes.AS, new LinkedList<Integer>());
		
		name = nameIncoming;
		age = ageIncoming;
		ability = abilityIncoming;
		form = formIncoming;
		injured = false;
		positions.add(positionInitialIncoming);		
		actualTraining = TrainingTypes.RST;
	}
	
	/*public boolean isOnTransferList(){				//nuevo
		return isOnTransferList;
	}
	public void setIsOnTransferList(boolean isOn){	//nuevo
		isOnTransferList=isOn;
	}
	
	public Integer getPrice(){						//nuevo
		return price;
	}
	*/

	public void activateUpdatesForm()
	{
		desactivatedUpdatesForm = false;
	}
	
	public void desactivateUpdatesForm()
	{
		desactivatedUpdatesForm = true;
	}
	

	public Integer getNumber() {
		return number;
	}
	
	public PlayerControlledTeam getTeam()
	{
		return myTeam;
	}
	
	public void setTeam(PlayerControlledTeam t)
	{
		myTeam = t;
	}

	
	public void setNumber(Integer numberIncoming) {
		if(myTeam == null){
			throw new UnsupportedOperationException("Operacion no permitida sin especificar un equipo");
		}
		
		Predicate<Player> p1 = new PredicatePlayerWithNumber(numberIncoming);
		
		//Buscar hueco de numericos.
		
		boolean b = Iterables.any(getTeam().getPlayersInTeam().getPlayers(), p1);
		
		if(b){
			List<Integer> i1 = new LinkedList<Integer>();
			Iterable<Integer> iI = Iterables.transform(getTeam().getPlayersInTeam().getPlayers(), new FunctionPlayerToNumber());
			
			for(Integer numberPlayer: iI){
				i1.add(numberPlayer);
			}
			
			
			Collections.sort(i1);
			
			Integer n = -1;
			
			int i;
			
			for(i = 0; i < i1.size(); i++){
				if(i1.get(i) > i+1){
					n = i+1;
					break;
				}
			}
			
			if(n < 0)
				n = getTeam().getPlayersInTeam().getPlayers().get(i-1).getNumber() + 1;
			
			number = n;
		}
		else {
			number = numberIncoming;
		}
	}

	public boolean isInjured() {
		return injured;
	}

	public void setInjured(Integer time) {	/**< Mientras no este lesionado el jugador y se debe indicar el tiempo de lesion y no este entrenando RST. */
		if(!isInjured() && !getActualTraining().equals(TrainingTypes.RST)){
			timeLimitInjured = time; 
			preActualTraining = actualTraining;
			actualTraining = TrainingTypes.FIT;			
			injured = true;
		}
		
	}

	public Integer getTimeLimitInjured() {
		return timeLimitInjured;
	}

	public TrainingTypes getActualTraining() {
		return actualTraining;
	}
	
	public void setActualTraining(TrainingTypes actualTrainingIncoming)
	{
		boolean trainingPos = false;
		
		if(actualTrainingIncoming.equals(TrainingTypes.CB) || actualTrainingIncoming.equals(TrainingTypes.SB) || 
				actualTrainingIncoming.equals(TrainingTypes.CM) || actualTrainingIncoming.equals(TrainingTypes.SM) || 
				actualTrainingIncoming.equals(TrainingTypes.CF)){
			trainingPos = true;
		}
		
		if(!getTeam().getEmployersOfTeam().existsEmployer(EmployerTypes.HC) && trainingPos){
			throw new PlayerTeamNotHaveHeadCoach("Operacion no permitida: No se dispone de HeadCoach.");
		}
			
		if(isInjured()){
			throw new PlayerInjuredIllegalChangeOfTraining("El jugador esta lesionado, cambio de tipo de entrenamiento no permitido.");
		}
		
		actualTraining = actualTrainingIncoming;
	}

	public Map<TrainingTypes, Integer> getStatsTraining() {
		return statsTraining;
	}

	
	public Integer getAge() {
		return age;
	}

	
	public Double getAbility() {
		return ability;
	}

	public void updateAbility(Double abi){
		Double res = ability + abi;
		
		if(res >= ABL_MIN && res <= ABL_MAX){
			ability = res;
		}
	}

	
	public FormTypes getForm() {
		return form;
	}

	public void updateForm(Integer i){ /**< Positivo aumenta un nivel de forma, negativo disminuye */
		
		if(i >= 0)
		{		
					if(this.form.equals(FormTypes.MINUS_MINUS)){		this.form = FormTypes.MINUS;}
			else 	if(this.form.equals(FormTypes.MINUS)){				this.form = FormTypes.PLUS;}
			else 	if(this.form.equals(FormTypes.PLUS)){				this.form = FormTypes.PLUS_PLUS;}
		}
		else
		{		
					if(this.form.equals(FormTypes.MINUS)){ 				this.form = FormTypes.MINUS_MINUS;}
			else 	if(this.form.equals(FormTypes.PLUS)){				this.form = FormTypes.MINUS;}
			else 	if(this.form.equals(FormTypes.PLUS_PLUS)){			this.form = FormTypes.PLUS;}
		}
	}


	public List<PositionTypes> getPositions() {
		return positions;
	}

	public void addPosition(PositionTypes pos){
		positions.add(pos);
	}
	
	public void addPosition(Collection<PositionTypes> pos){
		positions.addAll(pos);
	}

	
	public Map<StatisticsTypes, List<Integer>> getStatistics() {
		return statistics;
	}

	private void updateTrainingFRM(){
		updateForm(UPD_IMPROVE_FORM);
	}

	private void updateTrainingABL(){
		updateAbility(UPD_ABI);
	}

	private void updateTrainingFIT(){
		Integer timeInjured = statsTraining.get(TrainingTypes.FIT);
		
		if(timeInjured % 2 == 0){
			updateForm(UPD_INJURED_2WEEK);
		}
		
		if(timeInjured.equals(getTimeLimitInjured())){
			statsTraining.remove(actualTraining);
			actualTraining = preActualTraining;
			this.injured = false;
		}
	}

	private void updateTrainingRST(){
		/**< no se hace nada, el jugador no puede resultar lesionado. :D */
	}

	private void updateTraining(Integer weekNeed){
		if(weekNeed < 0)
			throw new IllegalArgumentException("El numero de semanas a verificar es erroneo");
		
		if((this.actualTraining.equals(TrainingTypes.CB) || 
				this.actualTraining.equals(TrainingTypes.SB) || 
				this.actualTraining.equals(TrainingTypes.CM) || 
				this.actualTraining.equals(TrainingTypes.SM) || 
				this.actualTraining.equals(TrainingTypes.CF)) && (! myTeam.getEmployersOfTeam().existsEmployer(EmployerTypes.HC)))
		{
			actualTraining = TrainingTypes.RST;
		}
		
		if(statsTraining.get(actualTraining) == weekNeed && ! actualTraining.equals(TrainingTypes.FIT))
		{	
			statsTraining.remove(actualTraining);
			
					if(this.actualTraining.equals(TrainingTypes.FRM)){ 	updateTrainingFRM();}
			else 	if(this.actualTraining.equals(TrainingTypes.ABL)){	updateTrainingABL();}
			else 	if(this.actualTraining.equals(TrainingTypes.RST)){	updateTrainingRST();}
			else 	if(	this.actualTraining.equals(TrainingTypes.CB) || 
						this.actualTraining.equals(TrainingTypes.SB) || 
						this.actualTraining.equals(TrainingTypes.CM) || 
						this.actualTraining.equals(TrainingTypes.SM) || 
						this.actualTraining.equals(TrainingTypes.CF)){	
				addPosition(PositionTypes.valueOf(actualTraining.toString()));
			}
		}
		else if(actualTraining.equals(TrainingTypes.FIT))
		{
			/**< Apartado de Fitness */
			updateTrainingFIT();
		}
	}
	
	private Integer calcWeekNeed(){
		Integer weekNeed = 0;
		
				if(actualTraining.equals(TrainingTypes.FRM)){	weekNeed = WEEK_FRM;}
		else	if(actualTraining.equals(TrainingTypes.ABL)){
			weekNeed = WEEK_ABL;
			
			if(getTeam().getEmployersOfTeam().existsEmployer(EmployerTypes.AM)){
				weekNeed--; /**< Se resta una semana si el equipo tiene contratado Assistan Manager */
			}
		}
		else	if(actualTraining.equals(TrainingTypes.FIT)){	
			weekNeed = WEEK_FIT;

			if(getTeam().getEmployersOfTeam().existsEmployer(EmployerTypes.CD)){
				weekNeed--; /**< Se resta una semana si el equipo tiene contratado Club Doctor */
			}
		}
		else	if(actualTraining.equals(TrainingTypes.RST)){	weekNeed = WEEK_RST;}
		else	if(actualTraining.equals(TrainingTypes.CB)){
			if(positions.contains(PositionTypes.SB) || positions.contains(PositionTypes.CM)){
				weekNeed = WEEK_POS_NEAR;
			}
			else{
				weekNeed = WEEK_POS_AWAY;
			}
		}
		else	if(actualTraining.equals(TrainingTypes.SB)){
			if(positions.contains(PositionTypes.CB) || positions.contains(PositionTypes.SM)){
				weekNeed = WEEK_POS_NEAR;
			}
			else{
				weekNeed = WEEK_POS_AWAY;
			}
		}
		else	if(actualTraining.equals(TrainingTypes.CM)){
			if(positions.contains(PositionTypes.CB) || positions.contains(PositionTypes.SM) || positions.contains(PositionTypes.CF)){
				weekNeed = WEEK_POS_NEAR;
			}
			else{
				weekNeed = WEEK_POS_AWAY;
			}
		}
		else	if(actualTraining.equals(TrainingTypes.SM)){
			if(positions.contains(PositionTypes.SB) || positions.contains(PositionTypes.CM)){
				weekNeed = WEEK_POS_NEAR;
			}
			else{
				weekNeed = WEEK_POS_AWAY;
			}
		}
		else	if(actualTraining.equals(TrainingTypes.CF)){
			if(positions.contains(PositionTypes.CM)){
				weekNeed = WEEK_POS_NEAR;
			}
			else{
				weekNeed = WEEK_POS_AWAY;
			}
		}
		
		return weekNeed;
	}
	
	private boolean playerCFAnd3WeeksWithoutGoals() /**< Si Jugador es CF y no ha marcado en 3 partidos seguidos: Empeorar */
	{
		boolean res = false;
		
		List<Integer> goals = getStatistics().get(StatisticsTypes.G);
		
		if((getPositions().contains(PositionTypes.CF)) && goals.size() >= MIN_MATCHES_PLAYED)
		{
			Integer lastWeek = goals.size() - 1;
			
			if(goals.get(lastWeek) == 0 && goals.get(lastWeek - 1) == 0 && goals.get(lastWeek - 2) == 0){
				res = true;
			}
		}
		
		return res;
	}
	
	private boolean playerHatTrick() /**< Si Jugador a marcado HatTrick (3 goles en el ultimo partido): Mejorar */
	{
		boolean res = false;
		
		List<Integer> goals = getStatistics().get(StatisticsTypes.G);
	
		Integer lastWeek = goals.size() - 1;
		
		if(goals.get(lastWeek) >= NUM_GOALS_HATTRICK){
			res = true;
		}
		
		return res;
	}
	
	private boolean player1GoalIn3Weeks() /**< Si Jugador marco 1 gol en 3 partidos seguidos: Mejorar */
	{
		boolean res = false;
		
		List<Integer> goals = getStatistics().get(StatisticsTypes.G);
		
		if(goals.size() >= MIN_MATCHES_PLAYED)
		{
			Integer lastWeek = goals.size() - 1;
			
			if(goals.get(lastWeek) >= 1 && goals.get(lastWeek - 1) >= 1 && goals.get(lastWeek - 2) >= 1){
				res = true;
			}
		}
		
		return res;
	}
	
	private boolean playerGkCbSb4GoalsConcededsInLastWeek() /**< Si Jugador GK/CB/SB le marcan 4 goles en el ultimo partido: Empeorar */
	{
		boolean res = false;

		if(getPositions().contains(PositionTypes.GK) || getPositions().contains(PositionTypes.CB) || getPositions().contains(PositionTypes.SB))
		{
			List<Integer> goalsConceded = getStatistics().get(StatisticsTypes.GC);
			Integer lastWeek = goalsConceded.size() - 1;
			
			if(goalsConceded.get(lastWeek) >= MIN_NUM_GOALS_CONCEDED){				res = true;	}
		}

		return res;
	}
	
	private boolean playerGkCbSbLessThan4GoalsConcededsIn3LastWeek() /**< Si Jugador GK/CB/SB le marcan menos de 4 goles en los ultimos 3 partidos: Mejorar*/
	{
		boolean res = false;
		
		List<Integer> goalsConceded = getStatistics().get(StatisticsTypes.GC);
		
		if((getPositions().contains(PositionTypes.GK) || getPositions().contains(PositionTypes.CB) || getPositions().contains(PositionTypes.SB)) && goalsConceded.size() >= MIN_MATCHES_PLAYED)
		{
			Integer lastWeek = goalsConceded.size() - 1;
			
			if(goalsConceded.get(lastWeek) + goalsConceded.get(lastWeek - 1) + goalsConceded.get(lastWeek - 2) < MIN_SUM_GOALS_CONCEDED){
				res = true;
			}
		}
		
		return res;
	}
	
	private boolean playerCmSmNotAssistanceOrGoalIn3LastWeek() /**< Si Jugador CM/SM No asiste o marca en los ultimos 3 partidos: Empeorar */
	{
		boolean res = false;

		List<Integer> goals = getStatistics().get(StatisticsTypes.G);
		List<Integer> assistances = getStatistics().get(StatisticsTypes.AS);
		
		if((getPositions().contains(PositionTypes.CM) || getPositions().contains(PositionTypes.SM)) && assistances.size() >= MIN_MATCHES_PLAYED && goals.size() >= MIN_MATCHES_PLAYED)
		{
			Integer lastWeekG = goals.size() - 1;
			Integer lastWeekAS = assistances.size() - 1;
			
			if((assistances.get(lastWeekAS) == 0 && assistances.get(lastWeekAS - 1) == 0 && assistances.get(lastWeekAS - 2) == 0) || (goals.get(lastWeekG) == 0 && goals.get(lastWeekG - 1) == 0 && goals.get(lastWeekG - 2) == 0)){
				res = true;
			}
		}
		
		return res;
	}
	
	private boolean player3AssistancesIn3LastWeek() /**< Si se realizan 3 asistencias en los 3 ultimos partidos: Mejorar */
	{
		boolean res = false;
		
		List<Integer> assistances = getStatistics().get(StatisticsTypes.AS);
		
		if(assistances.size() >= MIN_MATCHES_PLAYED)
		{
			Integer lastWeek = assistances.size() - 1;
			
			if(assistances.get(lastWeek) >= 1 && assistances.get(lastWeek - 1) >= 1 && assistances.get(lastWeek - 2) >= 1){
				res = true;
			}
		}
		
		return res;
	}

	private void updateWeeklyForm()
	{
		/**
		 *  Si Jugador es CF y no ha marcado en 3 partidos seguidos: Empeorar
		 * Si Jugador a marcado HatTrick (3 goles en el ultimo partido): Mejorar
		 * Si Jugador marco 1 gol en 3 partidos seguidos: Mejorar
		 * Si Jugador GK/CB/SB le marcan 4 goles en el ultimo partido: Empeorar
		 * Si Jugador GK/CB/SB le marcan menos de 4 goles en los ultimos 3 partidos: Mejorar
		 * Si Jugador CM/SM No asiste o marca en los ultimos 3 partidos: Empeorar
		 * Si se realizan 3 asistencias en los 3 ultimos partidos: Mejorar
		 */
		List<Integer> res = new LinkedList<Integer>();
		
		/**Batea de ifs... */
		if(playerCFAnd3WeeksWithoutGoals()){					res.add(UPD_WORSEN_FORM);}
		if(playerHatTrick()){									res.add(UPD_IMPROVE_FORM);}
		if(player1GoalIn3Weeks()){								res.add(UPD_IMPROVE_FORM);}
		if(playerGkCbSb4GoalsConcededsInLastWeek()){			res.add(UPD_WORSEN_FORM);}
		if(playerGkCbSbLessThan4GoalsConcededsIn3LastWeek()){	res.add(UPD_IMPROVE_FORM);}
		if(playerCmSmNotAssistanceOrGoalIn3LastWeek()){			res.add(UPD_WORSEN_FORM);}
		if(player3AssistancesIn3LastWeek()){					res.add(UPD_IMPROVE_FORM);}
		
		/**
		 * Ejecucion de actualizaciones de forma conseguidas por el jugador.
		 */
		if(! desactivatedUpdatesForm)
		{
			for(Integer i: res){
				updateForm(i);
			}
		}
	}

	public void updateWeeklyPlayer(Integer goals, Integer goalsConcededs, Integer assistans) {
		/**		 
		 * 1. Actualizar el entrenamiento del jugador, contabilizando el actualTraining 
		 * (el cual se puede modificar a lo largo de cada turno cuando el jugador desee)
		 * 
		 * 2. Actualizar la forma del jugador dependiendo de varios factores
		 */
		
		if(myTeam == null){
			throw new UnsupportedOperationException("Operacion no permitida sin especificar un equipo");
		}
		
		if(statsTraining.containsKey(actualTraining))
		{
			Integer weeks = statsTraining.get(actualTraining);
			statsTraining.put(actualTraining, weeks + 1);
		}
		else{
			statsTraining.put(actualTraining, 0);
		}
		
		statistics.get(StatisticsTypes.G).add(goals);
		statistics.get(StatisticsTypes.GC).add(goalsConcededs);
		statistics.get(StatisticsTypes.AS).add(assistans);
				
		Integer weekNeed = calcWeekNeed();
		
		updateTraining(weekNeed);
		
		updateWeeklyForm();
	}
	
	public String toString()
	{
		String res;
		
		res = getNumber()+"\t"+getName()+"\tEdad: "+getAge()+"\tHab: "+getAbility()+"\tForma: "+getForm()+"\tPosiciones: "+getPositions().toString()+"\tActualTraining: "+getActualTraining();
		
		if(isInjured()){
			res += " [Lesionado " + getStatsTraining().get(TrainingTypes.FIT) + "/" + getTimeLimitInjured() + " Semana]";
		}
		
		return res;
	}
	
	public boolean equals(Object o)
	{
		boolean res = false;

		if(o instanceof Player)
		{
			Player p = (Player) o;

			if(p.getTeam().equals(myTeam) && p.getNumber().equals(getNumber()))
			{
				res = true;
			}
		}
		
		return res;
	}
	
	public int hashCode()
	{
		return 31 * getNumber().hashCode() + 37 * getTeam().hashCode();
	}

	public void setPositions(List<PositionTypes> positionsIncoming) {
		positions = positionsIncoming;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String nameIncoming) {
		name = nameIncoming;		
	}

	@Override
	public void increaseAge() {
		this.age++;
	}

	@Override
	public boolean getPotential() {
		return potential;
	}

	@Override
	public void setPotential(boolean p) {
		this.potential = p;
		
	}
	
	public static Double getABL_MAX(){
		return ABL_MAX;
	}
}