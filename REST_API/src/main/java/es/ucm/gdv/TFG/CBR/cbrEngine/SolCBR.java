package es.ucm.gdv.TFG.CBR.cbrEngine;

import java.util.ArrayList;

public class SolCBR {
	ArrayList<CombinedItem> sol;
	
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
