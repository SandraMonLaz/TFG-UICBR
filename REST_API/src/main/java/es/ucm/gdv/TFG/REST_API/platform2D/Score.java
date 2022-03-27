package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Score {
	public Score(Importance importance) {
		super();
		this.importance = importance;
	}
	public Score() {
		importance = null;
	}
	public Importance getImportance() {
		return importance;
	}

	private final Importance importance;
}
