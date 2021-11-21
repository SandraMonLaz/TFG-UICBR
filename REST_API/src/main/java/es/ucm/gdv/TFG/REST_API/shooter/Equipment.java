package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Equipment {
	
	public Equipment(Importance importance, int nItems) {
		super();
		this.importance = importance;
		this.nItems = nItems;
	}
	
	public Importance getImportance() {
		return importance;
	}
	public int getnItems() {
		return nItems;
	}
	
	private final Importance importance;
	private final int nItems;
}
