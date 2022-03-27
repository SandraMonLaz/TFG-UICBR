package es.ucm.gdv.TFG.REST_API.platform2D;

import es.ucm.gdv.TFG.REST_API.Importance;

public class Time {
	public enum TimeUse{
		countdown,
		chrono
	}
	public Time(Importance importance, TimeUse use) {
		super();
		this.importance = importance;
		this.use = use;
	}
	
	public Time() {
		this.importance = null;
		this.use = null;		
	}
	
	public Importance getImportance() {
		return importance;
	}
	
	public TimeUse getUse() {
		return use;
	}

	private final Importance importance;
	private final TimeUse use;
}
