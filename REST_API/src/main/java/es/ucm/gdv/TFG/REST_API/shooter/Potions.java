package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Potions {
	
	public Potions(Importance importance) {
		super();
		this.importance = importance;
	}

	public Importance getImportance() {
		return importance;
	}

	private final Importance importance;
}
