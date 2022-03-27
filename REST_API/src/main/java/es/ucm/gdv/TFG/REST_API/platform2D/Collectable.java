package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Collectable {
	public Collectable(Importance importance) {
		super();
		this.importance = importance;
	}
	
	public Collectable() {
		this.importance = null;	
	}
	
	public Importance getImportance() {
		return importance;
	}

	private final Importance importance;
}
