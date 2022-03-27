package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.Importance;

public class Collectable extends Item {
	@Override
	public void fromString(String content) throws Exception {
		String[] splited = content.split("\\|");
		super.fromString(splited[0]);	
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		return s;
	}
	
	@Override
	public ItemSol adapt(ItemSol solutionCollectable, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene COLLECTABLE lo creamos
		if(solutionCollectable == null) {
			solutionCollectable = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "collectable", ItemId.COLLECTABLE);
		}
		
		//TODO : Hacer que coincida lo menos posible con otros elementos?
		solutionCollectable.setScreenPosition(ScreenPos.TOP_RIGHT);
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			solutionCollectable.setItemScale(Scale.MEDIUM);
		}
		else {
			solutionCollectable.setItemScale(Scale.SMALL);
		}
		
		solution.setSolItem(solutionCollectable, ItemId.COLLECTABLE);
		return solutionCollectable;
	}
}
