package es.ucm.gdv.TFG.REST_API.shooter;

import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class Health {
	
	public Health(Importance importance, boolean diegetic, RangeType rangetype) {
		super();
		this.importance = importance;
		this.diegetic = diegetic;
		this.rangetype = rangetype;
	}
	
	public boolean isDiegetic() {
		return diegetic;
	}
	public void setDiegetic(boolean diegetic) {
		this.diegetic = diegetic;
	}
	public Importance getImportance() {
		return importance;
	}
	public RangeType getRangetype() {
		return rangetype;
	}

	private final Importance importance;
	private boolean diegetic;
	private final RangeType rangetype;
	

}
