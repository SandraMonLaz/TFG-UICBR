package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retain.StoreCasesMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.gdv.TFG.CBR.cbrComparators.AbilitiesComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.AverageGlobal;
import es.ucm.gdv.TFG.CBR.cbrComparators.CharacterInfoComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.CharacterProgressComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.CollectableComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.HealthComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.LevelProgressComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.ScoreComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.ShieldsComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.TimeComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.WeaponsComparator;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Abilities;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterInfo;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Collectable;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.ItemId;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.LevelProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Score;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Shields;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Time;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Weapons;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;
import es.ucm.gdv.TFG.REST_API.UseType;
import es.ucm.gdv.TFG.REST_API.platform2D.LevelProgress.ProgressType;
import es.ucm.gdv.TFG.REST_API.platform2D.Time.TimeUse;

public class CBREngine implements StandardCBRApplication  {

	
	CustomPlainTextConnector connector;
	CBRCaseBase caseBase;
	NNConfig simConfig;
	
	CaseSolution solution;
	SolCBR solCBR;
	
	final static String CONNECTOR_FILE_PATH = "es/ucm/gdv/TFG/CBR/cbrEngine/plaintextconfig.xml";
	final static String CASE_BASE_PATH = "cbrdata"+File.separator;
	
	private static CBREngine instance = null;
	
	public static CBREngine getInstance() {
		if(instance == null)
			instance = new CBREngine();
		return instance;
	}
	
	public void init(){
		try {
			configure();
			preCycle();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void configure() throws ExecutionException {
		// TODO Auto-generated method stub
		connector = new CustomPlainTextConnector();
		caseBase = new CachedLinearCaseBase();
		
		connector.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH));
		
		connector.setCaseBaseFile(CASE_BASE_PATH, "casos.csv");
		
		
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new AverageGlobal());
		simConfig.addMapping(new Attribute("health",CaseDescription.class), new HealthComparator());	
		simConfig.addMapping(new Attribute("score",CaseDescription.class), new ScoreComparator());
		simConfig.addMapping(new Attribute("abilities", CaseDescription.class), new AbilitiesComparator());
		simConfig.addMapping(new Attribute("characterinfo", CaseDescription.class), new CharacterInfoComparator());
		simConfig.addMapping(new Attribute("characterProgress", CaseDescription.class), new CharacterProgressComparator());
		simConfig.addMapping(new Attribute("collectable", CaseDescription.class), new CollectableComparator());
		simConfig.addMapping(new Attribute("levelProgress", CaseDescription.class), new LevelProgressComparator());
		simConfig.addMapping(new Attribute("shields", CaseDescription.class), new ShieldsComparator());
		simConfig.addMapping(new Attribute("time", CaseDescription.class), new TimeComparator());
		simConfig.addMapping(new Attribute("weapons", CaseDescription.class), new WeaponsComparator());
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		
		//----------------------- CASO DE PRUEBA -----------------------------
		/*CBRCase _case = new CBRCase();
		CaseDescription des = new CaseDescription();*/
		
		/*Time t = new Time();
		t.setImportance(Importance.veryHigh);
		t.setTimeUse(TimeUse.chrono);
		des.setTime(t);*/
		
		/*Health h = new Health();
		h.setImportance(Importance.veryHigh);
		h.setType(RangeType.continuous);
		des.setHealth(h);*/
		
		/*Shields s = new Shields();
		s.setImportance(Importance.veryHigh);
		s.setType(RangeType.continuous);
		des.setShields(s);*/
		
		/*Score sc = new Score();
		sc.setImportance(Importance.veryHigh);
		des.setScore(sc);*/
		
		/*Collectable c = new Collectable();
		c.setImportance(Importance.veryHigh);
		des.setCollectable(c);*/
		
		/*CharacterProgress cp = new CharacterProgress();
		cp.setImportance(Importance.veryHigh);
		cp.setRangeType(RangeType.continuous);
		des.setCharacterProgress(cp);*/
		
		/*CharacterInfo ci = new CharacterInfo();
		ci.setImportance(Importance.veryHigh);
		des.setCharacterinfo(ci);*/
		
		/*Weapons w = new Weapons();
		w.setImportance(Importance.veryHigh);
		w.setUseType(UseType.infinite);
		w.setnWeapons(1);
		des.setWeapons(w);*/
		
		/*Abilities a = new Abilities();
		a.setImportance(Importance.veryHigh);
		a.setUseType(UseType.infinite);
		a.setnWeapons(2);
		des.setAbilities(a);*/
		
		/*LevelProgress lp = new LevelProgress();
		lp.setImportance(Importance.veryHigh);
		lp.setRangeType(RangeType.continuous);
		lp.setProgressType(ProgressType.progressDone);
		des.setLevelProgress(lp);*/
		
		/*_case.setDescription(des);*/
					
		/*CaseSolution sol = new CaseSolution();*/
		
		/*ItemSol time = new ItemSol(ScreenPos.TOP_RIGHT, Scale.VERY_BIG, "tiempoCountdown", ItemId.TIME);
		sol.setSolItem(time, ItemId.TIME);*/
		
