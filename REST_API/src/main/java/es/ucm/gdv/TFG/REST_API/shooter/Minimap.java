package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Minimap {
	
	public enum MinimapUse{
		partial,
		total
	}
	
	public Minimap(Importance importance, boolean diegetic, MinimapUse use) {
		super();
		this.importance = importance;
		this.diegetic = diegetic;
		this.use = use;
	}
	
	public Importance getImportance() {
		return importance;
	}
	
	public boolean isDiegetic() {
		return diegetic;
	}
	
	public MinimapUse getUse() {
		return use;
	}
	
	private final Importance importance;
	private final boolean diegetic;
	private final MinimapUse use;
}
