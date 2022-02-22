package es.ucm.gdv.TFG.REST_API;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.gdv.TFG.CBR.cbrEngine.CBREngine;


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
