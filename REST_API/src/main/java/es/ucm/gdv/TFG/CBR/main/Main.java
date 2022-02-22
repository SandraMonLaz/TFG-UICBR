package es.ucm.gdv.TFG.CBR.main;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.Health;
import es.ucm.gdv.TFG.CBR.cbrComponents.Item.Importance;
import es.ucm.gdv.TFG.CBR.cbrComponents.RangeType;
import es.ucm.gdv.TFG.CBR.cbrEngine.CBREngine;

public class Main {

	 static CBREngine cbrEngine;
	  
	 
	 public static void main(String[] args) {
		 cbrEngine = new CBREngine();
		 try {
			cbrEngine.configure();
			cbrEngine.preCycle();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 Health health = new Health();
		 health.setImportance(Importance.high);
		 health.setType(RangeType.continuous);

		 CaseDescription caseDes = new CaseDescription();
		 caseDes.setId(0);
		 caseDes.setHealth(health);
		 
		 CBRQuery query = new CBRQuery();
		 query.setDescription(caseDes);
		 
		 try {
			cbrEngine.cycle(query);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
}
