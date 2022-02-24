package es.ucm.gdv.TFG.REST_API;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PreloaderRestApi {

	private static final Logger log = LoggerFactory.getLogger(PreloaderRestApi.class);
	 
	@Bean(initMethod="initCbr")
    public CbrPreLoader init() {
        return new CbrPreLoader();
    }
	
	@Bean(destroyMethod="closeCbr")
    public CbrPreLoader close() {
		return new CbrPreLoader();
    }	
}
