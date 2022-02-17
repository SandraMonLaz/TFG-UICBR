package es.ucm.fdi.tfg.ui.cbrComponents;

import es.ucm.fdi.tfg.ui.cbrComponents.Item.Importance;

public class Health extends Item {
	RangeType type;

	public RangeType getType() {
		return type;
	}

	public void setType(RangeType type) {
		this.type = type;
	}
	
	@Override
	public void fromString(String content) throws Exception {
		String[] splited = content.split("\\|");
		super.fromString(splited[0]);
		this.importance = Importance.valueOf(content);		
	}
	@Override
	public String toString() {
		String s = super.toString();
		return s + "|" + this.importance.toString();
	}
}
