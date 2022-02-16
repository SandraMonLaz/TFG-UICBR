package es.ucm.fdi.tfg.ui.cbrEngine;

import java.io.File;
import java.util.Collection;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.tfg.ui.cbrComponents.CaseDescription;
import es.ucm.fdi.tfg.ui.cbrComponents.CaseSolution;
import es.ucm.fdi.tfg.ui.cbrComponents.ItemId;
import es.ucm.fdi.tfg.ui.cbrComponents.ItemSol;
import es.ucm.fdi.tfg.ui.cbrComponents.RangeType;
import es.ucm.fdi.tfg.ui.cbrComponents.ItemSol.Scale;
import es.ucm.fdi.tfg.ui.cbrComponents.ItemSol.ScreenPos;

public class CBREngine implements StandardCBRApplication  {

	CustomPlainTextConnector connector;
	CBRCaseBase caseBase;
	NNConfig simConfig;
	CaseSolution solution;
	
	final static String CONNECTOR_FILE_PATH = "es/ucm/fdi/tfg/ui/cbrEngine/plaintextconfig.xml";
	final static String CASE_BASE_PATH = "cbrdata"+File.separator+File.separator;
	
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
		//Compute reuse
		solution = reuse(eval);
		
		SolCBR solCBR = adapt((CaseDescription)query.getDescription());
		//Compute revise & retain
		/*CBRCase newCase = createNewCase(query);
		this.storageManager.reviseAndRetain(newCase);*/
		
	}

	private SolCBR adapt(CaseDescription queryDescription) {
		//Se modifica la solucion del caso
		ItemSol sol = solution.getSolutionItems()[ItemId.HEALTH.ordinal()];
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
		SolCBR solCBR = new SolCBR();
		CombinedItem combinedItem = new CombinedItem();
		combinedItem.getItems().add(sol);
		solCBR.getSol().add(combinedItem);
		
		return solCBR;		
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
		// TODO Auto-generated method stub
		
	}

}
