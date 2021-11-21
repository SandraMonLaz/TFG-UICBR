package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class Habilities {
	
	public Habilities(Importance importance, int nHabilities, RangeType rangeType) {
		super();
		this.importance = importance;
		this.nHabilities = nHabilities;
		this.rangeType = rangeType;
	}
	
	public Importance getImportance() {
		return importance;
	}
	public int getnHabilities() {
		return nHabilities;
	}
	public RangeType getRangeType() {
		return rangeType;
	}

	private final Importance importance;
	private final int nHabilities;
	private final RangeType rangeType;
}
