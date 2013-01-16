package stadium;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import team.PlayerControlledTeam;

/**
 * @author  Jesus Manuel
 */
public class StadiumImpl implements Stadium{


	private final List<StadiumZone> areas;
	private final List<StadiumZone> areasBuilding;
	private Map<StadiumZone,Integer> statisticsAreas; 
	private Integer capacity;
	
	private final PlayerControlledTeam myTeam;
	private Double price = 10.0;
	
	ShowStadium showStadium;
	
	private final static Integer WEEK_NEED_BUILD = 7;
	
	public StadiumImpl(PlayerControlledTeam t, ShowStadium show){
		myTeam = t;
		
		areas = new LinkedList<StadiumZone>();
		areasBuilding = new LinkedList<StadiumZone>();
		statisticsAreas = new HashMap<StadiumZone,Integer>();
		
		StadiumZone z1, z2, z3, z4;
		
		z1 = new StadiumZoneImpl(SZLocation.NORTH,SZMode.SEATED, SZTier.FIRST);
		z2 = new StadiumZoneImpl(SZLocation.SOUTH,SZMode.STANDING, SZTier.FIRST);
		z3 = new StadiumZoneImpl(SZLocation.EAST,SZMode.GRASS, SZTier.FIRST);
		z4 = new StadiumZoneImpl(SZLocation.WEST,SZMode.GRASS, SZTier.FIRST);

		areas.add(z1);
		areas.add(z2);
		areas.add(z3);
		areas.add(z4);
		
		capacity = 0;
		showStadium= show;
		
		for(StadiumZone s: areas)
			capacity += s.getCapacity();
	}
	
/**
 * en este metodo obtenemos la configuración del estadio, cada posicion identifica un area y si esta activa o n
 */
	
	public List<StadiumZone> getAreas() {
		return areas;
	}
/**< Aqui cambiamos anadimos un area nueva al estadio */ 
	public void addArea(SZLocation location, SZMode mode) {
		if(mode != SZMode.GRASS)
		{
			SZTier tier = null;
			StadiumZone sz;
			
			if(location.equals(SZLocation.CORNER))
			{
				sz = findSZ(location, SZTier.INNER_CORNER);
				
				if(sz == null)
				{
					tier = SZTier.INNER_CORNER;
				}
				else if(findSZ(location, SZTier.OUTER_CONNER) == null)
				{
					tier = SZTier.OUTER_CONNER;					
				}
				
				else 
					showStadium.maxTier();
				
			}
			else
			{
				sz = findSZ(location, SZTier.FIRST);
				
				if(sz == null || sz.getMode() == SZMode.GRASS)
				{
					tier = SZTier.FIRST;
				}
				else if(findSZ(location, SZTier.SECOND) == null)
				{
					tier = SZTier.SECOND;					
				}
				else if(findSZ(location, SZTier.THIRD) == null)
				{
					tier = SZTier.THIRD;					
				}
				
				else 
					showStadium.maxTier();
			}
			
			if(tier != null){
				sz = findBuildingSZ(location, tier);
				
				if(sz == null)
				{
					sz = new StadiumZoneImpl(location, mode, tier);
					
					if(myTeam.getFinances() >= sz.getCost()){
						myTeam.setFinancesCost(sz.getCost());
						areasBuilding.add(sz);	
						statisticsAreas.put(sz,WEEK_NEED_BUILD);
					}
					else
						showStadium.noMoney();
				}
			}
		}
	}
	
