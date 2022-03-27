package es.ucm.gdv.TFG.REST_API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.gdv.TFG.CBR.cbrEngine.CBREngine;

public class CbrPreLoader {
	
	private static final Logger log = LoggerFactory.getLogger(PreloaderRestApi.class);
	
	public CBREngine initCbr() {
		CBREngine.getInstance().init();	
		log.info("Inicializando cbr");
        return CBREngine.getInstance();
    }
	
	public void closeCbr() {
		
		try {
			CBREngine.getInstance().postCycle();
			log.info("Cerrando cbr");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
