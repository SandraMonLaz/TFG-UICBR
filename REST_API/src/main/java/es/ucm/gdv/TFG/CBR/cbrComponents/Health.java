package es.ucm.gdv.TFG.CBR.cbrComponents;

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
		this.type = RangeType.valueOf(splited[1]);		
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		return s + "|" + this.type.toString();
	}
}