		/*ItemSol health = new ItemSol(ScreenPos.TOP_LEFT, Scale.VERY_BIG, "vidaContinua", ItemId.HEALTH);
		sol.setSolItem(health, ItemId.HEALTH);*/
		
		/*ItemSol shields = new ItemSol(ScreenPos.TOP_LEFT, Scale.VERY_BIG, "escudosContinuos", ItemId.SHIELDS);
		sol.setSolItem(shields, ItemId.SHIELDS);*/
		
		/*ItemSol score = new ItemSol(ScreenPos.TOP_RIGHT, Scale.VERY_BIG, "puntos", ItemId.SCORE);
		sol.setSolItem(score, ItemId.SCORE);*/
		
		/*ItemSol collectable = new ItemSol(ScreenPos.TOP_RIGHT, Scale.VERY_BIG, "collectable", ItemId.COLLECTABLE);
		sol.setSolItem(collectable, ItemId.COLLECTABLE);*/
	
		/*ItemSol characterProgress = new ItemSol(ScreenPos.TOP_LEFT, Scale.VERY_BIG, "progresoPersonajeContinuo", ItemId.CHARACTER_PROGRESS);
		sol.setSolItem(characterProgress, ItemId.CHARACTER_PROGRESS);*/
		
		/*ItemSol CharacterInfo = new ItemSol(ScreenPos.TOP_LEFT, Scale.VERY_BIG, "informacionPersonaje", ItemId.CHARACTER_INFO);
		sol.setSolItem(CharacterInfo, ItemId.CHARACTER_INFO);*/
		
		/*ItemSol weapons = new ItemSol(ScreenPos.BOTTOM_RIGHT, Scale.VERY_BIG, "armaInfinita", ItemId.WEAPONS);
		sol.setSolItem(weapons, ItemId.WEAPONS);*/
			
		/*ItemSol abilities = new ItemSol(ScreenPos.BOTTOM_RIGHT, Scale.VERY_BIG, "habilidadInfinita", ItemId.ABILITIES);
		sol.setSolItem(abilities, ItemId.ABILITIES);*/
		
		/*ItemSol levelProgress = new ItemSol(ScreenPos.BOTTOM_CENTER, Scale.VERY_BIG, "progresoNivelContinuo", ItemId.LEVEL_PROGRESS);
		sol.setSolItem(levelProgress, ItemId.LEVEL_PROGRESS);*/
		
		/*_case.setSolution(sol);
		StoreCasesMethod.storeCase(caseBase, _case);*/
		//---------------------------------------------------------------------
		
		return caseBase;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		if(caseBase.getCases().isEmpty()) {
			throw new ExecutionException("La base de casos esta vacia");
		}

		//Compute retrieve
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
		//Compute reuse and adapt
		solution = reuse(eval);	
		solCBR = adapt((CaseDescription)query.getDescription());
		//Compute revise and Retain
		reviseAndRetain(query.getDescription(), eval);
	}
	
	private void reviseAndRetain(CaseComponent queryDescription, Collection<RetrievalResult> eval) {
		CBRCase _case = new CBRCase();
		_case.setSolution(solution);
		_case.setDescription(queryDescription);

		RetrievalResult first = SelectCases.selectTopKRR(eval, 1).iterator().next();
		this.solCBR.setId((Integer)first.get_case().getID());
		this.solCBR.setSim(first.getEval());
		if(first.getEval() < 0.8)
			StoreCasesMethod.storeCase(caseBase, _case);
	}
	
	private SolCBR adapt(CaseDescription queryDescription) {
		PlatformAdaptation adapt = new PlatformAdaptation();
		return adapt.adapt(solution, queryDescription);		
	}
	
	private CaseSolution reuse(Collection<RetrievalResult> eval)
	{
		// This simple implementation only uses 1NN
		// Consider using kNNs with majority voting
		RetrievalResult first = SelectCases.selectTopKRR(eval, 1).iterator().next();
		CBRCase mostSimilarCase = first.get_case();
		
		CaseSolution mostSimilarCaseSol = (CaseSolution) mostSimilarCase.getSolution();
		CaseSolution sol = new CaseSolution(mostSimilarCaseSol);
		
		return sol;
	}
	
	@Override
	public void postCycle() throws ExecutionException {
		this.caseBase.close();
		System.out.println("Cerrando cbr");
	}
	
	public SolCBR getSolution() {
		return this.solCBR;
	}
	
	public float evaluate(){
		float result = 0;
		for(CBRCase caso: this.caseBase.getCases()){
			CBRQuery query = new CBRQuery();
			query.setDescription(caso.getDescription());
			
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
			ArrayList<RetrievalResult> list = (ArrayList<RetrievalResult>) SelectCases.selectTopKRR(eval, 2);
			RetrievalResult second = list.get(1);
			System.out.println(second.getEval());
			result +=second.getEval();
		}
		result /= (float)this.caseBase.getCases().size();
		return result;
	}

}
