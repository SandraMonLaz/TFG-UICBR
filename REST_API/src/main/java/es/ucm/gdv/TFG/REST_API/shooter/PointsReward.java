package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;

public class PointsReward {
	
	public PointsReward(Importance importance, int nRewards) {
		super();
		this.importance = importance;
		this.nRewards = nRewards;
	}
	
	public Importance getImportance() {
		return importance;
	}
	public int getnRewards() {
		return nRewards;
	}
	
	private final Importance importance;
	private final int nRewards;
}
