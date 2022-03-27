package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.Importance;

public class CharacterInfo extends Item {
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
	public ItemSol adapt(ItemSol characterInfoSolution, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene HEALTH lo creamos
		if(characterInfoSolution == null) {
			characterInfoSolution = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "informacionPersonaje", ItemId.CHARACTER_INFO);
		}
		
		characterInfoSolution.setScreenPosition(ScreenPos.TOP_LEFT);
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			characterInfoSolution.setItemScale(Scale.MEDIUM);
		}
		else {
			characterInfoSolution.setItemScale(Scale.SMALL);
		}
		solution.setSolItem(characterInfoSolution, ItemId.CHARACTER_INFO);
		return characterInfoSolution;
	}
}
