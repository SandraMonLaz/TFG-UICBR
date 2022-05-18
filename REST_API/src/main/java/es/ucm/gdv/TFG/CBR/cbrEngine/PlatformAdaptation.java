package es.ucm.gdv.TFG.CBR.cbrEngine;

import es.ucm.gdv.TFG.CBR.cbrComponents.CaseDescription;
import es.ucm.gdv.TFG.CBR.cbrComponents.CaseSolution;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.Scale;
import es.ucm.gdv.TFG.CBR.cbrComponents.ItemSol.ScreenPos;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterInfo;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.CharacterProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Collectable;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Item;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.ItemId;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.LevelProgress;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Shields;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Time;
import es.ucm.gdv.TFG.CBR.cbrComponents.items.Weapons;

public class PlatformAdaptation {

	private float [][] similarityTable;
	
	public PlatformAdaptation(){
		float [][] aux = 
					/* HEALTH  SCORE  SHIELDS   COLLECTABLE  WEAPONS ABILITIES  TIME  LEVEL_PROGRESS CHARACTER_INFO CHARACTER_PROGRESS*/	
/*HEALTH*/{				{1f,	0.3f,	0.8f,		0.3f,		0f,		0f,		0f,			0f,			0.75f,		0.8f},
/*SCORE*/				{0.3f,	1f,		0.3f,		0.8f,		0.3f,	0.3f,	0.75f,		0.6f,		0.2f,		0.2f},
/*SHIELDS*/				{0.8f,	0.3f,	1f,			0.3f,		0f,		0f,		0f,			0f,			0f,			0f},
/*COLLECTABLE*/			{0.3f,	0.8f,	0.3f,		1f,			0f,		0f,		0.6f,		0.3f,		0f,			0f},
/*WEAPONS*/				{0f,	0.3f,	0f,			0f,			1f,		0.8f,	0f,			0f,			0f,			0f},
/*ABILITIES*/			{0f,	0.3f,	0f,			0f,			0.8f,	1f,		0f,			0f,			0f,			0f},
/*TIME*/				{0f,	0.75f,	0f,			0.6f,		0f,		0f,		1f,			0.8f,		0.4f,		0.4f},
/*LEVEL_PROGRESS*/		{0f,	0.6f,	0f,			0.3f,		0f,		0f,		0.8f,		1f,			0.65f,		0.65f},
/*CHARACTER_INFO*/		{0.75f,	0.2f,	0f,			0f,			0f,		0f,		0.4f,		0.65f,		1f,			0.9f},
/*CHARACTER_PROGRESS*/	{0.8f,	0.2f,	0f,			0f,			0f,		0f,		0.4f,		0.65f,		0.9f,		1f}};
		
		similarityTable = aux;

	}
	
	/*
	 * Adapta cada uno de los items por separado para que una vez estén posicionados los junte o separe aplicando
	 * los principios de diseño y leyes de la gestalt
	 * */
	public SolCBR adapt(CaseSolution solution, CaseDescription queryDescription){	
		
		adaptEachItem(solution, queryDescription);

		//Miramos para cada item como interfiere en pantalla con el resto
		for(ItemSol itemSol: solution.getSolutionItems().getValues()) {	
			for(ItemSol other: solution.getSolutionItems().getValues()) {
				if(itemSol == null || other  == null) continue;
				if(similarityTable[itemSol.getId().ordinal()][other.getId().ordinal()] >= 0.75f &&	//si el item tiene similaridad con el otro
					itemSol.getScreenPosition() != other.getScreenPosition()){						//y su posicion no es la misma
					//si el item tiene mayor importancia/tamaño que el otro
					//traemos el otro a donde esta posicionado el item
					if(itemSol.getItemScale().ordinal() > other.getItemScale().ordinal())
						other.setScreenPosition(itemSol.getScreenPosition());	
					//si es alrevés ponemos el item en la posición del otro
					else	
						itemSol.setScreenPosition(other.getScreenPosition());					
				}
				else if (similarityTable[itemSol.getId().ordinal()][other.getId().ordinal()] < 0.75f &&	//si el item no tiene similaridad con el otro
					itemSol.getScreenPosition() == other.getScreenPosition()){							//y se encuentran en la misma posicion
					//si el item tiene mayor importancia/tamaño que el otro
					//llevamos el otro al lado opuesto donde esta posicionado el item
					if(itemSol.getItemScale().ordinal() > other.getItemScale().ordinal())
						other.setScreenPosition(getOpposite(itemSol.getScreenPosition()));	
					//si es alrevés ponemos el item en la posición opuesta del otro
					else	
						itemSol.setScreenPosition(getOpposite(other.getScreenPosition()));		
				}
			}
		}
		
		
		
		return createSol(solution, queryDescription);		
	}
	
