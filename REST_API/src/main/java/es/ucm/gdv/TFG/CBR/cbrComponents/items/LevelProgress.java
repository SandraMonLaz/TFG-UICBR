package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.REST_API.RangeType;
import es.ucm.gdv.TFG.REST_API.platform2D.LevelProgress.ProgressType;

public class LevelProgress extends Item {
	private RangeType rangeType;
	private ProgressType progressType;
	
	public RangeType getRangeType() {
		return rangeType;
	}
	public void setRangeType(RangeType rangeType) {
		this.rangeType = rangeType;
	}
	public ProgressType getProgressType() {
		return progressType;
	}
	public void setProgressType(ProgressType progressType) {
		this.progressType = progressType;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		return s + "|" + this.rangeType.toString() + "|" + this.progressType;
	}
}
