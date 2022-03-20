package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class CharacterProgress extends Item {
	
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
	public ItemSol adapt(ItemSol solutionCharProgress, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene CHARACTER_PROGRESS lo creamos
		if(solutionCharProgress == null) {
			solutionCharProgress = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "progresoPersonajeDiscreto", ItemId.CHARACTER_PROGRESS);
		}
		
		if(this.rangeType == RangeType.continuous) solutionCharProgress.setImage("progresoPersonajeContinuo");
		else solutionCharProgress.setImage("ProgresoPersonajeDiscreto");
				
		solutionCharProgress.setScreenPosition(ScreenPos.TOP_LEFT);
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			solutionCharProgress.setItemScale(Scale.MEDIUM);
		}
		else {
			solutionCharProgress.setItemScale(Scale.SMALL);
		}
		
		solution.setSolItem(solutionCharProgress, ItemId.CHARACTER_PROGRESS);
		return solutionCharProgress;
	}
}
