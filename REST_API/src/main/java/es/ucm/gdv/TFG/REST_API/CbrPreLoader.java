package es.ucm.gdv.TFG.REST_API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.gdv.TFG.CBR.cbrEngine.CBREngine;

public class CbrPreLoader {
	
	private static CBREngine cbr;
	private static final Logger log = LoggerFactory.getLogger(PreloaderRestApi.class);
	
	public CBREngine initCbr() {
		cbr = new CBREngine();		
		log.info("Inicializando cbr");
        return cbr;
    }
	
	public void closeCbr() {
		
		try {
			cbr.postCycle();
			log.info("Cerrando cbr");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
