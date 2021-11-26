package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class LevelProgress {

	public LevelProgress(Importance importance, RangeType rangeType, ProgressType p) {
		super();
		this.importance = importance;
		this.rangeType = rangeType;
		this.progressType = p;
	}
	
	public enum ProgressType {
		progressLeft,
		progressDone
	}
	
	public Importance getImportance() {
		return importance;
	}
	public RangeType getRangeType() {
		return rangeType;
	}
	public ProgressType getProgressType() {
		return progressType;
	}
	
	private final Importance importance;
	private final RangeType rangeType;
	private final ProgressType progressType;
}
