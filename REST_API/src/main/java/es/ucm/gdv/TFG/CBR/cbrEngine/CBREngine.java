package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.io.File;
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
import es.ucm.gdv.TFG.CBR.cbrComparators.HealthComparator;
import es.ucm.gdv.TFG.CBR.cbrComparators.ScoreComparator;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Item;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.ItemId;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Score;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

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
		simConfig.setDescriptionSimFunction(new Average());
		simConfig.addMapping(new Attribute("health",CaseDescription.class), new HealthComparator());	
		simConfig.addMapping(new Attribute("score",CaseDescription.class), new ScoreComparator());
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		
		//----------------------- CASO DE PRUEBA -----------------------------
		/*CBRCase _case = new CBRCase();
		
		CaseDescription des = new CaseDescription();
		Health h = new Health();
		h.setImportance(Importance.low);
		h.setType(RangeType.continuous);
		des.setHealth(h);
		Score s = new Score();
		s.setImportance(Importance.low);
		des.setScore(s);
		_case.setDescription(des);
		
		CaseSolution sol = new CaseSolution();
		ItemSol health = new ItemSol(ScreenPos.TOP_LEFT, Scale.SMALL, "vidaContinua", ItemId.HEALTH);
		sol.setSolItem(health, ItemId.HEALTH);
		ItemSol score = new ItemSol(ScreenPos.TOP_RIGHT, Scale.SMALL, "puntos", ItemId.SCORE);
		sol.setSolItem(score, ItemId.SCORE);
		_case.setSolution(sol);
		
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

}