	/*
	 * Adapta cada item adecuandose a las conclusiones sacadas en el diseño de interfaz en plataforma
	 * */
	private void adaptEachItem(CaseSolution solution, CaseDescription queryDescription){
		//Se modifica la solucion del caso
		ItemSol health = solution.getSolutionItems().getValues()[ItemId.HEALTH.ordinal()];
		ItemSol score = solution.getSolutionItems().getValues()[ItemId.SCORE.ordinal()];
		ItemSol abilities = solution.getSolutionItems().getValues()[ItemId.ABILITIES.ordinal()];
		ItemSol characterinfo = solution.getSolutionItems().getValues()[ItemId.CHARACTER_INFO.ordinal()];
		ItemSol characterProgress = solution.getSolutionItems().getValues()[ItemId.CHARACTER_PROGRESS.ordinal()];
		ItemSol collectable = solution.getSolutionItems().getValues()[ItemId.COLLECTABLE.ordinal()];
		ItemSol levelProgress = solution.getSolutionItems().getValues()[ItemId.LEVEL_PROGRESS.ordinal()];
		ItemSol shields = solution.getSolutionItems().getValues()[ItemId.SHIELDS.ordinal()];
		ItemSol time = solution.getSolutionItems().getValues()[ItemId.TIME.ordinal()];
		ItemSol weapons = solution.getSolutionItems().getValues()[ItemId.WEAPONS.ordinal()];
		
		//Llamamos a las funciones de adaptación
		Item item;
		item = queryDescription.getHealth();
		if(item != null)	item.adapt(health, solution);
		else solution.setSolItem(null, ItemId.HEALTH);
		
		item = queryDescription.getScore();
		if(item != null)	item.adapt(score, solution); 
		else solution.setSolItem(null, ItemId.SCORE);
		
		item = queryDescription.getAbilities();
		if(item != null)	item.adapt(abilities, solution);
		else solution.setSolItem(null, ItemId.ABILITIES);
			
		item = queryDescription.getCharacterinfo();
		if(item != null)	item.adapt(characterinfo, solution);
		else solution.setSolItem(null, ItemId.CHARACTER_INFO);
		
		item = queryDescription.getCharacterProgress();
		if(item != null)	item.adapt(characterProgress, solution);
		else solution.setSolItem(null, ItemId.CHARACTER_PROGRESS);
		
		item = queryDescription.getCollectable();
		if(item != null)	item.adapt(collectable, solution);
		else solution.setSolItem(null, ItemId.COLLECTABLE);
		
		item = queryDescription.getLevelProgress();
		if(item != null)	item.adapt(levelProgress, solution);
		else solution.setSolItem(null, ItemId.LEVEL_PROGRESS);
		
		item = queryDescription.getShields();
		if(item != null)	item.adapt(shields, solution);
		else solution.setSolItem(null, ItemId.SHIELDS);
		
		item = queryDescription.getTime();
		if(item != null)	item.adapt(time, solution);
		else solution.setSolItem(null, ItemId.TIME);
		
		item = queryDescription.getWeapons();
		if(item != null)	item.adapt(weapons, solution);
		else solution.setSolItem(null, ItemId.WEAPONS);
	}
	
