package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.RangeType;

public class Score extends Item {

	@Override
	public void fromString(String content) throws Exception {
		super.fromString(content);	
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
	public ItemSol adapt(ItemSol score, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene SCORE lo creamos
		if(score == null) {
			score = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "puntos", ItemId.SCORE);
		}
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			score.setScreenPosition(ScreenPos.TOP_RIGHT); //Habría que evitar que coincida con la vida
			score.setItemScale(Scale.MEDIUM);
		}
		else {
			score.setScreenPosition(ScreenPos.BOTTOM_CENTER);
			score.setItemScale(Scale.SMALL);
		}
		solution.setSolItem(score, ItemId.SCORE);
		return score;
	}
}
