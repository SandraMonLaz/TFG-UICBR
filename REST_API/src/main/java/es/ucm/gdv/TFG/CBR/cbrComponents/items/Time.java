package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class Time extends Item {
private RangeType rangeType;
	
	public RangeType getRangeType() {
		return rangeType;
	}

	public void setRangeType(RangeType rangeType) {
		this.rangeType = rangeType;
	}

	@Override
	public void fromString(String content) throws Exception {
		String[] splited = content.split("\\|");
		super.fromString(splited[0]);	
		this.rangeType = RangeType.valueOf(splited[1]);
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		return s + "|" + this.rangeType.toString();
	}
	
	@Override
	public ItemSol adapt(ItemSol solutionTime, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene CHARACTER_PROGRESS lo creamos
		if(solutionTime == null) {
			solutionTime = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "tiempoContinuo", ItemId.TIME);
		}
		
		solutionTime.setScreenPosition(ScreenPos.TOP_CENTER);
		
		if(this.rangeType == RangeType.continuous) solutionTime.setImage("tiempoContinuo");
		else solutionTime.setImage("tiempoDiscreto");
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			solutionTime.setItemScale(Scale.MEDIUM);
		}
		else {
			solutionTime.setItemScale(Scale.SMALL);
		}
		
		solution.setSolItem(solutionTime, ItemId.TIME);
		return solutionTime;
	}
}