	/*
	 * Crea una solucion combinando los items por posicion
	 * */
	private SolCBR createSol(CaseSolution solution,  CaseDescription queryDescription){
		// Se usa la solucion modificada para crear la solucion del CBR		
		SolCBR CBR = new SolCBR();
		CombinedItem combinedTL = new CombinedItem();
		combinedTL.setScreenPosition(ScreenPos.TOP_LEFT);
		combinedTL.setItemScale(Scale.MEDIUM);
		CombinedItem combinedTC = new CombinedItem();
		combinedTC.setScreenPosition(ScreenPos.TOP_CENTER);
		combinedTC.setItemScale(Scale.MEDIUM);
		CombinedItem combinedTR = new CombinedItem();
		combinedTR.setScreenPosition(ScreenPos.TOP_RIGHT);
		combinedTR.setItemScale(Scale.MEDIUM);
		CombinedItem combinedCL = new CombinedItem();
		combinedCL.setScreenPosition(ScreenPos.MIDDLE_LEFT);
		combinedCL.setItemScale(Scale.MEDIUM);
		CombinedItem combinedCC = new CombinedItem();
		combinedCC.setScreenPosition(ScreenPos.MIDDLE_CENTER);
		combinedCC.setItemScale(Scale.MEDIUM);
		CombinedItem combinedCR = new CombinedItem();
		combinedCR.setScreenPosition(ScreenPos.MIDDLE_RIGHT);
		combinedCR.setItemScale(Scale.MEDIUM);
		CombinedItem combinedBL= new CombinedItem();
		combinedBL.setScreenPosition(ScreenPos.BOTTOM_LEFT);
		combinedBL.setItemScale(Scale.MEDIUM);
		CombinedItem combinedBC= new CombinedItem();
		combinedBC.setScreenPosition(ScreenPos.BOTTOM_CENTER);
		combinedBC.setItemScale(Scale.MEDIUM);
		CombinedItem combinedBR= new CombinedItem();
		combinedBR.setScreenPosition(ScreenPos.BOTTOM_RIGHT);
		combinedBR.setItemScale(Scale.MEDIUM);
		
		
		for(ItemSol itemSol: solution.getSolutionItems().getValues()) {
			if(itemSol != null) {
				int n = 1;
				if(itemSol.getId() == ItemId.ABILITIES || itemSol.getId() == ItemId.WEAPONS)
					n = (itemSol.getId() == ItemId.ABILITIES)? queryDescription.getAbilities().getnWeapons(): queryDescription.getWeapons().getnWeapons();
				
				for(int i = 0; i < n; i++){				
					switch(itemSol.getScreenPosition()) {
					case TOP_LEFT:		combinedTL.getItems().add(itemSol); break;
					case TOP_CENTER:	combinedTC.getItems().add(itemSol);	break;
					case TOP_RIGHT: 	combinedTR.getItems().add(itemSol); break;
					case MIDDLE_LEFT:	combinedCL.getItems().add(itemSol); break;
					case MIDDLE_CENTER:	combinedCC.getItems().add(itemSol); break;
					case MIDDLE_RIGHT:	combinedCR.getItems().add(itemSol); break;
					case BOTTOM_LEFT:	combinedBL.getItems().add(itemSol); break;
					case BOTTOM_CENTER:	combinedBC.getItems().add(itemSol); break;
					case BOTTOM_RIGHT:	combinedBR.getItems().add(itemSol); break;
					}			
				}
			}
		}
			
		CBR.getSol().add(combinedTL);
		CBR.getSol().add(combinedTC);
		CBR.getSol().add(combinedTR);
		CBR.getSol().add(combinedCL);
		CBR.getSol().add(combinedCC);
		CBR.getSol().add(combinedCR);
		CBR.getSol().add(combinedBL);
		CBR.getSol().add(combinedBC);
		CBR.getSol().add(combinedBR);
		return CBR;
	}
	
	/*
	 * Devuelve la posicion opuesta en la pantalla a la dada
	 * */
	private ScreenPos getOpposite(ScreenPos pos) {
		switch(pos){
			case TOP_LEFT:		return ScreenPos.BOTTOM_RIGHT;
			case TOP_CENTER:	return ScreenPos.BOTTOM_CENTER;
			case TOP_RIGHT: 	return ScreenPos.BOTTOM_LEFT;
			case MIDDLE_LEFT:	return ScreenPos.MIDDLE_RIGHT;
			case MIDDLE_CENTER:	return ScreenPos.BOTTOM_RIGHT;
			case MIDDLE_RIGHT:	return ScreenPos.MIDDLE_LEFT;
			case BOTTOM_LEFT:	return ScreenPos.TOP_RIGHT;
			case BOTTOM_CENTER:	return ScreenPos.TOP_CENTER;
			case BOTTOM_RIGHT:	return ScreenPos.TOP_LEFT;
		}
		return pos;
	}
}
