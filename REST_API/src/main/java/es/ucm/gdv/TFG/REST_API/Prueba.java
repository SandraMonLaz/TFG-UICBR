package es.ucm.gdv.TFG.REST_API;

public class Prueba {

	private final Importance holo;
	private final String saludation;
	
	public Prueba(Importance holiwis, String saludacion) {
		holo = holiwis;
		saludation = saludacion;
	}
	
	public Importance getHolo() {
		return holo;
	}
	
	public String getSaludation() {
		return saludation;
	}
}
