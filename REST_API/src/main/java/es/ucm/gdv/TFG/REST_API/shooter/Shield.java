package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class Shield {
	
	public Shield(Importance importance, boolean diegetic, RangeType rangeType) {
		super();
		this.importance = importance;
		this.diegetic = diegetic;
		this.rangeType = rangeType;
	}
	
	public Importance getImportance() {
		return importance;
	}
	public boolean isDiegetic() {
		return diegetic;
	}
	public RangeType getRangeType() {
		return rangeType;
	}

	private final Importance importance;
	private final boolean diegetic;
	private final RangeType rangeType;
}
