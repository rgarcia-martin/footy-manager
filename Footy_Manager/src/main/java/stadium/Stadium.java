package stadium;

import java.util.List;

import team.PlayerControlledTeam;
/**
 * 
 * @author Jesus Manuel
 * @biref Estadio, sus Zonas, areas,Construccion...
 *
 */
public interface Stadium {

	/**< Devuelve las zonas construidas actualmente */
	public abstract List<StadiumZone> getAreas();

	/**< Con este metodo se anade un nuevo area este ya creada o no */ 
	public abstract void addArea(SZLocation location, SZMode mode);

	/**< Al finalizar la temporada se terminan automaticamente todo las construcciones en curso*/
	public abstract void finishStadium(PlayerControlledTeam team);

	/**< Comprobamos semanalmente como van las construcciones del estadio */
	public abstract void updateStadium();

	/**< Obtener los beneficios de un partido */
	public abstract Double getBenifit();

	/**< Comprobar si hay reformas en curso */
	public abstract boolean checkReforms();

	/**< modificar el modo de un area */
	public abstract void modifyArea(SZLocation location, SZMode newMode, SZTier tier);

	/**< Obtener los beneficios de un partido */
	public abstract Integer getBenifit(PlayerControlledTeam t);

	public abstract Integer financesForDivision(Integer division,
			PlayerControlledTeam t, Boolean derby);
	
	Integer getCapacity();
	
	StadiumZone findSZ(SZLocation location, SZTier tier);

	
	/**< METODOS A DESARROLLAR EN LAS CLASES QUE SEA PRECISO */ 
	public abstract Integer getDivision(PlayerControlledTeam t);
	public abstract Boolean isDerby();
}