	private StadiumZone findBuildingSZ(SZLocation location, SZTier tier){
			StadiumZone res = null;
			for(StadiumZone sz:areasBuilding){
				if(sz.getLocation()==location && sz.getTier()==tier){
					res=sz;
					break;
				}
			}
			return res;
		}	
	
/**< Como en findPlayer en PlayerIMPL */
	public StadiumZone findSZ(SZLocation location, SZTier tier){
		StadiumZone res = null;
		for(StadiumZone sz:areas){
			if(sz.getLocation()==location && sz.getTier()==tier){
				res=sz;
				break;
			}
		}
		return res;
	}
	
	
/**< Al finalizar la temporada se terminan automaticamente todos las construcciones en curso */
	public void finishStadium(PlayerControlledTeam team) {
		areas.addAll(areasBuilding);
		statisticsAreas=new HashMap<StadiumZone, Integer>();
		showStadium.finishBuild();
	}
	
/**< Comprobamos semanalmente como van las construcciones del estadio */
	public void updateStadium() {
		StadiumZone updateZone=areasBuilding.get(0);
		statisticsAreas.put(updateZone, statisticsAreas.get(updateZone) - 1);
		Integer weektc = statisticsAreas.get(updateZone);
		if(weektc == 0){
			if(updateZone.getLocation()!=SZLocation.CORNER &&
				updateZone.getTier()==SZTier.FIRST)
				areas.remove(areas.indexOf(findSZ(updateZone.getLocation(), SZMode.GRASS)));
			areas.add(updateZone);			
			areasBuilding.remove(0);	
			statisticsAreas.remove(updateZone);
		}
	}
	
/**< Para localizar un area dando su modo y localización */
	private StadiumZone findSZ(SZLocation location, SZMode mode) {
		StadiumZone res = null;
		for(StadiumZone sz:areas){
			if(sz.getLocation()==location && sz.getMode()==mode){
				res=sz;
				break;
			}
		}
		return res;
	}
	
/**< Obtener los beneficios de un partido*/
	public Double getBenifit(){
		Integer cap=0;
		for(StadiumZone sz:getAreas()){
				cap=+sz.getCapacity();
		}
		return (double)cap*price;
	}

	
/**< Comprobar si hay reformas en curso */
	public boolean checkReforms() {
		boolean res = false;
		
		if(statisticsAreas.keySet().size() > 0)
			res = true;
		
		return res;
	}
	
/** Si queremos hacer un cambio al tipo de un area en concreto y 
 *cambiarlo de de pie a sentado o viceversa la buscamos creamos el nuevo
 *area y lo mandamos a construir a statisticsAreas y despues la borramos de 
 *areas, ya que ahora es una zona en construcción 
 */
	public void modifyArea(SZLocation location, SZMode newMode, SZTier tier){
		for(StadiumZone sz:areas){
			if(sz.getLocation()==location && sz.getTier()==tier){
				StadiumZone oldZone=new StadiumZoneImpl(location,sz.getMode(),tier);
				StadiumZone newZoneModify = new StadiumZoneImpl(location,newMode,tier);
				if(statisticsAreas.containsKey(newZoneModify))
					break;
				else{
					statisticsAreas.put(newZoneModify, WEEK_NEED_BUILD);
					areas.remove(oldZone);
				break;
				}
			}
		}
	}
	
	
	public Integer getCapacity() {

		this.capacity = 0;
		
		for(StadiumZone s: areas)
			this.capacity += s.getCapacity();
		
		return capacity;
	}
	
	/* ************************************************* */
	/* A partir de aquí sería lo que fue StadiumFinances */
	/* ************************************************* */
	
/**< Primero debemos comprobar en que division estamos y si se juega como local */
		private static Integer division;
		private static Boolean derby;
		
		public Boolean isDerby() {
			return derby;
		}
		public Integer getDivision(PlayerControlledTeam t){
			return division;
		}
		
/**< Aqui se calculan las ganancias de un partido por venta de localidades */
		public Integer getBenifit(PlayerControlledTeam t){
			Integer cap=0;
			Integer div= getDivision(t);
			Boolean derb=isDerby();
			Integer nOT=financesForDivision(div,t,derb);
			for(StadiumZone s: areas)
				cap += s.getCapacity();
			Integer res=0;
/**< Tenemos que comprobar si caben menos perosnas de las que podemos meter y en funcion de ello aplicar las ganacias */
			if(cap>nOT)
				res=nOT*10;
			else
				res=cap*10;
			return res;
		}
				
/**< Las finanzas son diferentes en función de la division en la que se este */
		public Integer financesForDivision(Integer div,PlayerControlledTeam t, Boolean derb){
			Integer numberOT = 0;
				if(div==1){
					numberOT=+30000+5000;
					/**< Con el siguiente if comprobamos si tiene racha */
					if(t.getStreak())
						numberOT=+2000;
					/**< Si quedo el primero de primera */
					if(t.lastYearChampions())
						numberOT=+10000;
					/**< Si ascendio */
					if(t.runnerUp())
						numberOT=+6000;				
					/**< Si se mantiene en la division */
					if(t.keepDivision())
						numberOT=+5000;
					/**< Si gano la temporada anterior */
					if(t.winLastSeason())
						numberOT=+10000;
				}else if(div==2){
					if(t.relegated()){
						numberOT=+8000+3000;
						if(t.getStreak())
							numberOT=+1500;
					}else{
					numberOT=+20000+3000;
					if(t.getStreak())
						numberOT=+1500;
					if(t.runnerUp())
						numberOT=+4000;
					if(t.winLastSeason())
						numberOT=+7000;
					}
				}else if(div==3){
					if(t.relegated()){
						numberOT=+6000+2000;
						if(t.getStreak())
							numberOT=+1000;
					}else{
					numberOT=+15000+2000;
					if(t.getStreak())
						numberOT=+1000;
					if(t.runnerUp())
						numberOT=+2000;				
					if(t.winLastSeason())
						numberOT=+4000;
					}
				}else if(div==4){
					if(t.relegated()){
						numberOT=+5000+1000;
						if(t.getStreak())
							numberOT=500;
					}else{
					numberOT=+10000+1000;
					if(t.getStreak())
						numberOT=+500;
					}
				}return numberOT;
		}
}