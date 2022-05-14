package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.REST_API.Importance;
import es.ucm.gdv.TFG.REST_API.UseType;

public class Weapons extends Item {
	UseType useType;
	int nWeapons;

	public int getnWeapons() {
		return nWeapons;
	}

	public void setnWeapons(int nWeapons) {
		this.nWeapons = nWeapons;
	}

	public UseType getUseType() {
		return useType;
	}

	public void setUseType(UseType type) {
		this.useType = type;
	}
	
	@Override
	public void fromString(String content) throws Exception {
		String[] splited = content.split("\\|");
		super.fromString(splited[0]);
		this.useType = UseType.valueOf(splited[1]);	
		this.nWeapons = Integer.parseInt(splited[2]);
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		return s + "|" + this.useType.toString() + "|" + this.nWeapons;
	}
	
	@Override
	public ItemSol adapt(ItemSol solutionWeapons, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene habilidades lo creamos
		if(solutionWeapons == null) {
			solutionWeapons = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "armaInfinita", ItemId.WEAPONS);
		}
		
		if(this.useType == UseType.infinite)		solutionWeapons.setImage("armaInfinita");
		else if(this.useType == UseType.limited) 	solutionWeapons.setImage("armaLimitada");
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			solutionWeapons.setItemScale(Scale.MEDIUM);
		}
		else {
			solutionWeapons.setItemScale(Scale.SMALL);
		}
		
		solution.setSolItem(solutionWeapons, ItemId.ABILITIES);
		return solutionWeapons;
	}
}
