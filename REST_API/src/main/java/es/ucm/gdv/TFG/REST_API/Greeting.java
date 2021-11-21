package es.ucm.gdv.TFG.REST_API;

public class Greeting {

	private final long id;
	private final String content;
	private final Prueba prueba;

	public Greeting(long id, String content, Prueba pruebita) {
		this.id = id;
		this.content = content;
		this.prueba = pruebita;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
	public Prueba getPrueba(){
		return prueba;
	}
}
