package vaadin;

import division.ClassificationData;
import division.Division;
import division.DivisionImpl;
import division.DivisionType;
import division.FactoryDivision;
import division.FactoryDivisionImpl;
import division.Fixtures;
import division.FixturesImpl;
import division.MatchesInSeason;
import employer.Employer;
import employer.EmployerImpl;
import employer.EmployerTypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import match.Formation;
import match.FormationImpl;
import match.Match;
import match.MatchImpl;
import match.Squad;
import match.SquadImpl;
import match.StadisticsTeam;
import player.Player;
import player.Players;
import player.PlayersImpl;
import player.PositionTypes;

import sponsor.FactorySponsor;
import sponsor.FactorySponsorImpl;
import sponsor.Sponsor;
import sponsor.SponsorAgreement;
import sponsor.SponsorAgreementImpl;
import sponsor.SponsorValue;
import sponsor.SponsorValueImpl;
import team.FactoryTeam;
import team.FactoryTeamImpl;
import team.PlayerControlledTeam;
import team.Team;

import average.AverageLocation;

import com.vaadin.Application; 
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class VaadinMain extends Application {

	private static final VerticalLayout rootLayout = new VerticalLayout();
	private static final Window root = new Window("Footy Manager", rootLayout);

	private HorizontalLayout top = new HorizontalLayout();
	private HorizontalLayout center = new HorizontalLayout();
	private HorizontalLayout bottom = new HorizontalLayout();

	private HorizontalLayout title = new HorizontalLayout();
	private HorizontalLayout ind_season = new HorizontalLayout();
	private HorizontalLayout ind_season_label1 = new HorizontalLayout();
	private HorizontalLayout ind_season_label2 = new HorizontalLayout();
	private HorizontalLayout ind_season_ind = new HorizontalLayout();

	private HorizontalLayout center_1 = new HorizontalLayout();
	private VerticalLayout center_1_1 = new VerticalLayout();
	private HorizontalLayout center_1_2 = new HorizontalLayout();
	private TabSheet center_1_2_tab = new TabSheet();

	private Table tablaJug = new Table();
	private Form editorJug = new Form();
	
	private static Table tablaTra = new Table();
	private static Form editorTra = new Form();

	private static HorizontalLayout tab_Entrenadores = new HorizontalLayout();
	
	private Label l3 = new Label();
	private ProgressIndicator pi = new ProgressIndicator();

	private List<PlayerControlledTeam> players = new LinkedList<PlayerControlledTeam>();
	private List<Division> division = new LinkedList<Division>();
	private List<Sponsor> sponsors = new LinkedList<Sponsor>();
	
	private Map<Team, Squad> selections = new HashMap<Team,Squad>();
	
	private Iterator<PlayerControlledTeam> it_players;
	
	private static PlayerControlledTeam team;
	private Integer week = 0;	
	private Fixtures fixture;

	private static Label t_l = new Label();
	private static Label f_l = new Label();
	private static Label r_l = new Label();
	private static Label s_l = new Label();
	
	private static final Integer MAX_PLAYERS = 8;
	private static final Integer MAX_TEAMS = 32;
	private static final Integer NUM_DIVISIONS = 4;
	private static final Integer MAX_TEMP_SPONSOR = 2;
	private static final Integer MATCHS_FOR_SEASON = 14;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void chooseSponsor() {
		SponsorValue sv = new SponsorValueImpl();
		
		Integer value = sv.calcValue(team);
		
		final Sponsor s = sponsors.get(value);
		
		final Window winSpon = new Window("Eleccion de Sponsor para " + team.getName(), new VerticalLayout());
		winSpon.setModal(true);
		
		final Select sel = new Select("Años de Contrato");
		for(int i = 0; i < MAX_TEMP_SPONSOR; i++)
		{
			sel.addItem(i+1);
		}
		
		sel.setRequired(true);
		sel.setRequiredError("Debe seleccionar los años de contrato");
		
		Button b = new Button("Aceptar", new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
	
			@Override
			public void buttonClick(ClickEvent event) {
				sel.validate();
				
				SponsorAgreement sa = new SponsorAgreementImpl(s, (Integer) sel.getValue());
				
				team.setSponsorAgreement(sa);
				
				//Cobrar sponsor
				Double inc;
				
				if(team.getSponsorAgreement().getDuration() == 1)
				{
					inc = team.getSponsorAgreement().getSponsor().getOneSeasonCash();
				}
				else
				{
					inc = team.getSponsorAgreement().getSponsor().getTwoSeasonsCash();
				}
				
				team.addIncome(inc);
				
				refresh();
				
				root.removeWindow(winSpon);
				
				fillCenter_1_1();
				fillCenter_1_2();
			}
		});

		winSpon.addComponent(new Label("Sponsor:"));
		winSpon.addComponent(new Label(s.getName()));
		winSpon.addComponent(new Label("Dinero por:"));
		winSpon.addComponent(new Label("1 Año: " + s.getOneSeasonCash()));
		winSpon.addComponent(new Label("2 Año: " + s.getTwoSeasonsCash()));
	
		winSpon.addComponent(sel);
		winSpon.addComponent(b);
		
		root.addWindow(winSpon);
	}

	@Override
	public void init() {
		root.removeAllComponents();
		
		setMainWindow(root);
		
		Window ini = new Window("Bienvenido a FootyManager");
		ini.setModal(true);
		
		setIni1(ini);
				
		root.addWindow(ini);				
	}

	private void setIni1(final Window ini) {
		VerticalLayout ini1 = new VerticalLayout();
		ini1.setSizeFull();

		Label l1 = new Label("Bienvenido a Footy Manager");

		HorizontalLayout h2 = new HorizontalLayout();
		h2.setWidth("100%");
		h2.addComponent(new Label("Creado por Rafael Garcia Martin"));
		
		HorizontalLayout h3 = new HorizontalLayout();
		h3.setWidth("100%");
		h3.addComponent(new Label("Grupo 2"));
		
		HorizontalLayout h4 = new HorizontalLayout();
		h4.setWidth("100%");
		h4.addComponent(new Label("ISG2. ITIG. Curso 11/12"));			
		
		
		Button b1 = new Button("Siguente", new Button.ClickListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				setIni2(ini);				
			}
			
		});

		ini1.addComponent(l1);
		ini1.addComponent(new Label(" "));
		
		ini1.addComponent(h2);
		ini1.addComponent(h3);
		ini1.addComponent(h4);

		h2.setComponentAlignment(h2.getComponent(0), Alignment.MIDDLE_LEFT);
		h3.setComponentAlignment(h3.getComponent(0), Alignment.MIDDLE_RIGHT);
		h4.setComponentAlignment(h4.getComponent(0), Alignment.MIDDLE_RIGHT);
		
		ini1.addComponent(new Label(" "));
		ini1.addComponent(b1);
		
		ini1.setSpacing(true);
		
		ini.addComponent(ini1);

		ini.setHeight("250px");
		ini.setWidth("400px");
	}

	private void setIni2(final Window ini) {
		ini.removeAllComponents();
		
		VerticalLayout ini2 = new VerticalLayout();
		ini2.setSizeFull();
		
		final Select s1 = new Select("Introduzca el Numero de jugadores");
		
		for(int i = 0; i < MAX_PLAYERS; i++)
		{
			s1.addItem(i + 1);
		}
		
		s1.setRequired(true);
		s1.setRequiredError("Debe rellenar el Campo");
		
		Button b1 = new Button("Siguente", new Button.ClickListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				s1.validate();
				
				Integer num = (Integer) s1.getValue();
				
				ini.removeAllComponents();
				setIni3(ini, num);				
			}
			
		});

		ini2.addComponent(s1);
		ini2.addComponent(new Label(" "));
		ini2.addComponent(b1);
		
		ini.addComponent(ini2);
	}
	
	private void setIni3(final Window ini, Integer num) {
		ini.removeAllComponents();
		
		VerticalLayout ini3 = new VerticalLayout();
		ini3.setSizeFull();
		
		final List<TextField> l_tf = new LinkedList<TextField>();
		
		for(int i = 0; i < num; i++)
		{
			TextField tf = new TextField("Nombre del equipo " + (i+1));
			
			tf.setRequired(true);
			tf.setRequiredError("Debe rellenar el nombre del equipo " + (i+1));
			
			l_tf.add(tf);
		}
		
		Button b1 = new Button("Aceptar", new Button.ClickListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				for(TextField tf : l_tf)
				{
					tf.validate();
				}
				
				ini.removeAllComponents();
				launchGame(ini, l_tf);
			}
			
		});

		for(TextField tf: l_tf)
		{
			ini3.addComponent(tf);
		}
		
		ini3.addComponent(new Label(" "));
		ini3.addComponent(b1);
		
		ini.addComponent(ini3);
	}
	
	private void launchGame(Window ini, List<TextField> l_tf){
		(ini.getParent()).removeWindow(ini);
		
		fillPlayers(l_tf);
		fillDivisions();
		fillSponsors();
		fillMatches();
		
		Collections.reverse(players);
				
		fillRoot();
		
		fillTop();
		
		fillCenter();
		
		fillBottom();
	}

	private void fillMatches() {
		fixture = new FixturesImpl(division);
	}

	private void fillSponsors() {
		FactorySponsor factoryS = new FactorySponsorImpl();
		Sponsor s;
		
		while((s = factoryS.getSponsor()) != null)
		{
			sponsors.add(s);
		}
	}

	private void fillDivisions() {
		FactoryDivision factoryD = new FactoryDivisionImpl();
		
		for(int i = 1; i <= NUM_DIVISIONS; i++)
		{
			DivisionType dt = null;
			
					if(i == 1){	dt = DivisionType.PREMIER_DIVISION;	}
			else	if(i == 2){	dt = DivisionType.SECOND_DIVISION;	}
			else	if(i == 3){	dt = DivisionType.THIRD_DIVISION;	}
			else	if(i == 4){	dt = DivisionType.FOURTH_DIVISION;	}
			
			Integer numNPCT = MAX_TEAMS - players.size();
			Double num = ((double) numNPCT / (double) DivisionImpl.TEAMS_PER_DIVISION);
			boolean preg = i <= (num);
			
			if(preg)
			{
				division.add(factoryD.createNewDivisionWithoutPlayers(dt));
			}
			else
			{
				division.add(factoryD.createNewDivisionWithPlayers(dt,players));
			}
		}
	}

	private void fillPlayers(List<TextField> l_tf) {
		FactoryTeam factoryT = new FactoryTeamImpl();
		
		for(TextField t : l_tf)
		{
			players.add(factoryT.createNewPCT((String) t.getValue()));
		}
		
		it_players = players.iterator();
	}

	private void fillRoot(){
		rootLayout.setHeight("100%");
		rootLayout.setWidth("100%");
		
		rootLayout.addComponent(top);
		rootLayout.addComponent(center);
		rootLayout.addComponent(bottom);
	
		top.setWidth("100%");
		
		center.setHeight("100%");
		center.setWidth("100%");
		
		bottom.setWidth("100%");
		
		rootLayout.setExpandRatio(center, 1.0F);
	}

	private void fillTop() {		
		Label l1 = new Label();	
		Label l2 = new Label();
		
		calcInterval();
		
		l1.setValue("Footy Manager");
		l2.setValue("Temporada");
	
		ind_season_label1.addComponent(l2);
		ind_season_label2.addComponent(l3);
		ind_season_ind.addComponent(pi);
	
		ind_season_label1.setMargin(true);
		ind_season_label2.setMargin(true);
		ind_season_ind.setMargin(true);
	
		title.addComponent(l1);		
		title.setMargin(true);
	
		ind_season.addComponent(ind_season_label1);
		ind_season.addComponent(ind_season_label2);
		ind_season.addComponent(ind_season_ind);
	
		ind_season.setComponentAlignment(ind_season_label1, Alignment.MIDDLE_CENTER);
		ind_season.setComponentAlignment(ind_season_label2, Alignment.MIDDLE_CENTER);
		ind_season.setComponentAlignment(ind_season_ind, Alignment.MIDDLE_CENTER);
		
		top.addComponent(title);
		top.addComponent(ind_season);
	
		top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		top.setComponentAlignment(ind_season, Alignment.MIDDLE_RIGHT);
		
		top.setExpandRatio(title, 1.0F);		
	}

	private void fillCenter() {
		center.addComponent(center_1);

		center_1.setWidth("100%");
		center_1.setHeight("100%");
		
		center_1.addComponent(center_1_1);
		center_1.addComponent(center_1_2);
		
		center_1_1.setWidth("150px"); 

		center_1_2.setWidth("100%"); 
		center_1_2.setHeight("100%");
		
		center_1_2.setMargin(true);

		center_1_2_tab.setHeight("100%");
		center_1_2_tab.setWidth("100%");
		
		center_1_2.addComponent(center_1_2_tab);
		
		center_1.setExpandRatio(center_1_2, 1.0F);
			
		nextTeam(); 
	}

	private void fillBottom() {
		bottom.removeAllComponents();
		
		Button b = new Button();

		HorizontalLayout bottom1 = new HorizontalLayout();
		
		HorizontalLayout button = new HorizontalLayout();
		List<VerticalLayout> l_vl = new LinkedList<VerticalLayout>();
		
		for(int i = 0; i < NUM_DIVISIONS; i++)
		{
			l_vl.add(getVerticalDivision(division.get(i)));
		}
		
		b.addListener(new Button.ClickListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				setFormation();
			}
			
		});
		
		b.setCaption("Fin del Turno");
		
		for(VerticalLayout v: l_vl)
		{
			bottom1.addComponent(v);
		}
		
		button.addComponent(b);
		
		bottom1.addComponent(button);

		bottom1.setWidth("100%");
		
		bottom1.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
		
		bottom.setMargin(true);
		
		bottom.addComponent(bottom1);
	}
	
	private VerticalLayout getVerticalDivision(Division division2) {
		VerticalLayout v = new VerticalLayout();
		
		HorizontalLayout h1 = new HorizontalLayout();
		h1.addComponent(new Label(division2.getCategory().toString()));
		
		v.addComponent(h1);
		
		for(ClassificationData cd : division2.getTeams())
		{
			HorizontalLayout hC = new HorizontalLayout();
			hC.setSpacing(true);

			Label l1 = new Label(cd.getTeam().getName());
			Label l2 = new Label("["+cd.getTeam().getRanking()+"]");
			Label l3 = new Label(cd.getPoints().toString());

			hC.addComponent(l1);
			hC.addComponent(l2);
			hC.addComponent(l3);

			hC.setComponentAlignment(l1, new Alignment(34));
			hC.setComponentAlignment(l2, new Alignment(33));
			hC.setComponentAlignment(l3, new Alignment(33));
			
			hC.setWidth("100%");
			
			v.addComponent(hC);
		}
		
		return v;
	}

	private void nextTeam(){		
		if(it_players.hasNext())
		{
			team = it_players.next();
			
			if(players.size() > 1)
				windowNextTurn();
			
			center_1_2_tab.removeAllComponents();
			center_1_1.removeAllComponents();
			
			if(team.getSponsorAgreement() == null || team.getSponsorAgreement().getTemp() >= team.getSponsorAgreement().getDuration())
			{
				chooseSponsor();
			}
			else
			{
				fillCenter_1_1();
				fillCenter_1_2();
			}
		}
		else
		{			
			updateWeek();
					
			it_players = players.iterator();
			week++;
			calcInterval();
			nextTeam();
		}
	}

	private void updateWeek() {
		playMatchs();
		orderDivisions();
		
		for(PlayerControlledTeam t: players)
		{
			updateTeam(t);
		}
	}

	private void orderDivisions() {
		for(Division d: division)
		{
			d.updateWeeklyDivision();
		}		
		
		fillBottom();
	}

	private void setFormation() {
		final Window selFormation = new Window("Selecciona Formacion para partido " + team.getName(), new VerticalLayout());
		selFormation.setModal(true);
		
		VerticalLayout v = new VerticalLayout();
		
		final List<Formation> l_f = new LinkedList<Formation>();
		Formation f;
		
		//4 4 2
		f = new FormationImpl();
		
		f.setDefense(4);
		f.setMiddle(4);
		f.setAttack(2);
		
		l_f.add(f);		
		
		//4 5 1
		f = new FormationImpl();
		
		f.setDefense(4);
		f.setMiddle(5);
		f.setAttack(1);
		
		l_f.add(f);	
		
		//5 4 1
		f = new FormationImpl();
		
		f.setDefense(5);
		f.setMiddle(4);
		f.setAttack(1);
		
		l_f.add(f);	
		
		//4 3 3
		f = new FormationImpl();
		
		f.setDefense(4);
		f.setMiddle(3);
		f.setAttack(3);
		
		l_f.add(f);	
		
		//4 2 4
		f = new FormationImpl();
		
		f.setDefense(4);
		f.setMiddle(2);
		f.setAttack(4);
		
		l_f.add(f);
		
		//3 4 3
		f = new FormationImpl();
		
		f.setDefense(3);
		f.setMiddle(4);
		f.setAttack(3);
		
		l_f.add(f);
		
		//3 5 2
		f = new FormationImpl();
		
		f.setDefense(3);
		f.setMiddle(5);
		f.setAttack(2);
		
		l_f.add(f);	
		
		final Select s = new Select("Seleccione Formacion");

		Object itemId1 = s.addItem();
		s.setItemCaption(itemId1, l_f.get(0).toString());
		Object itemId2 = s.addItem();
		s.setItemCaption(itemId2, l_f.get(1).toString());
		Object itemId3 = s.addItem();
		s.setItemCaption(itemId3, l_f.get(2).toString());
		Object itemId4 = s.addItem();
		s.setItemCaption(itemId4, l_f.get(3).toString());
		Object itemId5 = s.addItem();
		s.setItemCaption(itemId5, l_f.get(4).toString());
		Object itemId6 = s.addItem();
		s.setItemCaption(itemId6, l_f.get(5).toString());
		Object itemId7 = s.addItem();
		s.setItemCaption(itemId7, l_f.get(6).toString());
		
		s.setRequired(true);
		s.setRequiredError("Debe rellenar el campo");
		
		Button b = new Button("Aceptar", new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				s.validate();
				
				selFormation.getParent().removeWindow(selFormation);
				getSelection(l_f.get((Integer) s.getValue() - 1));
			}
		});	
		
		v.addComponent(s);
		v.addComponent(b);
		v.setSpacing(true);
		
		selFormation.addComponent(v);
		
		selFormation.setHeight("250px");
		selFormation.setWidth("400px");
		
		root.addWindow(selFormation);
	}
	
	private void getSelection(final Formation formation) {
		final Window selSelection = new Window("Seleccione Jugadores para la Formacion " + formation.toString(), new VerticalLayout());
		HorizontalLayout hl = new HorizontalLayout();
		HorizontalLayout h2 = new HorizontalLayout();
		
		final ListSelect lS = new ListSelect("Seleccione para eliminar jugador de la Seleccion");
		
		final Table tP;
				
		final BeanContainer<Integer, BeanPlayer> beans =
			    new BeanContainer<Integer, BeanPlayer>(BeanPlayer.class);
		
		beans.setBeanIdProperty("num");
		
		for(int i = 0; i < team.getPlayersInTeam().getPlayers().size(); i++)
		{
			beans.addBean(new BeanPlayer(i, team));
		}
		
		tP = new Table("Seleccione Jugadores", beans);
		
		tP.setVisibleColumns(new Object[] {"number", "name", "age", "ability", "form", "positions", "actualTraining", "injured"});

		tP.setColumnHeader("number", "Numero");
		tP.setColumnHeader("name", "Nombre");
		tP.setColumnHeader("age", "Edad");
		tP.setColumnHeader("ability", "Habilidad");
		tP.setColumnHeader("form", "Forma");
		tP.setColumnHeader("positions", "Posiciones");
		tP.setColumnHeader("actualTraining", "Entrenamiento Actual");
		tP.setColumnHeader("injured", "Lesionado");
		
		final Players ps = new PlayersImpl();
		
		tP.addListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(tP.getValue() != null)
				{
					Integer id = (Integer) tP.getValue();
				
					Player p = team.getPlayersInTeam().getPlayers().get(id);
					
					if(!p.isInjured() && !ps.getPlayers().contains(p)){
						
						Object idItem = lS.addItem();
						ps.addPlayer(p);
						
						lS.setItemCaption(idItem, p.getName());
					}
				}
			}
		});
		
		lS.addListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(lS.getValue() != null)
				{										
					int pos = 0;
					for(Object o : lS.getItemIds())
					{
						if(o == lS.getValue())
							break;
						
						pos++;
					}
					
					ps.remove(ps.getPlayers().get(pos)); 
					lS.removeItem(lS.getValue());
				}
			}
		});
		
		lS.setRows(12);
		
		Button b_ok = new Button("Aceptar", new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if(validatePlayers(formation, ps))
				{
					Squad sq = new SquadImpl(ps, formation);
					selections.put(team, sq);
					selSelection.getParent().removeWindow(selSelection);
					
					nextTeam();
				}
			}
		});

		Button b_back = new Button("Cambiar de Formacion", new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				selSelection.getParent().removeWindow(selSelection);

				setFormation();
			}
		});

		tP.setSelectable(true); 

		hl.addComponent(tP);
		hl.addComponent(lS);

		hl.setSpacing(true);
		hl.setMargin(true);

		h2.addComponent(b_ok);
		h2.addComponent(b_back);

		h2.setSpacing(true);
		h2.setMargin(true);

		selSelection.addComponent(hl);
		selSelection.addComponent(h2);

		selSelection.setHeight("550px");
		selSelection.setWidth("1100px");
		
		root.addWindow(selSelection);
	}

	private boolean validatePlayers(Formation formation, Players ps) {
		boolean res = true;
		String msg = " ";
		
		Players gk = new PlayersImpl();
		Players at = new PlayersImpl();
		Players md = new PlayersImpl();
		Players df = new PlayersImpl();
		
		if(ps.getPlayers().size() != 11){
			res = false;
			msg = "Debe seleccionar 11 Jugadores";
		} 
		
		gk.addAll(ps.findPlayer(PositionTypes.GK));
		
		if(gk.getPlayers().size() != 1){
			res = false;
			msg = "Debe seleccionar 1 Portero";
		}

		df.addAll(ps.findPlayer(PositionTypes.CB));
		df.addAll(ps.findPlayer(PositionTypes.SB));
		
		if(formation.getDefense() != df.getPlayers().size()){
			res = false;
			msg = "Debe seleccionar "+formation.getDefense()+" Defensas (Dispone de " + df.getPlayers().size() + ")";
		}

		md.addAll(ps.findPlayer(PositionTypes.CM));
		md.addAll(ps.findPlayer(PositionTypes.SM));
		
		if(formation.getMiddle() != md.getPlayers().size()){
			res = false;
			msg = "Debe seleccionar "+formation.getMiddle()+" CentroCampistas (Dispone de " + md.getPlayers().size() + ")";
		}
		
		at.addAll(ps.findPlayer(PositionTypes.CF));
		
		if(formation.getAttack() != at.getPlayers().size()){
			res = false;
			msg = "Debe seleccionar "+formation.getAttack()+" Delanteros (Dispone de " + at.getPlayers().size() + ")";
		}
		
		if(!res)
			launchMen(msg);
		
		return res;
	}
	
	private void playMatchs() {
		for(Division d: division)
		{			
			MatchesInSeason mIs = fixture.getMatchesSeasonForDivision(d.getCategory());
			
			List<Team> m = mIs.getMatchesForWeek(week % MATCHS_FOR_SEASON);
			
			for(int i = 0; i < 4; i++)
			{
				Integer posHome = i * 2;
				Integer posAway = posHome + 1;

				Team home = m.get(posHome);
				Team away = m.get(posAway);
				
				Match mat = null;
				
				if(home instanceof PlayerControlledTeam && away instanceof PlayerControlledTeam){
										
					mat = new MatchImpl(home, selections.get(home), away, selections.get(away));
				}
				else if(home instanceof PlayerControlledTeam || away instanceof PlayerControlledTeam){
					//MvP
					Team machine;
					Team player;
					AverageLocation loc;
					
					if(home instanceof PlayerControlledTeam){
						player = home;
						machine = away;
						loc = AverageLocation.AWAY;
					}
					else
					{
						player = away;
						machine = home;
						loc = AverageLocation.HOME;						
					}
					
					mat = new MatchImpl(machine, loc, player, selections.get(player));
				}
				else{
					//MvM
					mat = new MatchImpl(home, away);
				}

				home.addMatch(mat);
				away.addMatch(mat);
				
				mat.playMatch();
			}
		}
	}

	private void calcInterval() {
		Integer num_season = week / MATCHS_FOR_SEASON;
		Integer n = week % MATCHS_FOR_SEASON;
		Double interval_week = ((double) n / MATCHS_FOR_SEASON);
		
		pi.setValue(interval_week); //Hasta 1.0 - 100% 
		l3.setValue(num_season + 1);
		
		if(n == (MATCHS_FOR_SEASON - 1)){
			endSeason();
		}
	}

	private void endSeason() {
		launchMen("Fin de Temporada");
		
		for(PlayerControlledTeam t: players){
			t.getSponsorAgreement().updateAgreement();
		}
		
		moveDivisions();
		
		fillMatches();
	}

	private void moveDivisions() {
		List<Team> lt1 = getNewTeams(division.get(0), 				null, division.get(1));
		List<Team> lt2 = getNewTeams(division.get(1), 	division.get(0), division.get(2));
		List<Team> lt3 = getNewTeams(division.get(2), 	division.get(1), division.get(3));
		List<Team> lt4 = getNewTeams(division.get(3), 	division.get(2), null);

		division.set(0, new DivisionImpl(DivisionType.PREMIER_DIVISION));
		division.set(1, new DivisionImpl(DivisionType.SECOND_DIVISION));
		division.set(2, new DivisionImpl(DivisionType.THIRD_DIVISION));
		division.set(3, new DivisionImpl(DivisionType.FOURTH_DIVISION));
		
		division.get(0).fillDivision(lt1);
		division.get(1).fillDivision(lt2);
		division.get(2).fillDivision(lt3);
		division.get(3).fillDivision(lt4);
		
		fillBottom();
	}

	private List<Team> getNewTeams(Division d, Division sup, Division inf) {
		List<Team> l = new LinkedList<Team>();
		
		//Descendientes de divisiones superiores
		if(sup != null)
		{
			l.add(sup.getBottom().get(0).getTeam());
			l.add(sup.getBottom().get(1).getTeam());
		}
		else
		{
			l.add(d.getTeams().get(0).getTeam());
			l.add(d.getTeams().get(1).getTeam());
		}

		l.add(d.getTeams().get(2).getTeam());
		l.add(d.getTeams().get(3).getTeam());
		l.add(d.getTeams().get(4).getTeam());
		l.add(d.getTeams().get(5).getTeam());
		
		if(inf != null)
		{
			l.add(inf.getTop().get(0).getTeam());
			l.add(inf.getTop().get(1).getTeam());
		}
		else
		{
			l.add(d.getTeams().get(6).getTeam());
			l.add(d.getTeams().get(7).getTeam());
		}
		
		return l;
	}

	private void windowNextTurn() {		
		launchMen("Turno de " + team.getName());
	}

	private void fillCenter_1_2() {
		HorizontalLayout tab_Jugadores = new HorizontalLayout();
		HorizontalLayout tab_Estadio = new HorizontalLayout();
		HorizontalLayout tab_ListaTransferencia = new HorizontalLayout();
		
		center_1_2_tab.addTab(tab_Jugadores, "Jugadores", null);
		center_1_2_tab.addTab(tab_Entrenadores, "Entrenadores", null);
		center_1_2_tab.addTab(tab_Estadio, "Estadio", null); 
		center_1_2_tab.addTab(tab_ListaTransferencia, "Lista de Transferencia", null);
		
		fillTabJugadores(tab_Jugadores);
		fillTabEntrenadores();
	}

	private static void fillTabEntrenadores() {
		HorizontalLayout tab = new HorizontalLayout();
		HorizontalLayout edi = new HorizontalLayout();

		final BeanContainer<Integer, BeanTrainers> beans = new BeanContainer<Integer, BeanTrainers>(BeanTrainers.class);
		
		beans.setBeanIdProperty("num");
		
		int i = 0;
		
		for(EmployerTypes et: EmployerTypes.values())
		{
			beans.addBean(new BeanTrainers(new EmployerImpl(et), team, i++));
		}
		
		tablaTra = new Table(" ", beans);
		
		editorTra.setFormFieldFactory(new FactoryEmployers());
		
		tablaTra.setVisibleColumns(new Object[]{"name","hired"});

		tablaTra.setColumnHeader("name", "Tipo de Entrenador");
		tablaTra.setColumnHeader("hired", "Contratado");
		
		tablaTra.addListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object id = tablaTra.getValue();
				editorTra.setItemDataSource((id == null ? null : beans.getItem(beans.getIdByIndex((Integer) id)))); 
			}
		});
		
		editorTra.setCaption("Editar Contrato de Empleado");
		
		edi.setMargin(true);

		tab.addComponent(tablaTra);
		edi.addComponent(editorTra);

		tab.setHeight("100%");
		edi.setHeight("100%");

		tab_Entrenadores.setHeight("100%");
		tab_Entrenadores.setWidth("100%");
		
		tab_Entrenadores.removeAllComponents();
		
		tab_Entrenadores.addComponent(tab);
		tab_Entrenadores.addComponent(edi);
		
		editorTra.setHeight("100%");
		tablaTra.setHeight("100%");
		editorTra.setWidth("100%");
		tablaTra.setWidth("100%");
		
		tablaTra.setSelectable(true); 
		
		edi.setWidth("350px");
		
		tab_Entrenadores.setExpandRatio(edi, 1.0F);
	}

	private void fillTabJugadores(HorizontalLayout tab_Jugadores) {
	 	
		final BeanContainer<Integer, BeanPlayer> beans =
			    new BeanContainer<Integer, BeanPlayer>(BeanPlayer.class);

		HorizontalLayout tab = new HorizontalLayout();
		HorizontalLayout edi = new HorizontalLayout();
		
		beans.setBeanIdProperty("num");
		
		for(int i = 0; i < team.getPlayersInTeam().getPlayers().size(); i++)
		{
			beans.addBean(new BeanPlayer(i, team));
		}
		 
		tablaJug = new Table(" ", beans);

		Vector<String> order = new Vector<String>();
		order.add("number");
		order.add("name");
		order.add("actualTraining");
		
		editorJug.setVisibleItemProperties(order);
		
		editorJug.setFormFieldFactory(new FactoryEditJug());
		
		tablaJug.setVisibleColumns(new Object[] {"number", "name", "age", "ability", "form", "positions", "actualTraining", "injured"});

		tablaJug.setColumnHeader("number", "Numero");
		tablaJug.setColumnHeader("name", "Nombre");
		tablaJug.setColumnHeader("age", "Edad");
		tablaJug.setColumnHeader("ability", "Habilidad");
		tablaJug.setColumnHeader("form", "Forma");
		tablaJug.setColumnHeader("positions", "Posiciones");
		tablaJug.setColumnHeader("actualTraining", "Entrenamiento Actual");
		tablaJug.setColumnHeader("injured", "Lesionado");
		
		tablaJug.addListener(new Property.ValueChangeListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object id = tablaJug.getValue();
				editorJug.setItemDataSource((id == null ? null : beans.getItem(beans.getIdByIndex((Integer) id)))); 
			}
			
		});
		
		editorJug.setCaption("Editar Jugador");
		
		edi.setMargin(true);

		tab.addComponent(tablaJug);
		edi.addComponent(editorJug);

		tab.setHeight("100%");
		edi.setHeight("100%");

		tab_Jugadores.setHeight("100%");
		tab_Jugadores.setWidth("100%");
		
		tab_Jugadores.addComponent(tab);
		tab_Jugadores.addComponent(edi);

		editorJug.setHeight("100%");
		tablaJug.setHeight("100%");
		editorJug.setWidth("100%");
		tablaJug.setWidth("100%");
		
		tablaJug.setSelectable(true); 
		
		edi.setWidth("350px");
		
		tab_Jugadores.setExpandRatio(edi, 1.0F);
	}

	private void fillCenter_1_1() {
		HorizontalLayout center_1_1_title = new HorizontalLayout();
		HorizontalLayout center_1_1_title_value = new HorizontalLayout();
		HorizontalLayout center_1_1_finan = new HorizontalLayout();
		HorizontalLayout center_1_1_finan_value = new HorizontalLayout();
		HorizontalLayout center_1_1_rank = new HorizontalLayout();
		HorizontalLayout center_1_1_rank_value = new HorizontalLayout();
		HorizontalLayout center_1_1_sponsor = new HorizontalLayout();
		HorizontalLayout center_1_1_sponsor_value = new HorizontalLayout();
		HorizontalLayout center_1_1_blank = new HorizontalLayout();

		Label t = new Label();
		Label f = new Label();
		Label r = new Label();
		Label s = new Label();
		Label b = new Label();

		t.setValue("Equipo");
		f.setValue("Finanzas");
		r.setValue("Ranking");
		s.setValue("Sponsor");
		
		b.setValue(" ");
		
		refresh();

		center_1_1_title.addComponent(t);
		center_1_1_title_value.addComponent(t_l);
		center_1_1_finan.addComponent(f);
		center_1_1_finan_value.addComponent(f_l);
		center_1_1_rank.addComponent(r);
		center_1_1_rank_value.addComponent(r_l);
		center_1_1_sponsor.addComponent(s);
		center_1_1_sponsor_value.addComponent(s_l);
		center_1_1_blank.addComponent(b);
		
		center_1_1_title.setMargin(true);
		center_1_1_title.setWidth("50%");
		
		center_1_1_finan.setMargin(true);
		center_1_1_finan.setWidth("50%");

		center_1_1_rank.setMargin(true);
		center_1_1_rank.setWidth("50%");

		center_1_1_sponsor.setMargin(true);
		center_1_1_sponsor.setWidth("50%");

		center_1_1_title_value.setWidth("50%");
		center_1_1_finan_value.setWidth("50%");
		center_1_1_rank_value.setWidth("50%");
		center_1_1_sponsor_value.setWidth("50%"); 

		center_1_1.addComponent(center_1_1_title);
		center_1_1.addComponent(center_1_1_title_value);

		center_1_1.setComponentAlignment(center_1_1_title, Alignment.MIDDLE_LEFT);
		center_1_1.setComponentAlignment(center_1_1_title_value, Alignment.MIDDLE_RIGHT);

		center_1_1.addComponent(center_1_1_blank);
		
		center_1_1.addComponent(center_1_1_finan);
		center_1_1.addComponent(center_1_1_finan_value);

		center_1_1.setComponentAlignment(center_1_1_finan, Alignment.MIDDLE_LEFT);
		center_1_1.setComponentAlignment(center_1_1_finan_value, Alignment.MIDDLE_RIGHT);

		center_1_1.addComponent(center_1_1_blank);
		
		center_1_1.addComponent(center_1_1_rank);
		center_1_1.addComponent(center_1_1_rank_value);
		
		center_1_1.setComponentAlignment(center_1_1_rank, Alignment.MIDDLE_LEFT);
		center_1_1.setComponentAlignment(center_1_1_rank_value, Alignment.MIDDLE_RIGHT);

		center_1_1.addComponent(center_1_1_blank); 
		
		center_1_1.addComponent(center_1_1_sponsor);
		center_1_1.addComponent(center_1_1_sponsor_value);
		
		center_1_1.setComponentAlignment(center_1_1_sponsor, Alignment.MIDDLE_LEFT);
		center_1_1.setComponentAlignment(center_1_1_sponsor_value, Alignment.MIDDLE_RIGHT);

		center_1_1.addComponent(center_1_1_blank); 
	}
	
	public static void refresh(){
		t_l.setValue(team.getName());
		f_l.setValue(team.getFinances());
		r_l.setValue(team.getRanking());
		s_l.setValue(team.getSponsorAgreement().getSponsor().getName());
	}
	
	public static void yesOrNoTrainer(String msg, final BeanTrainers beanTrainers){		
		final Window preg = new Window("Confirmar Accion");
		preg.setModal(true);

		VerticalLayout v = new VerticalLayout();
		HorizontalLayout h = new HorizontalLayout();

		Label l = new Label();
		Label l2 = new Label();
		
		l.setCaption(msg);
		l2.setCaption(" ");

		Button ok = new Button("Aceptar",new Button.ClickListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				beanTrainers.setHired("fin");
				fillTabEntrenadores();
				(preg.getParent()).removeWindow(preg);
			}
		});
		
		Button no = new Button("Cancelar", new Button.ClickListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				(preg.getParent()).removeWindow(preg);
			}
		});

		h.addComponent(ok);
		h.addComponent(no);
		
		v.addComponent(l);
		v.addComponent(l2);
		v.addComponent(h);
		
		preg.addComponent(v);

		l.setWidth("100%");
		l.setHeight("100%");
		
		v.setWidth("100%");
		v.setHeight("100%");

		v.setComponentAlignment(l, Alignment.TOP_CENTER);
		v.setComponentAlignment(h, Alignment.BOTTOM_CENTER);

		h.setWidth("100%");
		h.setHeight("100%");
		
		h.setComponentAlignment(ok, Alignment.MIDDLE_CENTER);
		h.setComponentAlignment(no, Alignment.MIDDLE_CENTER);
		
		root.addWindow(preg);
		
		preg.setHeight("75px");
		preg.setWidth("225px");
	}
	
	public static void launchMen(String string) {
		root.showNotification(string);		
	}
	
	private void updateTeam(PlayerControlledTeam t) {
		updatePlayers(t);
		
		getNewFinances(t);
	}

	private void updatePlayers(PlayerControlledTeam playerControlledTeam) {
		Match m = playerControlledTeam.getLastMatch();
		
		if(m != null)
		{
		
			List<Player> lp = playerControlledTeam.getPlayersInTeam().getPlayers();
			
			StadisticsTeam tm = m.getMyStatistics(playerControlledTeam);
			
			for(Player p : lp)
			{			
				Integer g = tm.getGoals().get(p);
				Integer gc = tm.getGoalsConceded().get(p);
				Integer a = tm.getAssistances().get(p);
				
				if(g == null)
				{
					g = 0;
				}
				
				if(gc == null)
				{
					gc = 0;
				}
				
				if(a == null)
				{
					a = 0;
				}
				
				p.updateWeeklyPlayer(g, gc, a);
			}	
		}
	}

	private void getNewFinances(PlayerControlledTeam playerControlledTeam) {
		//Mirar si el ultimo partido se jugo en Home
		Match m = playerControlledTeam.getLastMatch();
		
		if(m != null)
		{
			if(m.playingAtHome(playerControlledTeam))
			{
				playerControlledTeam.addIncome(playerControlledTeam.getStadium().getBenifit());
			}
		}
		
		//Cobrar los empleados
		Double cost = 0.0;
		
		for(Employer e : playerControlledTeam.getEmployersOfTeam().getEmployers())
		{
			cost += e.getWage();
		}
		
		playerControlledTeam.addWaste(cost);
	}
}