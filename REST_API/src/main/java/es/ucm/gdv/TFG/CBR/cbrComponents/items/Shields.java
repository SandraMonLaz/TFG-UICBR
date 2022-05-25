package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

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
		//Si la solucion que nos ofrece no tiene shields lo creamos
		if(shields == null) {
			shields = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "escudosContinuos", ItemId.SHIELDS);
			if(this.importance.ordinal() >= Importance.high.ordinal()) {
				shields.setScreenPosition(ScreenPos.TOP_LEFT); //Habría que hacer que coincida con la vida
			}
			else {
				shields.setScreenPosition(ScreenPos.BOTTOM_CENTER);
			}
		}
		
		if(this.type == RangeType.discrete) 		shields.setImage("escudosDiscretos");
		else if(this.type == RangeType.continuous)	shields.setImage("escudosContinuos");
		
		shields.setItemScale(Scale.values()[this.importance.ordinal()]);
		solution.setSolItem(shields, ItemId.SHIELDS);
		return shields;
	}
}
