package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;

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
	
	@Override
	public ItemSol adapt(ItemSol health, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene HEALTH lo creamos
		if(health == null) {
			health = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "vidaContinua", ItemId.HEALTH);
		}
		
		if(this.type == RangeType.discrete && health.getImage() == "vidaContinua") {
			health.setImage("vidaDiscreta");
		}
		else if(this.type == RangeType.continuous &&  health.getImage() == "vidaDiscreta") {
				health.setImage("vidaContinua");
		}
		
		if(this.importance.ordinal() >= Importance.medium.ordinal()) {
			health.setScreenPosition(ScreenPos.TOP_LEFT);
			health.setItemScale(Scale.VERY_BIG);
		}
		else {
			health.setScreenPosition(ScreenPos.BOTTOM_CENTER);
			health.setItemScale(Scale.SMALL);
		}
		solution.setSolItem(health, ItemId.HEALTH);
		return health;
	}
}
