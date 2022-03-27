package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class Shields {

	public Shields(Importance importance, RangeType rangeType) {
		super();
		this.importance = importance;
		this.rangeType = rangeType;
	}
	
	public Shields() {
		this.importance = null;
		this.rangeType = null;		
	}
	
	public Importance getImportance() {
		return importance;
	}
	public RangeType getRangeType() {
		return rangeType;
	}

	private final Importance importance;
	private final RangeType rangeType;
}
