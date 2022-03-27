package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.UseType;

public class Abilities {
	
	public Abilities(Importance importance, int nWeapons, UseType use) {
		super();
		this.importance = importance;
		this.nWeapons = nWeapons;
		this.use = use;
	}
	
	public Importance getImportance() {
		return importance;
	}
	public int getnWeapons() {
		return nWeapons;
	}
	
	public UseType getUse() {
		return use;
	}

	private final Importance importance;
	private final int nWeapons;
	private final UseType use;

}
