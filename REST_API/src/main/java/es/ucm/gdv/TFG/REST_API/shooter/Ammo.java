package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Ammo {
	
	public Ammo(Importance importance, int numBullets, boolean diegetic) {
		super();
		this.importance = importance;
		this.numBullets = numBullets;
		this.diegetic = diegetic;
	}
	
	public Importance getImportance() {
		return importance;
	}
	
	public int getNumBullets() {
		return numBullets;
	}
	
	public boolean isDiegetic() {
		return diegetic;
	}

	private final Importance importance;
	private final int numBullets;
	private final boolean diegetic;
	
}
