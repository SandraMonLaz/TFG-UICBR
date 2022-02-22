package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.io.File;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemId;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.PreloaderRestApi;
import es.ucm.gdv.TFG.CBR.cbrComponents.RangeType;

public class CBREngine implements StandardCBRApplication  {

	
	CustomPlainTextConnector connector;
	CBRCaseBase caseBase;
	NNConfig simConfig;
	
	CaseSolution solution;
	SolCBR solCBR;
	
	final static String CONNECTOR_FILE_PATH = "es/ucm/gdv/TFG/CBR/cbrEngine/plaintextconfig.xml";
	final static String CASE_BASE_PATH = "cbrdata"+File.separator+File.separator;
	
	
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
		simConfig.addMapping(new Attribute("health",CaseDescription.class), new Interval(15000));	
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
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
		reviseAndRetain(query.getDescription());	
	}
	
	private void reviseAndRetain(CaseComponent queryDescription) {
		CBRCase _case = new CBRCase();
		_case.setSolution(solution);
		_case.setDescription(queryDescription);
		StoreCasesMethod.storeCase(caseBase, _case);
	}
	
	private SolCBR adapt(CaseDescription queryDescription) {
		//Se modifica la solucion del caso
		ItemSol sol = solution.getSolutionItems().getValues()[ItemId.HEALTH.ordinal()];
		if(sol == null && queryDescription.getHealth() != null) {
			sol = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "vidaContinua", ItemId.HEALTH);
		}
			
		if(sol != null) {
				
			if(queryDescription.getHealth().getType() == RangeType.discrete &&
			    sol.getImage() == "vidaContinua") {
				sol.setImage("vidaDiscreta");
				solution.setSolItem(sol, ItemId.HEALTH);
			}
			else if(queryDescription.getHealth().getType() == RangeType.continuous &&
				    sol.getImage() == "vidaDiscreta") {
					sol.setImage("vidaContinua");
					solution.setSolItem(sol, ItemId.HEALTH);
				}
		}
		
		// Se usa la solucion modificada para crear la solucion del CBR		
		SolCBR CBR = new SolCBR();
		CombinedItem combinedItem = new CombinedItem();
		combinedItem.getItems().add(sol);
		CBR.getSol().add(combinedItem);
		
		return CBR;		
	}
	
	private CaseSolution reuse(Collection<RetrievalResult> eval)
	{
		// This simple implementation only uses 1NN
		// Consider using kNNs with majority voting
		RetrievalResult first = SelectCases.selectTopKRR(eval, 1).iterator().next();
		CBRCase mostSimilarCase = first.get_case();

		CaseSolution solution = (CaseSolution) mostSimilarCase.getSolution();
		
		return solution;
	}
	@Override
	public void postCycle() throws ExecutionException {
		System.out.println("Cerrando cbr");
	}

}
