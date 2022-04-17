package es.ucm.gdv.TFG.CBR.main;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Score;
import es.ucm.gdv.TFG.CBR.cbrEngine.CBREngine;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class Main {

	 static CBREngine cbrEngine;
	  
	 
	 public static void main(String[] args) {
		 cbrEngine = new CBREngine();
		 cbrEngine.init();
		 
		 Health health = new Health();
		 health.setImportance(Importance.high);
		 health.setType(RangeType.discrete);
		 
			
		 CaseDescription caseDes = new CaseDescription();
		 caseDes.setId(0);
		 caseDes.setHealth(health);
		// caseDes.setScore(score);
		 CBRQuery query = new CBRQuery();
		 query.setDescription(caseDes);
		 
		 
		 try {
			cbrEngine.cycle(query);
		 } catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 
		 cbrEngine.getSolution();
		 
		 
		 try {
			 cbrEngine.postCycle();			 
		 }
		 catch (ExecutionException e) {
			 e.printStackTrace();
		 }
		 
	 }
}
