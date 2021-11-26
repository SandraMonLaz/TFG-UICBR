package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;

public class CharacterInfo {
	public CharacterInfo(Importance importance) {
		super();
		this.importance = importance;
	}
	
	public Importance getImportance() {
		return importance;
	}

	private final Importance importance;
}
