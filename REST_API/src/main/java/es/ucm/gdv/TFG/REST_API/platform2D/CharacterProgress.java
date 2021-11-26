package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class CharacterProgress {

	public CharacterProgress(Importance importance, RangeType rangeType) {
		super();
		this.importance = importance;
		this.rangeType = rangeType;
	}
	
	public Importance getImportance() {
		return importance;
	}

	private final Importance importance;
	private final RangeType rangeType;
}
