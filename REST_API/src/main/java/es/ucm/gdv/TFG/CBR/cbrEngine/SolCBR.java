package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.util.ArrayList;

public class SolCBR {
	ArrayList<CombinedItem> sol;
	int id;
	double sim;

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
