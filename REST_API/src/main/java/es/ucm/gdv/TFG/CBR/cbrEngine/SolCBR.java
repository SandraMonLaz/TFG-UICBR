package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.util.ArrayList;

/*Clase que guarda la definición de la interfaz solución*/
public class SolCBR {
	ArrayList<CombinedItem> sol;
	int id;		//id del caso devuelto(el más similar)
	double sim;	//similitud del caso devuelto

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSim() {
		return sim;
	}

	public void setSim(double sim) {
		this.sim = sim;
	}

	
	public SolCBR() {
		sol = new ArrayList<CombinedItem>();
	}

	public ArrayList<CombinedItem> getSol() {
		return sol;
	}

	public void setSol(ArrayList<CombinedItem> sol) {
		this.sol = sol;
	}
	
}
