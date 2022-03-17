package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.io.File;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Item;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.ItemId;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Item.Importance;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSolArray;
import es.ucm.gdv.TFG.CBR.cbrComponents.RangeType;

public class CBREngine implements StandardCBRApplication  {

	
	CustomPlainTextConnector connector;
	CBRCaseBase caseBase;
	NNConfig simConfig;
	
	CaseSolution solution;
	SolCBR solCBR;
	
	final static String CONNECTOR_FILE_PATH = "es/ucm/gdv/TFG/CBR/cbrEngine/plaintextconfig.xml";
	final static String CASE_BASE_PATH = "cbrdata"+File.separator;
	
	
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
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		
		//----------------------- CASO DE PRUEBA -----------------------------
		/*CBRCase _case = new CBRCase();
		
		CaseDescription des = new CaseDescription();
		Health h = new Health();
		h.setImportance(Importance.high);
		h.setType(RangeType.continuous);
		des.setHealth(h);
		_case.setDescription(des);
		
		CaseSolution sol = new CaseSolution();
		ItemSol health = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "vidaDiscreta", ItemId.HEALTH);
		sol.setSolItem(health, ItemId.HEALTH);
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
		ItemSol health = solution.getSolutionItems().getValues()[ItemId.HEALTH.ordinal()];
		ItemSol score = solution.getSolutionItems().getValues()[ItemId.SCORE.ordinal()];
		
		//Llamamos a las funciones de adaptación
		Item item;
		item = queryDescription.getHealth();
		if(item != null)	item.adapt(health, solution);
		
		item = queryDescription.getScore();
		if(item != null)	item.adapt(score, solution); 
		
		// Se usa la solucion modificada para crear la solucion del CBR		
		SolCBR CBR = new SolCBR();
		CombinedItem combinedTL = new CombinedItem();
		combinedTL.setScreenPosition(ScreenPos.TOP_LEFT);
		combinedTL.setItemScale(Scale.MEDIUM);
		CombinedItem combinedTC = new CombinedItem();
		combinedTC.setScreenPosition(ScreenPos.TOP_CENTER);
		combinedTC.setItemScale(Scale.MEDIUM);
		CombinedItem combinedTR = new CombinedItem();
		combinedTR.setScreenPosition(ScreenPos.TOP_RIGHT);
		combinedTR.setItemScale(Scale.MEDIUM);
		CombinedItem combinedCL = new CombinedItem();
		combinedCL.setScreenPosition(ScreenPos.MIDDLE_LEFT);
		combinedCL.setItemScale(Scale.MEDIUM);
		CombinedItem combinedCC = new CombinedItem();
		combinedCC.setScreenPosition(ScreenPos.MIDDLE_CENTER);
		combinedCC.setItemScale(Scale.MEDIUM);
		CombinedItem combinedCR = new CombinedItem();
		combinedCR.setScreenPosition(ScreenPos.MIDDLE_RIGHT);
		combinedCR.setItemScale(Scale.MEDIUM);
		CombinedItem combinedBL= new CombinedItem();
		combinedBL.setScreenPosition(ScreenPos.BOTTOM_LEFT);
		combinedBL.setItemScale(Scale.MEDIUM);
		CombinedItem combinedBC= new CombinedItem();
		combinedBC.setScreenPosition(ScreenPos.BOTTOM_CENTER);
		combinedBC.setItemScale(Scale.MEDIUM);
		CombinedItem combinedBR= new CombinedItem();
		combinedBR.setScreenPosition(ScreenPos.BOTTOM_RIGHT);
		combinedBR.setItemScale(Scale.MEDIUM);
		
		for(ItemSol itemSol: solution.getSolutionItems().getValues()) {
			switch(itemSol.getScreenPosition()) {
			case TOP_LEFT:		combinedTL.getItems().add(itemSol); break;
			case TOP_CENTER:	combinedTC.getItems().add(itemSol);	break;
			case TOP_RIGHT: 	combinedTR.getItems().add(itemSol); break;
			case MIDDLE_LEFT:	combinedCL.getItems().add(itemSol); break;
			case MIDDLE_CENTER:	combinedCC.getItems().add(itemSol); break;
			case MIDDLE_RIGHT:	combinedCR.getItems().add(itemSol); break;
			case BOTTOM_LEFT:	combinedBL.getItems().add(itemSol); break;
			case BOTTOM_CENTER:	combinedBC.getItems().add(itemSol); break;
			case BOTTOM_RIGHT:	combinedBR.getItems().add(itemSol); break;
				
			}
		}
			
		CBR.getSol().add(combinedTL);
		CBR.getSol().add(combinedTC);
		CBR.getSol().add(combinedTR);
		CBR.getSol().add(combinedCL);
		CBR.getSol().add(combinedCC);
		CBR.getSol().add(combinedCR);
		CBR.getSol().add(combinedBL);
		CBR.getSol().add(combinedBC);
		CBR.getSol().add(combinedBR);
		
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
		this.caseBase.close();
		System.out.println("Cerrando cbr");
	}
	
	public String getSolution() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this.solCBR);
		
		System.out.print(json);
		return json;
	}

}
