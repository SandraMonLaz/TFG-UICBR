package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.RangeType;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;

public class Shields extends Item {
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
	public ItemSol adapt(ItemSol shields, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene HEALTH lo creamos
		if(shields == null) {
			shields = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "escudosContinuos", ItemId.HEALTH);
		}
		
		if(this.type == RangeType.discrete && shields.getImage() == "escudosContinuos") {
			shields.setImage("escudosDiscretos");
		}
		else if(this.type == RangeType.continuous &&  shields.getImage() == "escudosDiscretos") {
			shields.setImage("escudosContinuos");
		}

		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			shields.setScreenPosition(ScreenPos.TOP_LEFT); //Habría que hacer que coincida con la vida
			shields.setItemScale(Scale.VERY_BIG);
		}
		else {
			shields.setScreenPosition(ScreenPos.BOTTOM_CENTER);
			shields.setItemScale(Scale.SMALL);
		}
		solution.setSolItem(shields, ItemId.SHIELDS);
		return shields;
	}
}
