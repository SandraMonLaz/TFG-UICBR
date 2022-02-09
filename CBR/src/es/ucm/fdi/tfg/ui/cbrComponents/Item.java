
package es.ucm.fdi.tfg.ui.cbrComponents;

public class Item {
	public enum Importance {
		veryLow,
		low,
		medium,
		high,
		veryHigh
	}
	Importance importance;
	
	public Importance getImportance() {
		return importance;
	}
	public void setImportance(Importance importance) {
		this.importance = importance;
	}
	
}
