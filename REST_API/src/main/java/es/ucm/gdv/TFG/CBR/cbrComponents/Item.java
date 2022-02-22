
package es.ucm.gdv.TFG.CBR.cbrComponents;


import es.ucm.fdi.gaia.jcolibri.connector.TypeAdaptor;

public class Item implements TypeAdaptor {
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
	@Override
	public void fromString(String content) throws Exception {
		 this.importance = Importance.valueOf(content);		
	}
	@Override
	public String toString() {
		return this.importance.toString();
	}
	
}
