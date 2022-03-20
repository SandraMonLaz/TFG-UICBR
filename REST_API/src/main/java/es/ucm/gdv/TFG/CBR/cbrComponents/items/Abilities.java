package es.ucm.gdv.TFG.CBR.cbrComponents.items;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.REST_API.platform2D.Abilities.UseType;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;

public class Abilities extends Item {
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
	public ItemSol adapt(ItemSol solutionAbilities, CaseSolution solution) {
		//Si la solucion que nos ofrece no tiene habilidades lo creamos
		if(solutionAbilities == null) {
			solutionAbilities = new ItemSol(ScreenPos.TOP_LEFT, Scale.MEDIUM, "habilidadInfinita", ItemId.ABILITIES);
		}
		
		if(this.useType == UseType.infinite)		solutionAbilities.setImage("habilidadInfinita");
		else if(this.useType == UseType.limited) 	solutionAbilities.setImage("habilidadLimitada");
		
		ItemSol health = solution.getSolutionItems().getValues()[ItemId.HEALTH.ordinal()];
		
		//Dependiendo de donde se encuentra la vida se colocan las habilidades
		if(health == null) {
			solutionAbilities.setScreenPosition(ScreenPos.TOP_RIGHT);
		}
		else {
			if(health.getScreenPosition() == ScreenPos.TOP_LEFT 
			|| health.getScreenPosition() == ScreenPos.BOTTOM_LEFT
			|| health.getScreenPosition() == ScreenPos.MIDDLE_LEFT) 
			{
				solutionAbilities.setScreenPosition(ScreenPos.TOP_RIGHT);
			}
			else if(health.getScreenPosition() == ScreenPos.TOP_RIGHT
				 || health.getScreenPosition() == ScreenPos.BOTTOM_RIGHT
				 || health.getScreenPosition() == ScreenPos.MIDDLE_RIGHT) 
			{
				solutionAbilities.setScreenPosition(ScreenPos.TOP_LEFT);
			}
			else 
			{
				solutionAbilities.setScreenPosition(ScreenPos.TOP_RIGHT);
			}
		}
		
		if(this.importance.ordinal() >= Importance.high.ordinal()) {
			solutionAbilities.setItemScale(Scale.MEDIUM);
		}
		else {
			solutionAbilities.setItemScale(Scale.SMALL);
		}
		
		solution.setSolItem(solutionAbilities, ItemId.ABILITIES);
		return solutionAbilities;
	}
}
